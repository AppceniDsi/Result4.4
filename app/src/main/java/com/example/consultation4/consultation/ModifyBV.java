package com.example.consultation4.consultation;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.consultation4.R;
import com.example.consultation4.model.BV;
import com.example.consultation4.consultation.VoixObtenue;
import com.example.consultation4.service.dbSqLite;

import java.util.ArrayList;

public class ModifyBV extends AppCompatActivity {

    private EditText editTextCodeBV, editTextResponsable, editBureauVote, editCentreVote, editTongaNandatsabato, editLaharanaPV;
    private EditText editVatoManankery, editTaratasyTaoAnatyVata, editVatoMaty;
    private Button buttonValider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifybv);

        // Initialiser les champs de texte de l'activité avec les valeurs du BV
        editTextCodeBV = findViewById(R.id.editTextCodeBV);
        editTextResponsable = findViewById(R.id.editTextResponsable);
        editBureauVote = findViewById(R.id.editBureauVote);
        editCentreVote = findViewById(R.id.editCentreVote);
        editTongaNandatsabato = findViewById(R.id.editTongaNandatsabato);
        editLaharanaPV = findViewById(R.id.editLaharanaPV);
        editVatoManankery = findViewById(R.id.editVatoManankery);
        editTaratasyTaoAnatyVata = findViewById(R.id.editTaratasyTaoAnatyVata);
        editVatoMaty = findViewById(R.id.editVatoMaty);
         buttonValider = findViewById(R.id.buttonValider);

        // Récupérer le Bundle avec les données de l'intent
        Bundle dataBundle = getIntent().getExtras();
        if (dataBundle != null) {

            // Récupérer les données du Bundle
            String codeBV = dataBundle.getString("codeBV");
            String responsable = dataBundle.getString("responsable");
            String bureauVote = dataBundle.getString("Bureau de vote");
            String centreVote = dataBundle.getString("Centre de vote");
            String tongaNandatsabato = dataBundle.getString("Tonga nandatsabato");
            String laharanaPV = dataBundle.getString("Laharana PV");
            String vatoManankery = dataBundle.getString("Vato manankery");
            String taratasyTaoAnatyVata = dataBundle.getString("Taratasy tao anaty vata");
            String vatoMaty = dataBundle.getString("Vato maty");

            // Attribuer les valeurs aux champs de texte
            editTextCodeBV.setText(codeBV);
            editTextResponsable.setText(responsable);
            editBureauVote.setText(bureauVote);
            editCentreVote.setText(centreVote);
            editTongaNandatsabato.setText(tongaNandatsabato);
            editLaharanaPV.setText(laharanaPV);
            editVatoManankery.setText(vatoManankery);
            editTaratasyTaoAnatyVata.setText(taratasyTaoAnatyVata);
            editVatoMaty.setText(vatoMaty);

            // Trouver le LinearLayout où nous allons ajouter les vues dynamiquement
            LinearLayout linearLayoutVoixObtenues = findViewById(R.id.linearLayoutVoixObtenues);

            // Boucle pour extraire et afficher les informations des voix obtenues
            int i = 0;
            while (dataBundle.containsKey("Numéro candidat " + i)) {
                String numeroCandidat = dataBundle.getString("Numéro candidat " + i);
                String nombreVoix = dataBundle.getString("Nombre de voix " + i);

                // Créer un LinearLayout horizontal pour chaque paire de données
                LinearLayout candidateVotesLayout = new LinearLayout(this);
                candidateVotesLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                candidateVotesLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Créer des TextView pour afficher le numéro de candidat
                TextView textNumeroCandidat = new TextView(this);
                textNumeroCandidat.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
                ));
                textNumeroCandidat.setText(numeroCandidat != null ? numeroCandidat : "");

                // Créer un EditText pour permettre l'édition du nombre de voix
                EditText editNombreVoix = new EditText(this);
                editNombreVoix.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
                ));
                editNombreVoix.setText(nombreVoix != null ? nombreVoix : "");
                editNombreVoix.setInputType(InputType.TYPE_CLASS_NUMBER); // Permet d'entrer uniquement des nombres

                // Ajouter les vues au LinearLayout horizontal
                candidateVotesLayout.addView(textNumeroCandidat);
                candidateVotesLayout.addView(editNombreVoix);

                // Ajouter le LinearLayout horizontal au LinearLayout principal
                linearLayoutVoixObtenues.addView(candidateVotesLayout);

                i++;
            }

            // Trouver le bouton "Valider"
            Button buttonValider = findViewById(R.id.buttonValider);

            dbSqLite dbHelper = new dbSqLite(getApplicationContext());

            // Ajouter un écouteur de clic au bouton "Valider"
            int finalI = i;
            buttonValider.setOnClickListener(view -> {
                // Ici, vous pouvez récupérer les valeurs modifiées dans les champs de texte
                String modifiedCodeBV = editTextCodeBV.getText().toString();
                String modifiedResponsable = editTextResponsable.getText().toString();
                String modifiedBureauVote = editBureauVote.getText().toString();
                String modifiedCentreVote = editCentreVote.getText().toString();
                String modifiedTongaNandatsabato = editTongaNandatsabato.getText().toString();
                String modifiedLaharanaPV = editLaharanaPV.getText().toString();
                String modifiedVatoManankery = editVatoManankery.getText().toString();
                String modifiedTaratasyTaoAnatyVata = editTaratasyTaoAnatyVata.getText().toString();
                String modifiedvatomaty = editVatoMaty.getText().toString();
                ArrayList<String> modifiedVoixList = new ArrayList<>();
                for (int j = 0; j < finalI; j++) {
                    LinearLayout candidateVotesLayout = (LinearLayout) linearLayoutVoixObtenues.getChildAt(j);
                    EditText editNombreVoix = (EditText) candidateVotesLayout.getChildAt(1);
                    String modifiedNombreVoix = editNombreVoix.getText().toString();
                    modifiedVoixList.add(modifiedNombreVoix);
                }

                BV bv = new BV();
                bv.setCode_bv(modifiedCodeBV);
                bv.setResponsable(modifiedResponsable);
                bv.setBULTNULL(modifiedTongaNandatsabato);
                bv.setVOTANT(modifiedLaharanaPV);
                bv.setNB_PV(modifiedLaharanaPV);
                bv.setV_Manankery(modifiedVatoManankery);
                bv.setBULTURNE(modifiedTaratasyTaoAnatyVata);
                bv.setV_Manankery(modifiedvatomaty);

                boolean result = dbHelper.updateBV(bv);
                if (result) {
                    Toast.makeText(getApplicationContext(), "Enregistrement réussi", Toast.LENGTH_LONG).show();

                    Intent menuIntent = new Intent(this, MenuActivity.class);
                    startActivity(menuIntent);

                    // ...
                } else {
                    Toast.makeText(getApplicationContext(), "Erreur lors de l'enregistrement", Toast.LENGTH_LONG).show();
                }


                // Créer un intent pour renvoyer les données modifiées à l'activité précédente (ConsultationActivity)
                Intent resultIntent = new Intent(this, ConsultationActivity.class);
                resultIntent.putExtra("codeBV", modifiedCodeBV);
                resultIntent.putExtra("responsable", modifiedResponsable);
                resultIntent.putExtra("Bureau de vote", modifiedBureauVote);
                resultIntent.putExtra("Centre de vote", modifiedCentreVote);
                resultIntent.putExtra("Tonga nandatsabato", modifiedCodeBV);
                resultIntent.putExtra("Laharana PV", modifiedResponsable);
                resultIntent.putExtra("Vato manankery", modifiedBureauVote);
                resultIntent.putExtra("Taratasy tao anaty vata", modifiedCentreVote);
                resultIntent.putExtra("Vato maty", modifiedResponsable);
                resultIntent.putExtra("Vato Fosty", modifiedBureauVote);
                resultIntent.putExtra("Lehilahy", modifiedCentreVote);
                resultIntent.putExtra("Lehilahy", modifiedCentreVote);
                // ... et ainsi de suite pour les autres données

                // Ajouter les nouvelles valeurs des voix obtenues à l'intent
                for (int j = 0; j < modifiedVoixList.size(); j++) {
                    resultIntent.putExtra("Nombre de voix " + j, modifiedVoixList.get(j));
                }

                // Définir le résultat de l'intention comme "RESULT_OK"
                setResult(MenuActivity.RESULT_OK, resultIntent);

                // Terminer l'activité actuelle (ModifyBV) pour retourner à l'activité précédente (ConsultationActivity)
                finish();
            }
            );

        }


    }
}
