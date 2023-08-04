package com.example.consultation4.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.consultation4.R;
import com.example.consultation4.model.Candidat;

import java.util.List;

public class CandidatAdapter extends ArrayAdapter<Candidat> {

    public CandidatAdapter(Context context, List<Candidat> candidatList) {
        super(context, 0, candidatList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_candidat, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvNumOrdre = convertView.findViewById(R.id.tvNumOrdre);
            viewHolder.ivLogo = convertView.findViewById(R.id.ivLogo);
            viewHolder.tvNomPrenom = convertView.findViewById(R.id.tvNomPrenom);
            viewHolder.tvEntite = convertView.findViewById(R.id.tvEntite);

            viewHolder.etVoixObtenues = convertView.findViewById(R.id.editTextVoixObtenue);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Candidat candidat = getItem(position);

        if (candidat != null) {
            viewHolder.tvNumOrdre.setText(String.valueOf(candidat.getNumOrdre()));
            viewHolder.tvNomPrenom.setText(candidat.getNomPrenom());
            viewHolder.tvEntite.setText(candidat.getEntite());

            int logoResId = getContext().getResources().getIdentifier(candidat.getLogo(), "drawable", getContext().getPackageName());
            if (logoResId != 0) {
                viewHolder.ivLogo.setImageResource(logoResId);
            } else {
                viewHolder.ivLogo.setImageResource(R.drawable.inc); // Set a default image
            }

            viewHolder.etVoixObtenues.removeTextChangedListener(viewHolder.textWatcher); // Remove the previous TextWatcher to avoid duplication

            viewHolder.etVoixObtenues.setText(String.valueOf(candidat.getVoixObtenue()));

            viewHolder.textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        int voixObtenues = Integer.parseInt(editable.toString());
                        candidat.setVoixObtenue(String.valueOf(voixObtenues));
                        Log.d("Voasoratra zaoooooo", "afterTextChanged: Candidat  " + candidat.getNomPrenom() + " voixObtenues " + voixObtenues);
                    } catch (NumberFormatException e) {
                        // Assurez-vous que la valeur par défaut est valide
                        candidat.setVoixObtenue("0");
                    }
                }
            };

            viewHolder.etVoixObtenues.addTextChangedListener(viewHolder.textWatcher);

            // Set the tag with the current position for later use
            viewHolder.etVoixObtenues.setTag(position);

            viewHolder.etVoixObtenues.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    int nextPosition = position + 1;
                    if (nextPosition < getCount()) {
                        Candidat nextCandidat = getItem(nextPosition);
                        if (nextCandidat != null) {
                            nextCandidat.setVoixObtenue(""); // Effacer la valeur du champ suivant
                            notifyDataSetChanged(); // Pour mettre à jour la vue
                        }
                    }
                }
                return false;
            });
        }

        return convertView;
    }


    private static class ViewHolder {
        TextView tvNumOrdre;
        ImageView ivLogo;
        TextView tvNomPrenom;
        TextView tvEntite;
        EditText etVoixObtenues;
        TextWatcher textWatcher;
    }

    public int getTotalVoixObtenues() {
        int totalVoixObtenues = 0;
        for (int i = 0; i < getCount(); i++) {
            Candidat candidat = getItem(i);
            if (candidat != null) {
                try {
                    int voixObtenues = Integer.parseInt(candidat.getVoixObtenue());
                    totalVoixObtenues += voixObtenues;
                } catch (NumberFormatException e) {
                    // Ignore les valeurs non valides
                }
            }
        }
        return totalVoixObtenues;
    }
}
