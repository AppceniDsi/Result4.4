package com.example.consultation4.consultation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consultation4.R;
import com.example.consultation4.model.BV;
import com.example.consultation4.service.dbSqLite;
import java.util.ArrayList;
import java.util.List;

public class ConsultationActivity extends AppCompatActivity {

    private ListView listView;
    private BVAdapter bvAdapter;
    private TextView textLoggedInUser ;
    private SearchView searchView;
    private List<BV> bvList;
    private static final int MODIFY_BV_REQUEST_CODE = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        listView = findViewById(R.id.listView);

        textLoggedInUser = findViewById(R.id.textLoggedInUser);

        searchView = findViewById(R.id.recherche);

        // Récupérer les données de l'utilisateur connecté depuis l'intent
        Intent intent = getIntent();
        String loggedInUserId = intent.getStringExtra("User");

        textLoggedInUser.setText(loggedInUserId);

        // Récupérez tous les BV de la base de données
        dbSqLite dbHelper = new dbSqLite(getApplicationContext());
        List<BV> bvList = dbHelper.getAllBV();

        // Créer un adaptateur personnalisé
        bvAdapter = new BVAdapter(this, bvList);

        // Associer l'adaptateur au ListView
        listView.setAdapter(bvAdapter);

        // Ajouter un écouteur de clic pour le ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            BV selectedBV = bvList.get(position);

            // Créer une chaîne contenant les informations du BV sélectionné
            String codeBV = selectedBV.getCode_bv();
            String responsable = selectedBV.getResponsable();
            String Bc = selectedBV.getBureau_de_vote();
            String Centrevote = selectedBV.getCentre_de_vote();
            String votant = selectedBV.getVOTANT();
            String numPV = selectedBV.getNB_PV();
            String Vatomanankery = selectedBV.getV_Manankery();
            String Bulturne = selectedBV.getBULTURNE();
            String vmaty = selectedBV.getBULTNULL();
            String vfotsy = selectedBV.getBULTBLANC();
            String homme = selectedBV.getHOMME();
            String femme = selectedBV.getFEMME();

            // Obtenir les voix obtenues pour le BV sélectionné

            List<VoixObtenue> voixObtenuesList = dbHelper.getVoixObtenuesForBV(codeBV);

            /*String message = "Code BV: " + codeBV + "\nResponsable: " + responsable;

             */
            // Vous pouvez personnaliser le contenu du message avec toutes les informations que vous souhaitez afficher.
            // Créer une chaîne contenant les informations du BV sélectionné et les voix obtenues
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Code BV: ").append(codeBV).append("\nResponsable : ").append(responsable).append("\nBureau de vote : ")
                    .append(Bc).append("\nCentre de vote : ").append(Centrevote).append("\nTonga nandatsabato: ").append(votant)
                    .append("\nLaharana PV: ").append(numPV).append("\nVato manankery : ").append(Vatomanankery).append("\nTaratasy tao anaty vata : ")
                    .append(Bulturne).append("\nVato maty : ").append(vmaty).append("\nVato fotsy : ").append(vfotsy).append("\nLehilahy : ").append(homme)
                    .append("\nVehivavy : ").append(femme);
            // Ajouter les informations des voix obtenues
            for (VoixObtenue voixObtenue : voixObtenuesList) {
                messageBuilder.append("\nN°Ordre : ").append(voixObtenue.getNumcandidat())
                        .append(", Nombre de voix: ").append(voixObtenue.getNbvoix());
            }

