package com.example.consultation4.consultation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.consultation4.R;
import com.example.consultation4.adapter.CandidatAdapter;
import com.example.consultation4.adapter.SpinnerBVAdapter;
import com.example.consultation4.adapter.SpinnerCVAdapter;
import com.example.consultation4.adapter.SpinnerCommuneAdapter;
import com.example.consultation4.adapter.SpinnerDistrictAdapter;
import com.example.consultation4.adapter.SpinnerFokontanyAdapter;
import com.example.consultation4.adapter.SpinnerRegionAdapter;
import com.example.consultation4.model.BV;
import com.example.consultation4.model.CV;
import com.example.consultation4.model.Candidat;
import com.example.consultation4.model.Circonscription;
import com.example.consultation4.model.Commune;
import com.example.consultation4.model.District;
import com.example.consultation4.model.Fokontany;
import com.example.consultation4.model.Region;
import com.example.consultation4.model.Voter;
import com.example.consultation4.service.dbSqLite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

//import jp.wasabeef.blurry.Blurry;


public class SaisieActivity extends AppCompatActivity {

    static SaisieActivity saisieActivity;
    private Spinner spinner_region, spinner_district, spinner_commune, spinner_fokotany, spinner_centre_de_vote, spinner_bureau_de_vote;
    private PopupWindow popupWindow;
    private static boolean ondissmissclick = false;
    private ImageView cin_recto, cin_verso;
    private String  format, currentPhotoPath_cin_recto_reclamation, currentPhotoPath_cin_verso_reclamation, imageVerso, imageRecto;
    private Button first_validation, buttonRecto, buttonVerso;
    private String state = Environment.getExternalStorageState();
    private EditText I_S_Voasoratra, I_M_Natovana, I_T_Nandatsabato,
            V_Fotsy,V_Maty,V_Manankery,I_Lehilahy,I_Vehivavy,
            I_Karine_bileta_tokana_voaray, Laharana_Karine_voaray, bileta_tokana_anatiny_karine,
            Bileta_tokana_nampiasaina, Bileta_tokana_tsy_nampiasaina,NB_PV,bulturne, bultenleve,observdata_bv,Bultmeme,Bultinf;
    private List<Region> ListRegion;
    private List<District> ListDistrict;
    private List<Commune> ListCommune;
    private List<Fokontany> ListFokontany;
    private List<CV> ListCV;
    private List<BV> ListBV;
    private static final int STORAGE_CODE = 1000;
    private TextView textLoggedInUser , responsable,bultmeme,bultsurnombr,bultinf, inscritsliste;
    private static final int WRITE_PERMISSION_REQUEST_CODE = 101;
    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private static final int RETOUR_PRENDRE_PHOTO = 1;
    private static final int PERMISSION_CAMERA = 2;
    private int currentPhotoSide = 0;
    private CandidatAdapter candidatAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie);
        saisieActivity = this;

        textLoggedInUser = findViewById(R.id.textLoggedInUser);
        responsable = findViewById(R.id.responsable);
        // Récupérer la ListView du modal
        //ListView listViewModal = findViewById(R.id.listView);
        // Créer et ajouter l'en-tête à la ListView
        View headerView = getLayoutInflater().inflate(R.layout.header_listview, null);
        //slistViewModal.addHeaderView(headerView);
        // Récupérer les données de l'utilisateur connecté depuis l'intent
        Intent intent = getIntent();
        String loggedInUserId = intent.getStringExtra("User");
        String communeUser = intent.getStringExtra("communeUser");
        String districtUser = intent.getStringExtra("districtUser");
        String regionUser = intent.getStringExtra("regionUser");
        String codeCommune = intent.getStringExtra("codeCommune");

        textLoggedInUser.setText(loggedInUserId);
        responsable.setText(loggedInUserId);

        dbSqLite dbHelper = new dbSqLite(getApplicationContext());
        List<Candidat> candidatList = dbHelper.getAllCandidats();
        Log.d("CandidatListSize", "Size: " + candidatList.size());


        // Créer l'adaptateur et associer la liste des candidats avec la ListView
        candidatAdapter = new CandidatAdapter(SaisieActivity.this, candidatList);
        //listViewModal.setAdapter(candidatAdapter);

        // Check and request write permission when the activity is launched
        if (!hasWritePermission()) {
            requestWritePermission();
        }


        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appel à la méthode de déconnexion
                logout();
            }
        });

        dbSqLite DB = new dbSqLite(this);
        Circonscription circonscription = new Circonscription();

