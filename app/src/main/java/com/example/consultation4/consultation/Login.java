package com.example.consultation4.consultation;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consultation4.R;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Login extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    private static String loggedInUserId;
    private static String passInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = username.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                // Appel à la méthode de vérification de l'authentification
                authenticate(enteredUsername, enteredPassword);
            }
        });
    }

    private void authenticate(String username, String password) {
        try {
            // Obtenir la référence au fichier XML
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.user);

            // Créer un parseur XML
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            // Lire les informations de connexion
            NodeList userList = doc.getElementsByTagName("item");
            boolean isAuthenticated = false;

            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    String userData = userNode.getTextContent();
                    String[] fields = userData.replaceAll("[()'\"]", "").split(",");

                    String pseudoUser = fields[4].trim();
                    String mdpUser = fields[5].trim();

                    if (username.equals(pseudoUser) && password.equals(mdpUser)) {
                        isAuthenticated = true;
                        break;
                    }
                }
            }

            if (isAuthenticated) {
                // Authentification réussie
                Toast.makeText(Login.this, "Authentification réussie", Toast.LENGTH_SHORT).show();

                loggedInUserId = username;
                passInUserId = password;

                // Redirection vers l'activité SaisieActivity
                Intent intent = new Intent(Login.this, MenuActivity.class);
                intent.putExtra("User", loggedInUserId);
                intent.putExtra("pass", passInUserId);
                startActivity(intent);
                finish();
            } else {
                // Authentification échouée
                Toast.makeText(Login.this, "Identifiants invalides", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