            // Créer un AlertDialog pour afficher les informations du BV sélectionné
            AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationActivity.this);
            builder.setTitle("Momba ny BV");
            builder.setMessage(messageBuilder.toString());


            // Ajouter un bouton "Retour à la liste"
            builder.setPositiveButton("Hiverina", (dialog, which) -> {
                // Laissez vide, l'AlertDialog se fermera automatiquement lorsque l'utilisateur appuie sur le bouton.

            });


            builder.setNeutralButton("Modifier", (dialog, which) -> {
                Intent modifyIntent = new Intent(ConsultationActivity.this, ModifyBV.class);

                // Créer un Bundle pour stocker les données
                Bundle dataBundle = new Bundle();
                dataBundle.putString("codeBV", codeBV);
                dataBundle.putString("responsable", responsable);
                dataBundle.putString("Bureau de vote", Bc);
                dataBundle.putString("Centre de vote", Centrevote);
                dataBundle.putString("Tonga nandatsabato", votant);
                dataBundle.putString("Laharana PV", numPV);
                dataBundle.putString("Vato manankery", Vatomanankery);
                dataBundle.putString("Taratasy tao anaty vata", Bulturne);
                dataBundle.putString("Vato maty", vmaty);
                dataBundle.putString("Vato Fosty", vfotsy);
                dataBundle.putString("Lehilahy", homme);
                dataBundle.putString("Vehivavy", femme);

                // Ajouter les informations des voix obtenues au Bundle
                for (int i = 0; i < voixObtenuesList.size(); i++) {
                    VoixObtenue voixObtenue = voixObtenuesList.get(i);
                    dataBundle.putString("Numéro candidat " + i, voixObtenue.getNumcandidat());
                    dataBundle.putString("Nombre de voix " + i, voixObtenue.getNbvoix());
                }

                // Ajouter le Bundle à l'intent
                modifyIntent.putExtras(dataBundle);

                // Démarrer l'activité ModifyBV
                startActivity(modifyIntent);
            });




            // Afficher l'AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Ce callback est appelé lorsque l'utilisateur soumet la requête (par exemple, en appuyant sur Entrée).
                // Vous pouvez ajouter du code ici pour effectuer une action en fonction de la requête soumise,
                // mais généralement, pour une recherche en temps réel, on utilise plutôt onQueryTextChange().
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Ce callback est appelé lorsque l'utilisateur modifie le texte de la requête.
                // Vous pouvez ajouter du code ici pour filtrer la liste des BV en fonction de la requête saisie.
                // Mettez à jour l'adaptateur avec les résultats filtrés et rafraîchissez le ListView pour les afficher.

                String userInput = newText.toLowerCase();
                List<BV> filteredList = new ArrayList<>();

                // Vérifier si la requête de recherche n'est pas vide
                if (!userInput.isEmpty()) {
                    for (BV bv : bvList) {
                        if (bv.getCode_bv().toLowerCase().contains(userInput)) {
                            filteredList.add(bv);
                        }
                    }
                } else {
                    // Si la requête de recherche est vide, affichez la liste complète de tous les BV
                    filteredList.addAll(bvList);
                }

                // Mettez à jour l'adaptateur avec les résultats filtrés
                bvAdapter.setFilter(filteredList);

                // Rafraîchir le ListView pour afficher les résultats filtrés
                bvAdapter.notifyDataSetChanged();

                return true;
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel à la méthode de déconnexion
                logout();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MODIFY_BV_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Extraire les nouvelles valeurs de l'intent
            String modifiedCodeBV = data.getStringExtra("codeBV");
            String modifiedResponsable = data.getStringExtra("responsable");
            String modifiedBureauVote = data.getStringExtra("Bureau de vote");
            String modifiedCentreVote = data.getStringExtra("Centre de vote");
            String modifiedTongaNandatsabato = data.getStringExtra("Tonga nandatsabato");
            String modifiedLaharanaPV = data.getStringExtra("Laharana PV");
            String modifiedVatoManankery = data.getStringExtra("Vato manankery");
            String modifiedTaratasyTaoAnatyVata = data.getStringExtra("Taratasy tao anaty vata");
            String modifiedvatomaty = data.getStringExtra("Vato maty");


            // Mettre à jour les éléments de bvList avec les nouvelles valeurs
            for (BV bv : bvList) {
                if (bv.getCode_bv().equals(modifiedCodeBV)) {
                    bv.setResponsable(modifiedResponsable);
                    bv.setBureau_de_vote(modifiedBureauVote);
                    bv.setCentre_de_vote(modifiedCentreVote);
                    bv.setBULTNULL(modifiedTongaNandatsabato);
                    bv.setVOTANT(modifiedLaharanaPV);
                    bv.setNB_PV(modifiedLaharanaPV);
                    bv.setV_Manankery(modifiedVatoManankery);
                    bv.setBULTURNE(modifiedTaratasyTaoAnatyVata);
                    bv.setV_Manankery(modifiedvatomaty);
                    break;
                }
            }

            // Mettre à jour l'adaptateur pour refléter les nouvelles valeurs
            bvAdapter.notifyDataSetChanged();
        }
    }


    private void logout() {
        // Réinitialisez les informations d'identification et redirigez vers l'activité de connexion
        Object loggedInUserId = null;
        Object passInUserId = null;

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}