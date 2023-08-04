package com.example.consultation4.consultation;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consultation4.MainActivity;
import com.example.consultation4.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSaisie;
    private Button btnConsultaion;
    private TextView textLoggedInUser;

    public static String user;

    public static String pass;
    private static String communeUser;
    private static String districtUser;
    private static String regionUser;
    private static String id , codeCommune ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnSaisie = findViewById(R.id.btnSaisie);
        btnConsultaion= findViewById(R.id.btnConsultaion) ;
        textLoggedInUser = findViewById(R.id.textLoggedInUser);


        btnSaisie.setOnClickListener(this);
        btnConsultaion.setOnClickListener(this);

        Intent intent = getIntent();
        String loggedInUserId = intent.getStringExtra("User");
        String passInUserId = intent.getStringExtra("pass");

        // Afficher les valeurs dans le TextView
        textLoggedInUser.setText(loggedInUserId);
        user = loggedInUserId;
        pass = passInUserId;

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
    public void onClick(View view) {
        if (view.getId() == R.id.btnConsultaion) {
            openConsultaionActivity();
        } else if (view.getId() == R.id.btnSaisie) {
            openSaisieActivity(user, pass);
            Log.d(TAG, "Utilisateur connecté : " + user + ", Mot de passe : " + pass);
        }
    }



    private void openSaisieActivity(String username, String password) {

        try {
            // Obtenir la référence au fichier XML
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.user);

            // Créer un parseur XML
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            // Lire les informations de connexion
            NodeList userList = doc.getElementsByTagName("item");
            boolean istrouve = false;
            Element userElement = null;

            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    String userData = userNode.getTextContent();
                    String[] fields = userData.replaceAll("[()'\"]", "").split(",");

                    String idUser = fields[1].trim();
                    String pseudoUser = fields[4].trim();
                    String mdpUser = fields[5].trim();
                    String Commune = fields[10].trim();
                    String District = fields[8].trim();
                    String Region = fields[6].trim();
                    String codeCommuneUser = fields[11].trim();

                    if (username.equals(pseudoUser) && password.equals(mdpUser)) {
                        istrouve = true;
                        id = idUser ;
                        communeUser = Commune ;
                        districtUser = District;
                        regionUser = Region;
                        codeCommune = codeCommuneUser;

                        // Passer les données de l'utilisateur connecté à MenuActivity
                        Intent intent = new Intent(this, SaisieActivity.class);
                        intent.putExtra("User", user);
                        intent.putExtra("pass", pass);
                        intent.putExtra("communeUser", communeUser);
                        intent.putExtra("districtUser", districtUser);
                        intent.putExtra("regionUser", regionUser);
                        intent.putExtra("idUser", id);
                        intent.putExtra("codeCommune", codeCommune);
                        startActivity(intent);
                        break;


                    }
                }
            }


            /*if (istrouve) {
                // Authentification réussie
                Toast.makeText(this, "Hita", Toast.LENGTH_SHORT).show();


                // Passer les données de l'utilisateur connecté à MenuActivity
                Intent intent = new Intent(this, SaisieActivity.class);
                intent.putExtra("communeUser", communeUser);
                intent.putExtra("districtUser", districtUser);
                intent.putExtra("regionUser", regionUser);
                startActivity(intent);



            } else {
                // Authentification échouée
                Toast.makeText(this, "Identifiants invalides", Toast.LENGTH_SHORT).show();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openConsultaionActivity() {
        Intent intent = new Intent(this, ConsultationActivity.class);
        intent.putExtra("User", user);
        intent.putExtra("pass", pass);
        startActivity(intent);
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