// Information circonscription
        this.ListRegion = DB.selectRegion();

        this.spinner_region = (Spinner) this.findViewById(R.id.spinner_region);

        SpinnerRegionAdapter regionAdapter = new SpinnerRegionAdapter(SaisieActivity.this,
                this.ListRegion,
                R.layout.dropdown,
                R.id.textViewLabel,
                R.id.textViewCode);
        this.spinner_region.setAdapter(regionAdapter);

        this.spinner_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Region regionSelected = (Region) spinner_region.getSelectedItem();

                ListDistrict = DB.selectDistrictFromRegion(regionSelected.getCode_region());

                spinner_district = (Spinner) SaisieActivity.this.findViewById(R.id.spinner_district);

                SpinnerDistrictAdapter districtAdapter = new SpinnerDistrictAdapter(SaisieActivity.this,
                        ListDistrict,
                        R.layout.dropdown,
                        R.id.textViewLabel,
                        R.id.textViewCode);

                spinner_district.setAdapter(districtAdapter);

                spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        District districtSelected = (District) spinner_district.getSelectedItem();
                        ListCommune = DB.selectCommuneFromDistrict(districtSelected.getCode_district());

                        spinner_commune = (Spinner) SaisieActivity.this.findViewById(R.id.spinner_commune);

                        SpinnerCommuneAdapter communeAdapter = new SpinnerCommuneAdapter(SaisieActivity.this,
                                ListCommune,
                                R.layout.dropdown,
                                R.id.textViewLabel,
                                R.id.textViewCode);

                        spinner_commune.setAdapter(communeAdapter);

                        spinner_commune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                Commune communeSelected = (Commune) spinner_commune.getSelectedItem();
                                ListFokontany = DB.selectFokotanyFromCommune(communeSelected.getCode_commune());

                                spinner_fokotany = (Spinner) SaisieActivity.this.findViewById(R.id.spinner_fokotany);

                                SpinnerFokontanyAdapter fokontanyAdapter = new SpinnerFokontanyAdapter(SaisieActivity.this,
                                        ListFokontany,
                                        R.layout.dropdown,
                                        R.id.textViewLabel,
                                        R.id.textViewCode);
                                spinner_fokotany.setAdapter(fokontanyAdapter);

                                spinner_fokotany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        Fokontany fokontanySelected = (Fokontany) spinner_fokotany.getSelectedItem();
                                        ListCV = DB.selectCvFromFokontany(fokontanySelected.getCode_fokontany());

                                        spinner_centre_de_vote = (Spinner) SaisieActivity.this.findViewById(R.id.spinner_centre_de_vote);

                                        SpinnerCVAdapter cvAdapter = new SpinnerCVAdapter(SaisieActivity.this,
                                                ListCV,
                                                R.layout.dropdown,
                                                R.id.textViewLabel,
                                                R.id.textViewCode);
                                        spinner_centre_de_vote.setAdapter(cvAdapter);

                                        spinner_centre_de_vote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                CV cvSelected = (CV) spinner_centre_de_vote.getSelectedItem();
                                                ListBV = DB.selectBvFromCv(cvSelected.getCode_cv());

                                                spinner_bureau_de_vote = (Spinner) SaisieActivity.this.findViewById(R.id.spinner_bureau_de_vote);

                                                SpinnerBVAdapter bvAdapter = new SpinnerBVAdapter(SaisieActivity.this,
                                                        ListBV,
                                                        R.layout.dropdown,
                                                        R.id.textViewLabel,
                                                        R.id.textViewCode);
                                                spinner_bureau_de_vote.setAdapter(bvAdapter);

                                                spinner_bureau_de_vote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        BV bvselected = (BV) spinner_bureau_de_vote.getSelectedItem();

                                                        circonscription.setRegion(regionSelected.getLabel_region());
                                                        circonscription.setDistrict(districtSelected.getLabel_district());
                                                        circonscription.setCommune(communeSelected.getLabel_commune());
                                                        circonscription.setFokontany(fokontanySelected.getLabel_fokontany() + "_" +fokontanySelected.getCode_fokontany());
                                                        int cdfkt = Integer.parseInt(fokontanySelected.getCode_fokontany());
                                                        circonscription.setCode_Fokontany(cdfkt);
                                                        circonscription.setCentre_de_vote(cvSelected.getLabel_cv());
                                                        circonscription.setBureau_de_vote(bvselected.getLabel_bv());

                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                /*
                                int communePosition = getCommunePosition(communeAdapter, communeUser);
                                if (communePosition != -1) {
                                    spinner_commune.setSelection(communePosition);
                                }*/
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        int districtPosition = getDistrictPosition(districtAdapter, districtUser);
                        if (districtPosition != -1) {
                            spinner_district.setSelection(districtPosition);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                // Préremplir le spinner région
                int regionPosition = getRegionPosition(regionAdapter, regionUser);
                if (regionPosition != -1) {
                    spinner_region.setSelection(regionPosition);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        // information utilisateur
        I_S_Voasoratra = findViewById(R.id.I_S_Voasoratra);
        I_M_Natovana = findViewById(R.id.I_M_Natovana);
        I_T_Nandatsabato = findViewById(R.id.I_T_Nandatsabato);
        V_Fotsy = findViewById(R.id.V_Fotsy);
        V_Maty = findViewById(R.id.V_Maty);
        V_Manankery = findViewById(R.id.V_Manankery);
        I_Lehilahy = findViewById(R.id.I_Lehilahy);
        I_Vehivavy = findViewById(R.id.I_Vehivavy);
        I_Karine_bileta_tokana_voaray = findViewById(R.id.I_Karine_bileta_tokana_voaray);
        Laharana_Karine_voaray = findViewById(R.id.Laharana_Karine_voaray);
        bileta_tokana_anatiny_karine = findViewById(R.id.bileta_tokana_anatiny_karine);
        Bileta_tokana_nampiasaina = findViewById(R.id.Bileta_tokana_nampiasaina);
        Bileta_tokana_tsy_nampiasaina = findViewById(R.id.Bileta_tokana_tsy_nampiasaina);
        NB_PV = findViewById(R.id.NB_PV);
        bulturne = findViewById(R.id.bulturne);
        bultenleve = findViewById(R.id.bultenleve);
        Bultinf = findViewById(R.id.Bultinf);
        Bultmeme = findViewById(R.id.Bultmeme);
        inscritsliste = findViewById(R.id.inscritsliste);

        // Ajouter un TextWatcher à chaque EditText
        Bultmeme.addTextChangedListener(new EditTextWatcher(Bultinf, bultenleve));
        Bultinf.addTextChangedListener(new EditTextWatcher(Bultmeme, bultenleve));
        bultenleve.addTextChangedListener(new EditTextWatcher(Bultmeme, Bultinf));

        Voter voter = new Voter();

        inscritsliste.setText("10");

        first_validation = findViewById(R.id.first_validation);
        first_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupérer les valeurs des champs
                String nbPV = NB_PV.getText().toString();
                String iSVoasoratra = I_S_Voasoratra.getText().toString();
                String iMNatovana = I_M_Natovana.getText().toString();
                String biletaTokanaNampiasaina = Bileta_tokana_nampiasaina.getText().toString();
                String bulturneStr = bulturne.getText().toString();
                String biletaTokanaTsyNampiasaina = Bileta_tokana_tsy_nampiasaina.getText().toString();
                String biletaTokanaAnatinyKarina = bileta_tokana_anatiny_karine.getText().toString();
                String laharanaKarinaVoaray = Laharana_Karine_voaray.getText().toString();
                String iKarinaBiletaTokanaVoaray = I_Karine_bileta_tokana_voaray.getText().toString();
                String iTNandatsabato = I_T_Nandatsabato.getText().toString();
                String vFotsyStr = V_Fotsy.getText().toString();
                String vMatyStr = V_Maty.getText().toString();
                String vManankeryStr = V_Manankery.getText().toString();
                String iVehivavyStr = I_Vehivavy.getText().toString();
                String iLehilahyStr = I_Lehilahy.getText().toString();
                String imitovy = Bultmeme.getText().toString();
                int tikoalaina = 0;

                // Vérifier que les champs obligatoires ne sont pas vides
                boolean isValid = true;
                if (nbPV.isEmpty()) {
                    NB_PV.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (iSVoasoratra.isEmpty()) {
                    I_S_Voasoratra.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (iMNatovana.isEmpty()) {
                    I_M_Natovana.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (biletaTokanaNampiasaina.isEmpty()) {
                    Bileta_tokana_nampiasaina.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (bulturneStr.isEmpty()) {
                    bulturne.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (biletaTokanaTsyNampiasaina.isEmpty()) {
                    Bileta_tokana_tsy_nampiasaina.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (biletaTokanaAnatinyKarina.isEmpty()) {
                    bileta_tokana_anatiny_karine.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (laharanaKarinaVoaray.isEmpty()) {
                    Laharana_Karine_voaray.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (iKarinaBiletaTokanaVoaray.isEmpty()) {
                    I_Karine_bileta_tokana_voaray.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (iTNandatsabato.isEmpty()) {
                    I_T_Nandatsabato.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (vFotsyStr.isEmpty()) {
                    V_Fotsy.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (vMatyStr.isEmpty()) {
                    V_Maty.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (vManankeryStr.isEmpty()) {
                    V_Manankery.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (iVehivavyStr.isEmpty()) {
                    I_Vehivavy.setError("Mila fenoiana !!");
                    isValid = false;
                }

                if (iLehilahyStr.isEmpty()) {
                    I_Lehilahy.setError("Mila fenoiana !!");
                    isValid = false;
                }

                // Vérifier que les calculs sont valides
                boolean isCalculationsValid = true;
                if (isValid) {

                    // Effectuer les calculs et vérifications
                    int iTNandatsabatoVal = Integer.parseInt(iTNandatsabato);
                    int iLehilahyVal = Integer.parseInt(iLehilahyStr);
                    int iVehivavyVal = Integer.parseInt(iVehivavyStr);
                    int bulturneVal = Integer.parseInt(bulturneStr);
                    int vFotsyVal = Integer.parseInt(vFotsyStr);
                    int vMatyVal = Integer.parseInt(vMatyStr);
                    int vManankeryVal = Integer.parseInt(vManankeryStr);
                    int biletaTokanaAnatinyKarinaVal = Integer.parseInt(biletaTokanaAnatinyKarina);

                    // Vérifier les autres logiques
                    //int vatoilaina = tikoalaina;

                    // I_T_Nandatsabato = I_Lehilahy + I_Vehivavy
                    if (iTNandatsabatoVal != (iLehilahyVal + iVehivavyVal)) {
                        isCalculationsValid = false;
                    }

                    // V_Manankery = bulturne - bultenleve - V_Fotsy - V_Maty
                    int vManankeryCalc = bulturneVal - vFotsyVal - vMatyVal;
                    int totblancs_nuls = vFotsyVal + vMatyVal;
                    String blancs_nuls = String.valueOf(totblancs_nuls);
                    if (vManankeryVal != vManankeryCalc) {
                        isCalculationsValid = false;
                    }

                    // Bileta_tokana_nampiasaina = bulturne
                    if (Integer.parseInt(biletaTokanaNampiasaina) != bulturneVal) {
                        isCalculationsValid = false;
                    }

                    // Bileta_tokana_tsy_nampiasaina = bileta_tokana_anatiny_karine - Bileta_tokana_nampiasaina
                    int biletaTokanaTsyNampiasainaCalc = biletaTokanaAnatinyKarinaVal - bulturneVal;
                    if (biletaTokanaTsyNampiasainaCalc != Integer.parseInt(biletaTokanaTsyNampiasaina)) {
                        isCalculationsValid = false;
                    }

                    /*les bult meme sup et inf
                    if (bulturneVal == iTNandatsabatoVal) {
                        // bulturne = I_T_Nandatsabato => bulturne doit être égal à Bultmeme
                        if (bulturneVal != vatoilaina) {
                            isCalculationsValid = false;
                        }
                    } else if (bulturneVal > iTNandatsabatoVal) {
                        // bulturne > I_T_Nandatsabato => bulturne - I_T_Nandatsabato = Bultsurnombr
                        int bultsurnombrCalc = bulturneVal - iTNandatsabatoVal;
                        if (vatoilaina != 0 || bultsurnombrCalc != vatoilaina) {
                            isCalculationsValid = false;
                        }
                    } else {
                        // bulturne < I_T_Nandatsabato => I_T_Nandatsabato - bulturne = Bultinf
                        int bultinfCalc = iTNandatsabatoVal - bulturneVal;
                        if (vatoilaina != 0 || bultinfCalc != vatoilaina) {
                            isCalculationsValid = false;
                        }
                    }

                     */

                    if (isCalculationsValid) {
                        // Si les champs obligatoires sont remplis et les calculs sont valides, afficher le AlertDialog pour confirmation
                        AlertDialog.Builder builder = new AlertDialog.Builder(SaisieActivity.this);
                        builder.setTitle("Validation");
                        builder.setMessage("Voamarina tsara ve ireo voasoratra ireo ?");
                        builder.setPositiveButton("Eny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Appeler la fonction de validation si l'utilisateur clique sur "Eny"
                                voter.setNB_PV(NB_PV.getText().toString());
                                voter.setI_KarineVoaray(I_Karine_bileta_tokana_voaray.getText().toString());
                                voter.setL_KarineVoaray(Laharana_Karine_voaray.getText().toString());
                                voter.setI_biletàTokanaKarine(bileta_tokana_anatiny_karine.getText().toString());
                                voter.setI_biletàTokanaTsyNampiasaina(Bileta_tokana_tsy_nampiasaina.getText().toString());
                                voter.setI_biletàTokanaNampiasaina(Bileta_tokana_nampiasaina.getText().toString());
                                voter.setINSCRITS(I_S_Voasoratra.getText().toString());
                                voter.setRAJOUT(I_M_Natovana.getText().toString());
                                voter.setBulturne(bulturne.getText().toString());
                                voter.setVOTANT(I_T_Nandatsabato.getText().toString());
                                voter.setBULTBLANC(V_Fotsy.getText().toString());
                                voter.setBULTNULL(V_Maty.getText().toString());
                                voter.setV_Manankery(V_Manankery.getText().toString());
                                voter.setBlanc_nuls(blancs_nuls);
                                voter.setFEMME(I_Vehivavy.getText().toString());
                                voter.setHOMME(I_Lehilahy.getText().toString());
                                voter.setEstanomalie("0");
                                voter.setObservdata_bv("null");
                                if (Bultinf.getText().toString().isEmpty() && bultenleve.getText().toString().isEmpty()) {
                                    voter.setBultmeme(Bultmeme.getText().toString());
                                    voter.setBultsurnombr("0");
                                    voter.setBultinf("0");
                                } else if (Bultmeme.getText().toString().isEmpty() && bultenleve.getText().toString().isEmpty()) {
                                    voter.setBultinf(Bultinf.getText().toString());
                                    voter.setBultmeme("0");
                                    voter.setBultsurnombr("0");
                                } else {
                                    voter.setBultsurnombr(bultenleve.getText().toString());
                                    voter.setBultmeme("0");
                                    voter.setBultinf("0");
                                }

                                viewModalListener(first_validation, voter, circonscription);
                            }
                        });
                        builder.setNegativeButton("Tsia", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        // Si des champs obligatoires sont vides ou les calculs sont invalides, afficher le toast approprié
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier les erreurs", Toast.LENGTH_LONG).show();

                        // Afficher les messages d'erreur dans l'AlertDialog
                        StringBuilder errorMessageBuilder = new StringBuilder();
                        if (iTNandatsabatoVal != (iLehilahyVal + iVehivavyVal)) {
                            errorMessageBuilder.append("- VOTANT différent de lahy + vavy \n");
                        }
                        if (vManankeryVal != vManankeryCalc) {
                            errorMessageBuilder.append("- Isan'ny vato manakery différent de BULTURNE - NOSARIHANA - MATY - FOTSY \n");
                        }

                        if (Integer.parseInt(biletaTokanaNampiasaina) != bulturneVal) {
                            errorMessageBuilder.append("- tatarasy tao anaty vata différent de bileta tokana nampiasaina \n");
                        }

                        if (biletaTokanaTsyNampiasainaCalc != Integer.parseInt(biletaTokanaTsyNampiasaina)) {
                            errorMessageBuilder.append("- bileta tsy nampiasaiana différent de bileta tonga - bileta nampiasaina \n");
                        }

                        if (errorMessageBuilder.length() > 0) {
                            AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(SaisieActivity.this);
                            errorDialogBuilder.setTitle("Hamarino tsara ireo voasoratra ireo");
                            errorDialogBuilder.setMessage(errorMessageBuilder.toString());
                            errorDialogBuilder.setPositiveButton("Eny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Effectuer des actions supplémentaires lorsque l'utilisateur clique sur "OK"
                                    voter.setNB_PV(NB_PV.getText().toString());
                                    voter.setI_KarineVoaray(I_Karine_bileta_tokana_voaray.getText().toString());
                                    voter.setL_KarineVoaray(Laharana_Karine_voaray.getText().toString());
                                    voter.setI_biletàTokanaKarine(bileta_tokana_anatiny_karine.getText().toString());
                                    voter.setI_biletàTokanaTsyNampiasaina(Bileta_tokana_tsy_nampiasaina.getText().toString());
                                    voter.setI_biletàTokanaNampiasaina(Bileta_tokana_nampiasaina.getText().toString());
                                    voter.setINSCRITS(I_S_Voasoratra.getText().toString());
                                    voter.setRAJOUT(I_M_Natovana.getText().toString());
                                    voter.setBulturne(bulturne.getText().toString());
                                    voter.setVOTANT(I_T_Nandatsabato.getText().toString());
                                    voter.setBULTBLANC(V_Fotsy.getText().toString());
                                    voter.setBULTNULL(V_Maty.getText().toString());
                                    voter.setV_Manankery(V_Manankery.getText().toString());
                                    voter.setBlanc_nuls(blancs_nuls);
                                    voter.setFEMME(I_Vehivavy.getText().toString());
                                    voter.setHOMME(I_Lehilahy.getText().toString());
                                    if (Bultinf.getText().toString().isEmpty() && bultenleve.getText().toString().isEmpty()) {
                                        voter.setBultmeme(Bultmeme.getText().toString());
                                        voter.setBultsurnombr("0");
                                        voter.setBultinf("0");
                                    } else if (Bultmeme.getText().toString().isEmpty() && bultenleve.getText().toString().isEmpty()) {
                                        voter.setBultinf(Bultinf.getText().toString());
                                        voter.setBultmeme("0");
                                        voter.setBultsurnombr("0");
                                    } else {
                                        voter.setBultsurnombr(bultenleve.getText().toString());
                                        voter.setBultmeme("0");
                                        voter.setBultinf("0");
                                    }
                                    // Mettre à jour voter.setEstanomalie à 1
                                    voter.setEstanomalie("1");

                                    // Mettre à jour voter.setObservdata_bv avec les erreurs affichées dans l'AlertDialog
                                    voter.setObservdata_bv(errorMessageBuilder.toString());

                                    viewModalListener(first_validation, voter, circonscription);
                                }
                            });
                            AlertDialog errorDialog = errorDialogBuilder.create();
                            errorDialog.show();
                        }
                    }
                } else {
                    // Si des champs obligatoires sont vides ou les calculs sont invalides, afficher le toast approprié
                    Toast.makeText(getApplicationContext(), "Veuillez vérifier les erreurs", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void viewModalListener(Button cardVote, Voter voter, Circonscription circonscription) {

        Log.e("Click ", "2");
        try {
            // Créer un nouvel objet PopupWindow
            PopupWindow popupWindow = new PopupWindow(SaisieActivity.this);

            // Créer une vue à afficher dans le PopupWindow
            View popupView = LayoutInflater.from(SaisieActivity.this).inflate(R.layout.activity_modal, null);

            // Ajouter la vue au PopupWindow
            popupWindow.setContentView(popupView);

            // Spécifier la taille du PopupWindow
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

            // Rendre le PopupWindow cliquable en dehors de la vue
            popupWindow.setOutsideTouchable(true);

             // image recto
            buttonRecto = popupView.findViewById(R.id.image_PV_recto);
            cin_recto = popupView.findViewById(R.id.cin_recto);

             // image verso
            buttonVerso = popupView.findViewById(R.id.image_PV_verso);
            cin_verso = popupView.findViewById(R.id.cin_verso);
            buttonRecto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPhotoSide = 0;
                    checkCameraPermission();
                }
            });

            buttonVerso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPhotoSide = 1;
                    checkCameraPermission();
                }
            });

            // Afficher le PopupWindow
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            final ImageView closeModal = popupView.findViewById(R.id.close_button);
            final Button validation = popupView.findViewById(R.id.validation);
            final TextView modale_region = popupView.findViewById(R.id.modale_region);
            final TextView modale_district = popupView.findViewById(R.id.modale_district);
            final TextView modale_commune = popupView.findViewById(R.id.modale_commune);
            final TextView modale_fokontany = popupView.findViewById(R.id.modale_fokontany);
            final TextView modale_centre_de_vote = popupView.findViewById(R.id.modale_centre_de_vote);
            final TextView modale_bureau_de_vote = popupView.findViewById(R.id.modale_bureau_de_vote);
            final TextView responsable = popupView.findViewById(R.id.responsable);

            final TextView modale_mitovy = popupView.findViewById(R.id.modale_mitovy);
            final TextView modale_bileta_tokana_anatiny_urne = popupView.findViewById(R.id.modale_bileta_tokana_anatiny_urne);
            final TextView modale_mihoatra = popupView.findViewById(R.id.modale_mihoatra);
            final TextView modale_latsaka = popupView.findViewById(R.id.modale_latsaka);

            final TextView modale_NB_PV = popupView.findViewById(R.id.modale_NB_PV);

            final TextView modale_voasoratra = popupView.findViewById(R.id.modale_voasoratra);
            final TextView modale_natovana = popupView.findViewById(R.id.modale_natovana);
            final TextView modale_nandatsabato = popupView.findViewById(R.id.modale_nandatsabato);
            final TextView modale_vatoFotsy = popupView.findViewById(R.id.modale_vatoFotsy);
            final TextView modale_vatoMaty = popupView.findViewById(R.id.modale_vatoMaty);
            final TextView modale_manankery = popupView.findViewById(R.id.modale_manankery);
            final TextView modale_vehivavy = popupView.findViewById(R.id.modale_vehivavy);
            final TextView modale_lehilahy = popupView.findViewById(R.id.modale_lehilahy);
            final TextView modale_I_Karine_bileta_tokana_voaray = popupView.findViewById(R.id.modale_I_Karine_bileta_tokana_voaray);
            final TextView modale_laharany_karine = popupView.findViewById(R.id.modale_laharany_karine);
            final TextView modale_I_bileta_tokana_tao_anatiny_karine = popupView.findViewById(R.id.modale_I_bileta_tokana_tao_anatiny_karine);
            final TextView modale_I_biletà_tokana_nampiasaina = popupView.findViewById(R.id.modale_I_biletà_tokana_nampiasaina);
            final TextView modale_I_biletà_tokana_tsy_nampiasaina = popupView.findViewById(R.id.modale_I_biletà_tokana_tsy_nampiasaina);

            responsable.setText(saisieActivity.responsable.getText().toString());
            modale_region.setText(circonscription.getRegion());
            modale_district.setText(circonscription.getDistrict());
            modale_commune.setText(circonscription.getCommune());
            modale_fokontany.setText(circonscription.getFokontany());
            modale_centre_de_vote.setText(circonscription.getCentre_de_vote());
            modale_bureau_de_vote.setText(circonscription.getBureau_de_vote());
            modale_I_Karine_bileta_tokana_voaray.setText(voter.getI_KarineVoaray());
            modale_laharany_karine.setText(voter.getL_KarineVoaray());
            modale_I_bileta_tokana_tao_anatiny_karine.setText(voter.getI_biletàTokanaKarine());
            modale_I_biletà_tokana_nampiasaina.setText(voter.getI_biletàTokanaNampiasaina());
            modale_I_biletà_tokana_tsy_nampiasaina.setText(voter.getI_biletàTokanaTsyNampiasaina());
            modale_NB_PV.setText(voter.getNB_PV());
            modale_voasoratra.setText(voter.getINSCRITS());
            modale_natovana.setText(voter.getRAJOUT());
            modale_nandatsabato.setText(voter.getVOTANT());
            modale_vatoFotsy.setText(voter.getBULTBLANC());
            modale_vatoMaty.setText(voter.getBULTNULL());
            modale_manankery.setText(voter.getV_Manankery());
            modale_vehivavy.setText(voter.getFEMME());
            modale_lehilahy.setText(voter.getHOMME());
            modale_mitovy.setText(voter.getBultmeme());
            modale_bileta_tokana_anatiny_urne.setText(voter.getBulturne());
            modale_mihoatra.setText(voter.getBultsurnombr());
            modale_latsaka.setText(voter.getBultinf());

            dbSqLite dbHelper = new dbSqLite(getApplicationContext());

            // Récupérer la ListView du modal
            ListView listViewModal = popupView.findViewById(R.id.listView_candidats_modal);
            //final EditText etVoixObtenues = popupView.findViewById(R.id.editTextVoixObtenues); // Récupérer l'EditText pour les voix obtenues

            // Créer et ajouter l'en-tête à la ListView
            View headerView = getLayoutInflater().inflate(R.layout.header_listview, null);
            listViewModal.addHeaderView(headerView);

            List<Candidat> candidatList = dbHelper.getAllCandidats();

            candidatAdapter = new CandidatAdapter(SaisieActivity.this, candidatList);


            listViewModal.setAdapter(candidatAdapter);



            validation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Validation !!", "RECLAMATION !!!");

                    // Initialize variables
                    boolean isInputValid = true;

                    // Obtenir la valeur numérique de modale_manankery
                    int modale_manankery_value = Integer.parseInt(modale_manankery.getText().toString());

                    int votesTotal = 0;
                    // Assurez-vous que vous avez ajouté tous les candidats à la liste voixObtenuesList avant d'appeler getTotalVoixObtenues().
                    int totalVoixObtenues = candidatAdapter.getTotalVoixObtenues();
                    votesTotal = totalVoixObtenues;
                    if (votesTotal != modale_manankery_value) {
                        isInputValid = false;

                        Toast.makeText(getApplicationContext(), "Ny isa hazon'ny candidat rehetra tokony mitovy @ vato manankery ", Toast.LENGTH_LONG).show();
                    }

                    Log.d("Tyyyyyyyyyy", "votes  Total: " + votesTotal);

                    // Check if the input is valid and proceed accordingly
                    if (isInputValid) {
                        Toast.makeText(getApplicationContext(), "Tafiditra", Toast.LENGTH_SHORT).show();

                        Intent intent = getIntent();
                        String loggedInUserId = intent.getStringExtra("User");
                        String idResponsable = intent.getStringExtra("idUser");
                        String codecommune = intent.getStringExtra("codeCommune");

                        ListAdapter adapter = listViewModal.getAdapter();

                        if (adapter instanceof HeaderViewListAdapter) {
                            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                            adapter = headerViewListAdapter.getWrappedAdapter();
                        }

                        if (adapter instanceof CandidatAdapter) {
                            CandidatAdapter candidatAdapter = (CandidatAdapter) adapter;
                            //List<Candidat> candidatListe = candidatAdapter.getItemList(); // Remplacez getItems() par getItemList()
                            // Maintenant vous pouvez parcourir la liste des candidats et faire ce que vous voulez
                        } else {
                            // Le bon adaptateur n'a pas été trouvé, gérez l'erreur ici
                            Toast.makeText(getApplicationContext(), "Le bon adaptateur n'a pas été trouvé", Toast.LENGTH_LONG).show();
                        }


                        int codefkt = circonscription.getCode_Fokontany();
                        String code_cv = String.valueOf(codefkt) + "01";
                        String code_bv = String.valueOf(codefkt) + "0101";
                        //int idBV = Integer.parseInt(idBVString);
                        Fokontany fokontanySelected = (Fokontany) spinner_fokotany.getSelectedItem();

                        String texte = fokontanySelected.getCode_commune().toString().trim();
                        String sousDossierNom = texte + "010101";
                        String fileName = texte+ "010101" + ".txt";

                        String format = "dd/MM/yyyy"; // Format jour/mois/année
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        String dateActuelle = sdf.format(new Date()); // Obtient la date actuelle au format souhaité

                        // Vérifier si le CODE_BV existe déjà dans la base de données
                        if (dbHelper.checkBVExists(code_bv)) {
                            Toast.makeText(getApplicationContext(), "BV efa vita saisie", Toast.LENGTH_LONG).show();
                            return; // Arrêtez l'exécution de la méthode, car le BV existe déjà
                        }

                        /* Définir le nom du fichier de sortie
                        String texte= modale_fokontany.getText().toString().trim();
                        String[] split = texte.split("_");

                        Log.d("split 1!!", split[0]);
                        Log.d("split 2!!", split[1]);
                        String fileName = split[1]+ "0101".toString().trim() + ".txt";
                        */
                        // Créer un objet File représentant le répertoire "DocumentTXT"
                        File documentTxtDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "DocumentTXT");

                        // Vérifier si le répertoire "DocumentTXT" existe, sinon le créer
                        if (!documentTxtDir.exists()) {
                            documentTxtDir.mkdirs();
                        }

                        // Obtenir le nom du sous-dossier
                        //String sousDossierNom = split[1] + "0101";

                        // Créer un objet File représentant le répertoire du sous-dossier
                        File sousDossierDir = new File(documentTxtDir, sousDossierNom);

                        /* Vérifier si le sous-dossier existe, sinon le créer
                        if (!sousDossierDir.exists()) {
                            sousDossierDir.mkdirs();
                        }
                        */


                        // Créer un objet File représentant le fichier dans le sous-dossier
                        File file = new File(sousDossierDir, fileName);

                        try {
                            String text = code_bv + "\n"
                                    + loggedInUserId + "\n"
                                    + code_cv + "\n"
                                    + circonscription.getBureau_de_vote() + "\n"
                                    + circonscription.getCentre_de_vote() + "\n"
                                    + voter.getINSCRITS() + "\n"
                                    + voter.getVOTANT() + "\n"
                                    + voter.getBlanc_nuls() + "\n"
                                    + voter.getNB_PV() + "\n"
                                    + voter.getV_Manankery() + "\n"
                                    + voter.getObservdata_bv() + "\n"
                                    + "0" + "\n"
                                    + voter.getRAJOUT() + "\n"
                                    + voter.getBulturne() + "\n"
                                    + voter.getBULTBLANC() + "\n"
                                    + dateActuelle + "\n"
                                    + voter.getHOMME() + "\n"
                                    + voter.getFEMME() + "\n"
                                    + voter.getBultsurnombr() + "\n"
                                    + voter.getBultmeme() + "\n"
                                    + voter.getBultinf() + "\n"
                                    + voter.getBultenleve() + "\n"
                                    + voter.getI_KarineVoaray() + "\n"
                                    + voter.getL_KarineVoaray() + "\n"
                                    + voter.getI_biletàTokanaKarine() + "\n"
                                    + voter.getI_biletàTokanaNampiasaina() + "\n"
                                    + voter.getI_biletàTokanaTsyNampiasaina();

                            BV bv = new BV();
                            bv.setCode_bv(code_bv);
                            bv.setResponsable(loggedInUserId.toString().trim());
                            bv.setCode_cv(code_cv);
                            bv.setBureau_de_vote(circonscription.getBureau_de_vote());
                            bv.setCentre_de_vote(circonscription.getCentre_de_vote());
                            bv.setINSCRITS(voter.getINSCRITS());
                            bv.setVOTANT(voter.getVOTANT());
                            bv.setBLANCS_NULS(voter.getBlanc_nuls());
                            bv.setNB_PV(voter.getNB_PV());
                            bv.setV_Manankery(voter.getV_Manankery());
                            bv.setOBSERVDATA_BV(voter.getObservdata_bv());
                            bv.setESTANOMALIE(voter.getEstanomalie());
                            bv.setETAT_BV("0");
                            bv.setRAJOUT(voter.getRAJOUT());
                            bv.setBULTURNE(voter.getBulturne());
                            bv.setBULTBLANC(voter.getBULTBLANC());
                            bv.setDATE_SAISIE(dateActuelle);
                            bv.setHOMME(voter.getHOMME());
                            bv.setFEMME(voter.getFEMME());
                            bv.setBULTSURNOMBR(voter.getBultsurnombr());
                            bv.setBULTMEME(voter.getBultmeme());
                            bv.setBULTINF(voter.getBultinf());
                            bv.setBULTENLEVE(voter.getBultenleve());
                            bv.setI_KarineVoaray(voter.getI_KarineVoaray());
                            bv.setL_KarineVoaray(voter.getL_KarineVoaray());
                            bv.setI_biletàTokanaKarine(voter.getI_biletàTokanaKarine());
                            bv.setI_biletàTokanaNampiasaina(voter.getI_biletàTokanaNampiasaina());
                            bv.setI_biletàTokanaTsyNampiasaina(voter.getI_biletàTokanaTsyNampiasaina());
                            // Chiffrer le texte avec Base64
                            String encryptedText = encryptWithBase64(text);
                                // Vérifier si le texte crypté n'est pas null
                                if (encryptedText != null) {
                                    // Écrire le texte crypté dans le fichier
                                    // Créer un FileWriter pour écrire dans le fichier
                                    FileWriter writer = new FileWriter(file);
                                    writer.write(encryptedText);

                                    // Fermer le FileWriter
                                    writer.flush();
                                    writer.close();
                                    boolean result = dbHelper.insertBV(bv);

                                    // Parcourez la liste et insérez les nouvelles valeurs dans la table "VOIXOBTENUE"

                                    for (Candidat candidat : candidatList) {
                                        String numCandidat = String.valueOf(candidat.getNumOrdre());
                                        String nbVoix = candidat.getVoixObtenue();
                                        String dhmajVoix = dateActuelle; // Supposons que vous avez une méthode pour obtenir la date actuelle au format souhaité

                                        // Appeler la méthode insertVoixObtenue pour enregistrer les nouvelles valeurs dans la table VOIXOBTENUE
                                        boolean insertionReussie = dbHelper.insertVoixObtenue(numCandidat, code_bv, nbVoix, dhmajVoix);

                                        if (insertionReussie) {
                                            Toast.makeText(getApplicationContext(), "Voix Enregistrées", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Échec de l'enregistrement des voix", Toast.LENGTH_LONG).show();
                                        }
                                    }


                                    if (result != false) {
                                        Toast.makeText(getApplicationContext(), "Enregistrement réussi", Toast.LENGTH_LONG).show();
                                        //Intent intent1 = new Intent(getApplicationContext(), MenuActivity.class);
                                        //startActivity(intent1);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Erreur lors de l'enregistrement", Toast.LENGTH_LONG).show();
                                    }
                                    // Afficher un message de succès
                                    //Toast.makeText(getApplicationContext(), "Fichier texte généré avec succès", Toast.LENGTH_LONG).show();
                                } else {
                                    // Gérer les erreurs lors du cryptage du texte
                                    Toast.makeText(getApplicationContext(), "Erreur lors du cryptage du texte", Toast.LENGTH_LONG).show();
                                }
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Gérer les erreurs lors de la création du fichier
                            Toast.makeText(getApplicationContext(), "Erreur lors de la génération du fichier texte", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        // Gérer le cas où l'entrée n'est pas valide
                    }
                }

            });

            final View back = saisieActivity.getWindow().getDecorView().getRootView();

            closeModal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });

            cardVote.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("LongLogTag")
                @Override
                public void onClick(View view) {

                    boolean VNB_PV = true;
                    boolean VI_S_Voasoratra = true;
                    boolean VI_M_Natovana = true;
                    boolean VI_T_Nandatsabato = true;
                    boolean VV_Fotsy = true;
                    boolean VV_Maty = true;
                    boolean VV_Manankery = true;
                    boolean VLera = true;
                    boolean VI_Vehivavy = true;
                    boolean VI_Lehilahy = true;
                    boolean VI_Karine_bileta_tokana_voaray = true;
                    boolean VLaharana_Karine_voaray = true;
                    boolean Vbileta_tokana_anatiny_karine = true;
                    boolean VBileta_tokana_nampiasaina = true;
                    boolean VBileta_tokana_tsy_nampiasaina = true;

                    //PV
                    if (NB_PV.getText().toString().length() != 0) {
                        voter.setI_KarineVoaray(NB_PV.getText().toString());
                    } else {
                        NB_PV.setError("Mila fenoiana !!");
                        VNB_PV = false;
                    }

                    //isan'ny karine bileta tokana voaray
                    if (I_Karine_bileta_tokana_voaray.getText().toString().length() != 0) {
                        voter.setI_KarineVoaray(I_Karine_bileta_tokana_voaray.getText().toString());
                    } else {
                        I_Karine_bileta_tokana_voaray.setError("Mila fenoiana !!");
                        VI_Karine_bileta_tokana_voaray = false;
                    }
                    //laharanan'ny karine voaray
                    if (Laharana_Karine_voaray.getText().toString().length() != 0) {
                        voter.setL_KarineVoaray(Laharana_Karine_voaray.getText().toString());
                    } else {
                        Laharana_Karine_voaray.setError("Mila fenoiana !!");
                        VLaharana_Karine_voaray = false;
                    }

                    //bileta tokana anatin'ny karine
                    if (bileta_tokana_anatiny_karine.getText().toString().length() != 0) {
                        voter.setI_biletàTokanaKarine(bileta_tokana_anatiny_karine.getText().toString());
                    } else {
                        bileta_tokana_anatiny_karine.setError("Mila fenoiana !!");
                        Vbileta_tokana_anatiny_karine = false;
                    }

                    //bileta tokana nampiasaina
                    if (Bileta_tokana_nampiasaina.getText().toString().length() != 0) {
                        voter.setI_biletàTokanaNampiasaina(Bileta_tokana_nampiasaina.getText().toString());
                    } else {
                        Bileta_tokana_nampiasaina.setError("Mila fenoiana !!");
                        VBileta_tokana_nampiasaina = false;
                    }

                    //bileta tokana tsy nampiasaina
                    if (Bileta_tokana_tsy_nampiasaina.getText().toString().length() != 0) {
                        voter.setI_biletàTokanaTsyNampiasaina(Bileta_tokana_tsy_nampiasaina.getText().toString());
                    } else {
                        Bileta_tokana_tsy_nampiasaina.setError("Mila fenoiana !!");
                        VBileta_tokana_tsy_nampiasaina = false;
                    }

                    //isan'ny mpifidy voasoratra
                    if (I_S_Voasoratra.getText().toString().length() != 0) {
                        voter.setINSCRITS(I_S_Voasoratra.getText().toString());
                    } else {
                        I_S_Voasoratra.setError("Mila fenoiana !!");
                        VI_S_Voasoratra = false;
                    }

                    //isan'ny mpifidy natovana

                    if (I_M_Natovana.getText().toString().length() != 0) {
                        voter.setRAJOUT(I_M_Natovana.getText().toString());
                    } else {
                        I_M_Natovana.setError("Mila fenoiana !!");
                        VI_M_Natovana = false;
                    }

                    //nandatsabato
                    if (I_T_Nandatsabato.getText().toString().length() != 0) {
                        voter.setVOTANT(I_T_Nandatsabato.getText().toString());

                        // Controler que I_S_Voasoratra >= I_T_Nandatsabato
                        if (I_S_Voasoratra.getText().toString().length() != 0) {
                            int I_S_Voasoratra_val = Integer.parseInt(I_S_Voasoratra.getText().toString());
                            int I_T_Nandatsabato_val = Integer.parseInt(I_T_Nandatsabato.getText().toString());
                            if (I_S_Voasoratra_val < I_T_Nandatsabato_val) {
                                I_T_Nandatsabato.setError("Mpifidy Nandatsabato tokony tsy mihoatra ny mpifidy Voasoratra!!");
                                VI_T_Nandatsabato = false;
                            }
                        }

                        // Calculer la somme de V_Fotsy, V_Maty et V_Manankery et vérifier qu'elle est égale à I_T_Nandatsabato

                        //vato fotsy
                        if (V_Fotsy.getText().toString().length() != 0) {
                            voter.setBULTBLANC(V_Fotsy.getText().toString());
                        } else {
                            V_Fotsy.setError("Mila fenoiana !!");
                            VV_Fotsy = false;
                        }

                        //vato maty
                        if (V_Maty.getText().toString().length() != 0) {
                            voter.setBULTNULL(V_Maty.getText().toString());
                        } else {
                            V_Maty.setError("Mila fenoiana !!");
                            VV_Maty = false;
                        }

                        //vato manankery
                        if (V_Manankery.getText().toString().length() != 0) {
                            voter.setV_Manankery(V_Manankery.getText().toString());
                        } else {
                            V_Manankery.setError("Mila fenoiana !!");
                            VV_Manankery = false;
                        } {

                            int V_Fotsy_val = Integer.parseInt(V_Fotsy.getText().toString());
                            int V_Maty_val = Integer.parseInt(V_Maty.getText().toString());
                            int V_Manankery_val = Integer.parseInt(V_Manankery.getText().toString());
                            int I_T_Nandatsabato_val = Integer.parseInt(I_T_Nandatsabato.getText().toString());
                            int total_votes = V_Fotsy_val + V_Maty_val + V_Manankery_val;
                            if (total_votes != I_T_Nandatsabato_val) {
                                V_Fotsy.setError("Vato_Fotsy + Vato_Maty + Vato_Manankery tokony mira @ Nandatsabato !!");
                                V_Maty.setError("Vato_Fotsy + Vato_Maty + Vato_Manankery tokony mira @ Nandatsabato !!");
                                V_Manankery.setError("Vato_Fotsy + Vato_Maty + Vato_Manankery tokony mira @ Nandatsabato !!");
                                VI_T_Nandatsabato = false;
                            }
                        }
                    } else {
                        I_T_Nandatsabato.setError("Mila fenoiana !!");
                        VI_T_Nandatsabato = false;
                    }
                    //vehivavy
                    if (I_Vehivavy.getText().toString().length() != 0) {
                        voter.setFEMME(I_Vehivavy.getText().toString());
                    } else {
                        I_Vehivavy.setError("Mila fenoiana !!");
                        VI_Vehivavy = false;
                    }

                    //Lehilahy
                    if (I_Lehilahy.getText().toString().length() != 0) {
                        voter.setHOMME(I_Lehilahy.getText().toString());
                    } else {
                        I_Lehilahy.setError("Mila fenoiana !!");
                        VI_Lehilahy = false;
                    }

                    //nandatsabato
                    if (I_T_Nandatsabato.getText().toString().length() != 0) {
                        String nandatsabato = I_T_Nandatsabato.getText().toString();
                        int iVehivavy = 0;
                        int iLehilahy = 0;
                        try {
                            iVehivavy = Integer.parseInt(I_Vehivavy.getText().toString());
                        } catch (NumberFormatException e) {
                            I_Vehivavy.setError("Mila fenoiana !!");
                            VI_Vehivavy = false;
                        }
                        try {
                            iLehilahy = Integer.parseInt(I_Lehilahy.getText().toString());
                        } catch (NumberFormatException e) {
                            I_Lehilahy.setError("Mila fenoiana !!");
                            VI_Lehilahy = false;
                        }
                        if (iVehivavy + iLehilahy != Integer.parseInt(nandatsabato)) {
                            I_Vehivavy.setError("Mpifidy lahy sy mpifidy vavy tokony mira @ nandatsa-bato !!");
                            I_Lehilahy.setError("Mpifidy lahy sy mpifidy vavy tokony mira @ nandatsa-bato !!");
                            VI_Vehivavy = false;
                            VI_Lehilahy = false;
                        } else {
                            voter.setVOTANT(nandatsabato);
                        }
                    } else {
                        I_T_Nandatsabato.setError("Mila fenoiana ny anarana !!");
                        VI_T_Nandatsabato = false;
                    }

                    if (VI_S_Voasoratra && VI_M_Natovana && VI_T_Nandatsabato && VV_Fotsy
                            && VV_Maty && VV_Manankery && VI_Lehilahy
                            && VI_Vehivavy && VI_Karine_bileta_tokana_voaray && VLaharana_Karine_voaray
                            && Vbileta_tokana_anatiny_karine && VBileta_tokana_nampiasaina && VBileta_tokana_tsy_nampiasaina && VNB_PV) {
                        Toast.makeText(getApplicationContext(), "Avereno tsindrina ", Toast.LENGTH_LONG).show();
                        viewModalListener(first_validation, voter, circonscription);
                    } else {
                        Toast.makeText(getApplicationContext(), "Hamarino fa mbola misy diso", Toast.LENGTH_LONG).show();
                    }
                    modale_region.setText(circonscription.getRegion());
                    modale_district.setText(circonscription.getDistrict());
                    modale_commune.setText(circonscription.getCommune());
                    modale_fokontany.setText(circonscription.getFokontany());
                    modale_centre_de_vote.setText(circonscription.getCentre_de_vote());
                    modale_bureau_de_vote.setText(circonscription.getBureau_de_vote());
                    modale_NB_PV.setText(saisieActivity.NB_PV.getText().toString());
                    modale_I_Karine_bileta_tokana_voaray.setText(saisieActivity.I_Karine_bileta_tokana_voaray.getText().toString());
                    modale_laharany_karine.setText(saisieActivity.Laharana_Karine_voaray.getText().toString());
                    modale_I_bileta_tokana_tao_anatiny_karine.setText(saisieActivity.bileta_tokana_anatiny_karine.getText().toString());
                    modale_I_biletà_tokana_nampiasaina.setText(saisieActivity.Bileta_tokana_nampiasaina.getText().toString());
                    modale_I_biletà_tokana_tsy_nampiasaina.setText(saisieActivity.Bileta_tokana_tsy_nampiasaina.getText().toString());
                    modale_voasoratra.setText(saisieActivity.I_S_Voasoratra.getText().toString());
                    modale_natovana.setText(saisieActivity.I_M_Natovana.getText().toString());
                    modale_nandatsabato.setText(saisieActivity.I_T_Nandatsabato.getText().toString());
                    modale_vatoFotsy.setText(saisieActivity.V_Fotsy.getText().toString());
                    modale_vatoMaty.setText(saisieActivity.V_Maty.getText().toString());
                    modale_manankery.setText(saisieActivity.V_Manankery.getText().toString());
                    modale_vehivavy.setText(saisieActivity.I_Vehivavy.getText().toString());
                    modale_lehilahy.setText(saisieActivity.I_Lehilahy.getText().toString());
                    responsable.setText(saisieActivity.responsable.getText().toString());
                    modale_bileta_tokana_anatiny_urne.setText(voter.getBulturne().toString().trim());
                    modale_mitovy.setText(voter.getBultmeme().toString().trim());
                    modale_mihoatra.setText(voter.getBultsurnombr().toString().trim());
                    modale_latsaka.setText(voter.getBultinf().toString().trim());

                    if (popupWindow.isShowing()) {
                        back.setBackgroundColor(saisieActivity.getResources().getColor(R.color.colorWhite));
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAtLocation(back, Gravity.CENTER, 0, 0);
                      //  popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        back.setAlpha(0.8F);
                        //Blurry.with(back.getContext()).onto((ViewGroup) back);
                    }
                }
            });

            //popupWindow.setElevation(0F);
            popupWindow.setContentView(popupView);
            popupWindow.setOutsideTouchable(true);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //Blurry.delete((ViewGroup) back);
                    back.setAlpha(1F);
                    saisieActivity.getResources().updateConfiguration(saisieActivity.getResources().getConfiguration(), saisieActivity.getResources().getDisplayMetrics());
                }
            });
            //popupWindow.setBackgroundDrawable(null);
            popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            popupWindow.setFocusable(true);
            popupWindow.update();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String encryptWithBase64(String text) {
        // Convertir le texte en bytes
        byte[] textBytes = text.getBytes();

        // Chiffrer le texte en Base64
        return Base64.encodeToString(textBytes, Base64.DEFAULT);
    }



    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        }
    }

    private void prendreUnePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                spinner_fokotany = (Spinner) SaisieActivity.this.findViewById(R.id.spinner_fokotany);
                Fokontany fokontanySelected = (Fokontany) spinner_fokotany.getSelectedItem();

                String texte = fokontanySelected.getCode_commune().toString().trim();
                String sousDossierNom = texte + "010101";
                File photoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "DocumentTXT");

                // Vérifier si le répertoire "DocumentTXT" existe, sinon le créer
                if (!photoDir.exists()) {
                    photoDir.mkdirs();
                }

                // Créer un objet File représentant le répertoire du sous-dossier
                File sousDossierDir = new File(photoDir, sousDossierNom);

                // Vérifier si le sous-dossier existe, sinon le créer
                if (!sousDossierDir.exists()) {
                    sousDossierDir.mkdirs();
                }
                String photoFileName = texte+ "010101_" + date + ".jpg";
                File photoFile = new File(sousDossierDir, photoFileName);

                if (currentPhotoSide == 0) {
                    currentPhotoPath_cin_recto_reclamation = photoFile.getAbsolutePath();
                } else {
                    currentPhotoPath_cin_verso_reclamation = photoFile.getAbsolutePath();
                }

                Uri photoUri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, RETOUR_PRENDRE_PHOTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Dans votre onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RETOUR_PRENDRE_PHOTO && resultCode == RESULT_OK) {
            if (currentPhotoSide == 0 && currentPhotoPath_cin_recto_reclamation != null) {
                loadImageIntoImageView(currentPhotoPath_cin_recto_reclamation, cin_recto);
                currentPhotoPath_cin_recto_reclamation = null; // Réinitialiser le chemin
            } else if (currentPhotoSide == 1 && currentPhotoPath_cin_verso_reclamation != null) {
                loadImageIntoImageView(currentPhotoPath_cin_verso_reclamation, cin_verso);
                currentPhotoPath_cin_verso_reclamation = null; // Réinitialiser le chemin
            } else {
                Toast.makeText(getApplicationContext(), "Erreur lors de la capture de l'image.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadImageIntoImageView(String imagePath, ImageView imageView) {
        Glide.with(this)
                .load(imagePath)
                .into(imageView);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                prendreUnePhoto();
            } else {
                Toast.makeText(getApplicationContext(), "Autorisation refusée", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void saveImage(byte[] byteArray, String fileName) throws IOException {

        spinner_fokotany = (Spinner) SaisieActivity.this.findViewById(R.id.spinner_fokotany);
        Fokontany fokontanySelected = (Fokontany) spinner_fokotany.getSelectedItem();

        String texte = fokontanySelected.getCode_commune().toString().trim();

        String sousDossierNom = texte + "010101";

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

        File imageFile = new File(sousDossierDir, fileName + ".JPEG");
        File image = File.createTempFile(fileName,".JPEG",sousDossierDir);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static Bitmap resizeImage(Bitmap realImage, float maxImageSize,
                                     boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    private int getRegionPosition(SpinnerRegionAdapter adapter, String regionUser) {
        for (int i = 0; i < adapter.getCount(); i++) {
            Region region = (Region) adapter.getItem(i);
            if (region.getLabel_region().equals(regionUser)) {
                return i;
            }
        }
        return -1;
    }

    private int getDistrictPosition(SpinnerDistrictAdapter adapter, String districtUser) {
        for (int i = 0; i < adapter.getCount(); i++) {
            District district = (District) adapter.getItem(i);
            if (district.getLabel_district().equals(districtUser)) {
                return i;
            }
        }
        return -1;
    }

    private int getCommunePosition(SpinnerCommuneAdapter adapter, String communeUser) {
        for (int i = 0; i < adapter.getCount(); i++) {
            Commune commune = (Commune) adapter.getItem(i);
            if (commune.getLabel_commune().equals(communeUser)) {
                return i;
            }
        }
        return -1;
    }

    private void logout() {
        // Réinitialisez les informations d'identification et redirigez vers l'activité de connexion
        Object loggedInUserId = null;
        Object passInUserId = null;

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private boolean hasWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }


    private void requestWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasWritePermission()) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Mila alalana hanoratana")
                            .setMessage("Mila alalana hanoratra ity fampiharana ity mba hitahiry ilay rakitra.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            WRITE_PERMISSION_REQUEST_CODE);
                                }
                            })
                            .setNegativeButton("hanafoana", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                } else {
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    // TextWatcher personnalisé pour gérer l'état des autres EditText
    private class EditTextWatcher implements TextWatcher {

        private EditText[] editTexts;

        EditTextWatcher(EditText... editTexts) {
            this.editTexts = editTexts;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Désactiver les autres EditText dès que l'un d'eux est rempli
            if (Bultmeme.isFocused() && !Bultmeme.getText().toString().isEmpty()) {
                Bultinf.setEnabled(false);
                bultenleve.setEnabled(false);
            } else if (Bultinf.isFocused() && !Bultinf.getText().toString().isEmpty()) {
                Bultmeme.setEnabled(false);
                bultenleve.setEnabled(false);
            } else if (bultenleve.isFocused() && !bultenleve.getText().toString().isEmpty()) {
                Bultmeme.setEnabled(false);
                Bultinf.setEnabled(false);
            } else {
                // Activer tous les EditText si aucun n'est rempli
                Bultmeme.setEnabled(true);
                Bultinf.setEnabled(true);
                bultenleve.setEnabled(true);
            }
        }
    }

}