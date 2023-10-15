package com.example.consultation4.consultation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.text.InputType;
import android.util.Base64;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ModifyBV extends AppCompatActivity {

    private EditText editTextCodeBV, editTextResponsable, editBureauVote, editCentreVote, editTongaNandatsabato, editLaharanaPV, editFotsy;
    private EditText editVatoManankery, editTaratasyTaoAnatyVata, editVatoMaty,editlahy,editvavy;
    private Button buttonValider;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifybv);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Miandrasa ...");
        progressDialog.setCancelable(false);

        // Initialiser les champs de texte de l'activité avec les valeurs du BV
        editTextCodeBV = findViewById(R.id.editTextCodeBV);
        editTextResponsable = findViewById(R.id.editTextResponsable);
        editBureauVote = findViewById(R.id.editBureauVote);
        editTongaNandatsabato = findViewById(R.id.editTongaNandatsabato);
        editLaharanaPV = findViewById(R.id.editLaharanaPV);
        editVatoManankery = findViewById(R.id.editVatoManankery);
        editTaratasyTaoAnatyVata = findViewById(R.id.editTaratasyTaoAnatyVata);
        editVatoMaty = findViewById(R.id.editVatoMaty);
        editFotsy = findViewById(R.id.editFotsy);
        editvavy= findViewById(R.id.editvavy);
        editlahy= findViewById(R.id.editlahy);
        buttonValider = findViewById(R.id.buttonValider);

        editBureauVote.setEnabled(false);
        editBureauVote.setFocusable(false);
        editBureauVote.setClickable(false);

        editTextCodeBV.setEnabled(false);
        editTextCodeBV.setFocusable(false);
        editTextCodeBV.setClickable(false);

        editTextResponsable.setEnabled(false);
        editTextResponsable.setFocusable(false);
        editTextResponsable.setClickable(false);

        // Récupérer le Bundle avec les données de l'intent
        Bundle dataBundle = getIntent().getExtras();
        if (dataBundle != null) {

            // Récupérer les données du Bundle
            String loggedInUserId = dataBundle.getString("loggedInUserId");
            String codeBV = dataBundle.getString("codeBV");
            String responsable = dataBundle.getString("responsable");
            String bureauVote = dataBundle.getString("Bureau de vote");
            String centreVote = dataBundle.getString("Centre de vote");
            String inscrit = dataBundle.getString("inscrit");
            String tongaNandatsabato = dataBundle.getString("Tonga nandatsabato");
            String Blancs_nuls = dataBundle.getString("Blancs_nuls");
            String laharanaPV = dataBundle.getString("Laharana PV");
            String vatoManankery = dataBundle.getString("Vato manankery");
            String taratasyTaoAnatyVata = dataBundle.getString("Taratasy tao anaty vata");
            String Observdata = dataBundle.getString("Observdata");
            String Etat_bv = dataBundle.getString("Etat_bv");
            String Rajout = dataBundle.getString("Rajout");
            String Bulturne = dataBundle.getString("Taratasy tao anaty vata");
            String vatoMaty = dataBundle.getString("Vato maty");
            String vfotsy = dataBundle.getString("Vato Fosty");
            String Date_saisie = dataBundle.getString("Date_saisie");
            String homme = dataBundle.getString("Lehilahy");
            String femme = dataBundle.getString("Vehivavy");
            String Bultsurnombr = dataBundle.getString("Bultsurnombr");
            String Bultmeme = dataBundle.getString("Bultmeme");
            String Bultinf = dataBundle.getString("Bultinf");
            String Bultenleve = dataBundle.getString("Bultenleve");
            String Carnerecu = dataBundle.getString("Carnerecu");
            String Numcarnetrecu = dataBundle.getString("Numcarnetrecu");
            String Bultrecu = dataBundle.getString("Bultrecu");
            String Bultrecuut = dataBundle.getString("Bultrecuut");
            String Bultrecunut = dataBundle.getString("Bultrecunut");



            // Attribuer les valeurs aux champs de texte
            editTextCodeBV.setText(codeBV);
            editTextResponsable.setText(responsable);
            editBureauVote.setText(bureauVote);
            editTongaNandatsabato.setText(tongaNandatsabato);
            editLaharanaPV.setText(laharanaPV);
            editVatoManankery.setText(vatoManankery);
            editTaratasyTaoAnatyVata.setText(taratasyTaoAnatyVata);
            editVatoMaty.setText(vatoMaty);
            editFotsy.setText(vfotsy);
            editlahy.setText(homme);
            editvavy.setText(femme);

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
                editNombreVoix.setInputType(InputType.TYPE_CLASS_NUMBER);
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
                        String modifiedTongaNandatsabato = editTongaNandatsabato.getText().toString();
                        String modifiedLaharanaPV = editLaharanaPV.getText().toString();
                        String modifiedVatoManankery = editVatoManankery.getText().toString();
                        String modifiedTaratasyTaoAnatyVata = editTaratasyTaoAnatyVata.getText().toString();
                        String modifiedvatomaty = editVatoMaty.getText().toString();
                        String modifiedfotsy = editFotsy.getText().toString();
                        String modifiedLahy = editlahy.getText().toString();
                        String modifiedvavy = editvavy.getText().toString();
                        ArrayList<String> modifiedVoixList = new ArrayList<>();
                        String texte = codeBV.toString().trim();
                        String sousDossierNom = texte;
                        String fileName = "bv_"+ texte + ".txt";

                        String format = "yyyy-MM-dd HH:mm:ss";
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        String dateActuelle = sdf.format(new Date()); // Obtient la date actuelle au format souhaité

                            /* Vérifier si le CODE_BV existe déjà dans la base de données
                            if (dbHelper.checkBVExists(code_bv)) {
                                Toast.makeText(getApplicationContext(), "BV efa vita saisie", Toast.LENGTH_LONG).show();
                                return; // Arrêtez l'exécution de la méthode, car le BV existe déjà
                            }*/
                        // Créer un objet File représentant le répertoire "DocumentTXT"
                        File documentTxtDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "DocumentTXT");

                        // Vérifier si le répertoire "DocumentTXT" existe, sinon le créer
                        if (!documentTxtDir.exists()) {
                            documentTxtDir.mkdirs();
                        }


                        // Créer un objet File représentant le répertoire du sous-dossier
                        File sousDossierDir = new File(documentTxtDir, sousDossierNom);

                        // Vérifier si le sous-dossier existe, sinon le créer
                        if (!sousDossierDir.exists()) {
                            sousDossierDir.mkdirs();
                        }

                        // Créer un objet File représentant le fichier des voix obtenues
                        File file = new File(sousDossierDir, fileName);
                        try {
                            // FileWriter avec le paramètre true permet d'ajouter au fichier existant au lieu de le remplacer
                            FileWriter fileWriter = new FileWriter(file, false);
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            // Écrire les informations communes dans le fichier
                            bufferedWriter.write("UPDATE bv SET " +
                                    "NUM_USERINFO = '" + loggedInUserId + "', " +
                                    "LIBELLE_BV = '" + modifiedBureauVote + "', " +
                                    "EMPLACEMENT = '" + centreVote + "', " +
                                    "INSCRITS = '" + inscrit + "', " +
                                    "VOTANT = '" + modifiedTongaNandatsabato + "', " +
                                    "BLANCS_NULS = '" + Blancs_nuls + "', " +
                                    "NUMPV = '" + modifiedLaharanaPV + "', " +
                                    "SEXPRIMES_BV = '" + modifiedVatoManankery + "', " +
                                    "OBSERVDATA_BV = '" + Observdata + "', " +
                                    "ETAT_BV = '0', " +
                                    "RAJOUT = '" + Rajout + "', " +
                                    "BULTURNE = '" + modifiedTaratasyTaoAnatyVata + "', " +
                                    "BULTNULL = '" + modifiedvatomaty + "', " +
                                    "BULTBLANC = '" + modifiedfotsy + "', " +
                                    "DATE_SAISIE = '" + Date_saisie + "', " +
                                    "HOMME = '" + modifiedLahy + "', " +
                                    "FEMME = '" + modifiedvavy + "', " +
                                    "BULTSURNOMBRE = '" + Bultsurnombr + "', " +
                                    "BULTMEME = '" + Bultmeme + "', " +
                                    "BULTINF = '" + Bultinf + "', " +
                                    "BULTENLEVE = '" + Bultenleve + "', " +
                                    "CARNETRECU = '" + Carnerecu + "', " +
                                    "NUMCARNETRECU = '" + Numcarnetrecu + "', " +
                                    "BULTRECU = '" + Bultrecu + "', " +
                                    "BULTRECUUT = '" + Bultrecuut + "', " +
                                    "BULTRECUNUT = '" + Bultrecunut + "' " +
                                    "WHERE CODE_BV = '" + modifiedCodeBV + "';\n");

                            progressDialog.show();

                            for (int j = 0; j < finalI; j++) {
                                LinearLayout candidateVotesLayout = (LinearLayout) linearLayoutVoixObtenues.getChildAt(j);
                                TextView textNumeroCandidat = (TextView) candidateVotesLayout.getChildAt(0);
                                EditText editNombreVoix = (EditText) candidateVotesLayout.getChildAt(1);
                                String modifiedNumeroCandidat = textNumeroCandidat.getText().toString();
                                String modifiedNombreVoix = editNombreVoix.getText().toString();
                                Intent resultIntent = new Intent(this, ConsultationActivity.class);
                                resultIntent.putExtra("Numéro candidat " + j, modifiedNumeroCandidat);
                                resultIntent.putExtra("Nombre de voix " + j, modifiedNombreVoix);

                                bufferedWriter.write("INSERT INTO voixobtenue (NUM_CANDIDAT, CODE_BV, NBVOIX) VALUES ('" +
                                        modifiedNumeroCandidat + "', '" + modifiedCodeBV + "', '" + modifiedNombreVoix + "');\n");

                                boolean updateResult = dbHelper.updateVoixObtenue(modifiedNumeroCandidat, modifiedNombreVoix);
                                if (updateResult) {
                                    // Mise à jour réussie pour ce candidat

                                } else {
                                    // Échec de la mise à jour pour ce candidat
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Échec de la mise à jour pour le candidat " + modifiedNumeroCandidat, Toast.LENGTH_SHORT).show();
                                }
                                modifiedVoixList.add(modifiedNombreVoix);
                            }
                            bufferedWriter.close();
                            progressDialog.dismiss();

                            // Lire le contenu du fichier combiné
                            String combinedText = readFile(file);

                            // Crypter le texte combiné avec Base64
                            String encryptedCombinedText = encryptWithBase64(combinedText);

                            // Vérifier si le texte crypté n'est pas null
                            if (encryptedCombinedText != null) {
                                // Écrire le texte crypté dans le fichier final
                                FileWriter writer = new FileWriter(file);
                                writer.write(encryptedCombinedText);

                                // Fermer le FileWriter pour le fichier final
                                writer.flush();
                                writer.close();

                                Toast.makeText(getApplicationContext(), "Tafiditra ", Toast.LENGTH_LONG).show();
                                finish();  // Fermer l'activité actuelle si nécessaire
                            } else {
                                // Gérer le cas où le texte crypté est null
                                Toast.makeText(getApplicationContext(), "Erreur de cryptage du texte combiné", Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Erreur lors de la création ou de l'écrasement du fichier texte combiné", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        String BN = vfotsy + vatoMaty ;

                        BV bv = new BV();
                        bv.setCode_bv(modifiedCodeBV);
                        bv.setResponsable(modifiedResponsable);
                        bv.setBureau_de_vote(modifiedBureauVote);
                        bv.setCentre_de_vote(centreVote);
                        bv.setVOTANT(modifiedTongaNandatsabato);
                        bv.setBLANCS_NULS(BN);
                        bv.setNB_PV(modifiedLaharanaPV);
                        bv.setV_Manankery(modifiedVatoManankery);
                        bv.setOBSERVDATA_BV(Observdata);
                        bv.setETAT_BV("0");
                        bv.setRAJOUT(Rajout);
                        bv.setBULTURNE(modifiedTaratasyTaoAnatyVata);
                        bv.setBULTNULL(modifiedvatomaty);
                        bv.setBULTBLANC(modifiedfotsy);
                        bv.setDHMAJ(dateActuelle);
                        bv.setHOMME(modifiedLahy);
                        bv.setFEMME(modifiedvavy);
                        bv.setBULTSURNOMBR(Bultsurnombr);
                        bv.setBULTMEME(Bultmeme);
                        bv.setBULTINF(Bultinf);
                        bv.setBULTENLEVE(Bultenleve);
                        bv.setI_KarineVoaray(Carnerecu);
                        bv.setL_KarineVoaray(Numcarnetrecu);
                        bv.setI_biletàTokanaKarine(Bultrecu);
                        bv.setI_biletàTokanaNampiasaina(Bultrecuut);
                        bv.setI_biletàTokanaTsyNampiasaina(Bultrecunut);
                        boolean result = dbHelper.updateBV(bv);
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Enregistrement réussi", Toast.LENGTH_LONG).show();
                            Intent menuIntent = new Intent(this, MenuActivity.class);
                            menuIntent.putExtra("User", loggedInUserId);
                            startActivity(menuIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Erreur lors de l'enregistrement", Toast.LENGTH_LONG).show();
                        }
                        Intent resultIntent = new Intent(this, ConsultationActivity.class);
                        resultIntent.putExtra("User", loggedInUserId);
                        resultIntent.putExtra("codeBV", modifiedCodeBV);
                        resultIntent.putExtra("responsable", modifiedResponsable);
                        resultIntent.putExtra("Bureau de vote", modifiedBureauVote);
                        resultIntent.putExtra("Centre de vote", centreVote);
                        resultIntent.putExtra("Tonga nandatsabato", modifiedTongaNandatsabato);
                        resultIntent.putExtra("Laharana PV", modifiedLaharanaPV);
                        resultIntent.putExtra("Vato manankery", modifiedVatoManankery);
                        resultIntent.putExtra("Taratasy tao anaty vata", modifiedTaratasyTaoAnatyVata);
                        for (int j = 0; j < modifiedVoixList.size(); j++) {
                            resultIntent.putExtra("Nombre de voix " + j, modifiedVoixList.get(j));
                        }
                        setResult(MenuActivity.RESULT_OK, resultIntent);
                        finish();
                    }
            );

        }
    }

    private static String encryptWithBase64(String text) {
        // Convertir le texte en bytes
        byte[] textBytes = text.getBytes();

        // Chiffrer le texte en Base64
        return Base64.encodeToString(textBytes, Base64.DEFAULT);
    }

    private String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        reader.close();
        return stringBuilder.toString();
    }
}