package com.example.consultation4.consultation;

import android.annotation.SuppressLint;
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
                    .append(Bulturne).append("\nVato maty : ").append(vmaty);
            // Ajouter les informations des voix obtenues
            for (VoixObtenue voixObtenue : voixObtenuesList) {
                messageBuilder.append("\nNuméro candidat: ").append(voixObtenue.getNumcandidat())
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

    private void logout() {
        // Réinitialisez les informations d'identification et redirigez vers l'activité de connexion
        Object loggedInUserId = null;
        Object passInUserId = null;

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}