package com.example.consultation4.consultation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.consultation4.R;
import com.example.consultation4.model.BV;

import java.util.ArrayList;
import java.util.List;

public class BVAdapter extends ArrayAdapter<BV> implements Filterable {

    private List<BV> bvList;
    private List<BV> filteredBvList;

    public BVAdapter(Context context, List<BV> bvList) {
        super(context, 0, bvList);
        this.bvList = bvList;
        this.filteredBvList = new ArrayList<>(bvList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BV bv = bvList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bv, parent, false);
        }

        TextView tvCode_bv = convertView.findViewById(R.id.tvCode_bv);
        TextView tvNB_PV = convertView.findViewById(R.id.tvNB_PV);
        TextView tvCentreVote = convertView.findViewById(R.id.tvEmplacement);
        TextView tvBureauVote = convertView.findViewById(R.id.tvBureauVote);
        TextView tvResponsable = convertView.findViewById(R.id.tvResponsable);

        tvCode_bv.setText(bv.getCode_bv());
        tvNB_PV.setText(bv.getNB_PV());
        tvCentreVote.setText(bv.getCentre_de_vote());
        tvBureauVote.setText(bv.getBureau_de_vote());
        tvResponsable.setText(bv.getResponsable());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();

                List<BV> filteredList = new ArrayList<>();

                // Parcourez la liste complète pour trouver les BV qui correspondent au texte de recherche
                for (BV bv : bvList) {
                    if (bv.getCode_bv().toLowerCase().contains(filterString) ||
                            bv.getCentre_de_vote().toLowerCase().contains(filterString) ||
                            bv.getBureau_de_vote().toLowerCase().contains(filterString) ||
                            bv.getResponsable().toLowerCase().contains(filterString)) {
                        filteredList.add(bv);
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredBvList = (List<BV>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setFilter(List<BV> filteredList) {
        clear();
        addAll(filteredList);
    }
}
