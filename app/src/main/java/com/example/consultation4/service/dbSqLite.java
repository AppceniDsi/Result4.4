package com.example.consultation4.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

//import com.android.consultation.R;
//import com.android.consultation.consultation.VoixObtenue;
import com.example.consultation4.R;
import com.example.consultation4.consultation.VoixObtenue;
import com.example.consultation4.model.BV;
import com.example.consultation4.model.CV;
import com.example.consultation4.model.Candidat;
import com.example.consultation4.model.Commune;
import com.example.consultation4.model.District;
import com.example.consultation4.model.Fokontany;
import com.example.consultation4.model.Region;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class dbSqLite extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "consultation.db";
    private static final int DB_VERSION = 1;

    /*---------------------------------------------------------------------------------------
                                    TABLE LOCALISATION
----------------------------------------------------------------------------------------*/
    private static final String region_label = "region_label";
    private static final String code_region = "code_region";
    private static final String district_label = "district_label";
    private static final String code_district = "code_district";
    private static final String commune_label = "commune_label";
    private static final String code_commune = "code_commune";
    private static final String fokontany_label = "fokontany_label";
    private static final String code_fokontany = "code_fokontany";
    private static final String cv_label = "cv_label";
    private static final String code_cv = "code_cv";
    private static final String bv_label = "bv_label";
    private static final String code_bv = "code_bv";

    public static final String TABLE_LOCALISATION = "Localisation";
    /*---------------------------------------------------------------------------------------
                                            TABLE USER
    ----------------------------------------------------------------------------------------*/
    private static final String IDUSER = "idUser";
    private static final String NOMUSER = "nomUser";
    private static final String PRENOMUSER = "prenomUser";
    private static final String ROLE = "role";
    private static final String PSEUDO = "pseudo";
    private static final String MOTDEPASSE = "motdepasse";
    private static final String REGIONUSER = "regionUser";
    private static final String USER_CODEREGION = "code_region";
    private static final String DISTRICTUSER = "districtUser";
    private static final String USER_CODEDISTRICT = "code_district";
    private static final String COMMUNEUSER = "communeUser";
    private static final String USER_CODECOMMUNE = "code_commune";
    private static final String NBSAISI = "nbSaisi";

    public static final String TABLE_User = "User";

    /*---------------------------------------------------------------------------------------
                                            TABLE BV
    ----------------------------------------------------------------------------------------*/
    private static final String CODE_BV  = "CODE_BV";
    private static final String NUM_USERINFO  = "NUM_USERINFO";
    private static final String CODE_CV = "CODE_CV";
    private static final String LIBELLE_BV  = "LIBELLE_BV";
    private static final String EMPLACEMENT  = "EMPLACEMENT";
    private static final String INSCRITSLISTE = "INSCRITSLISTE";
    private static final String INSCRITS = "INSCRITS";
    private static final String VOTANT = "VOTANT";
    private static final String BLANCS_NULS = "BLANCS_NULS";
    private static final String NUMPV  = "NUMPV";
    private static final String SEXPRIMES_BV = "SEXPRIMES_BV";
    private static final String OBSERVDATA_BV = "OBSERVDATA_BV";
    private static final String ETAT_BV = "ETAT_BV";
    private static final String ETAT_BVMODIFIE = "ETAT_BVMODIFIE";
    private static final String DHMAJ = "DHMAJ ";
    private static final String RAJOUT = "RAJOUT";
    private static final String ESTANOMALIE  = "ESTANOMALIE";
    private static final String RAJOUTPV ="RAJOUTPV";
    private static final String BULTURNE ="BULTURNE";
    private static final String BULTDESINSUF ="BULTDESINSUF";
    private static final String BULTSP ="BULTSP";
    private static final String BULTAUTRMOTIF ="BULTAUTRMOTIF";
    private static final String BULTNONSIGNDEU ="BULTNONSIGNDEU";
    private static final String BULTNULL = "BULTNULL";
    private static final String BULTBLANC   = "BULTBLANC";
    private static final String ACORRIGER ="ACORRIGER";
    private static final String DATE_SAISIE ="DATE_SAISIE";
    private static final String STATUT ="STATUT";
    private static final String HOMME = "HOMME";
    private static final String FEMME = "FEMME";
    private static final String BULTSURNOMBR ="BULTSURNOMBR";
    private static final String BULTMEME ="BULTMEME";
    private static final String BULTINF = "BULTINF";
    private static final String BULTENLEVE = "BULTENLEVE";
    private static final String CARNETRECU = "CARNETRECU";
    private static final String NUMCARNETRECU = "NUMCARNETRECU";
    private static final String BULTRECU = "BULTRECU";
    private static final String BULTRECUUT = "BULTRECUUT";
    private static final String BULTRECUNUT = "BULTRECUNUT";
    private static final String NUMBV = "NUMBV";
    private static final String MARQUAGE = "MARQUAGE";


    public static final String TABLE_BV = "BV";

    /*---------------------------------------------------------------------------------------
                                    VOIXOBTENU
----------------------------------------------------------------------------------------*/
    private static final String NUM_CANDIDAT = "NUM_CANDIDAT";
    private static final String CODE_BV_VOIX = "CODE_BV";
    private static final String NBVOIX = "NBVOIX";
    private static final String DHMAJ_VOIX = "DHMAJ_VOIX ";
    private static final String NUM_USERINFO_VOIX = "NUM_USERINFO";

    public static final String TABLE_VOIXOBTENUE = "VOIXOBTENUE";

    /*---------------------------------------------------------------------------------------
                                        CANDIDAT
    ----------------------------------------------------------------------------------------*/
    public static final String TABLE_CANDIDAT = "CANDIDAT";
    public static final String NUM_ORDRE = "NUM_ORDRE";
    public static final String LOGO = "LOGO";
    public static final String NOM_PRENOMS = "NOM_PRENOMS";
    public static final String ENTITE = "ENTITE";
    public static final String VOIXOBTENUE = "VOIXOBTENUE";

    public dbSqLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "CREATE TABLE " + TABLE_User + " ( " + IDUSER + " INTEGER, " + NOMUSER + " TEXT, " +
                PRENOMUSER + " TEXT, " + ROLE + " TEXT, " + PSEUDO + " TEXT, " + MOTDEPASSE + " TEXT, " + REGIONUSER + " TEXT, " +
                USER_CODEREGION + " TEXT, " + DISTRICTUSER + " TEXT, " + USER_CODEDISTRICT + " TEXT, " + COMMUNEUSER + " TEXT, " + USER_CODECOMMUNE + " TEXT, " + NBSAISI + " INT)";

        String query2 = "CREATE TABLE " + TABLE_LOCALISATION + "(" + region_label + " TEXT, " + code_region + " TEXT, " +
                district_label + " TEXT, " + code_district + " TEXT, " + commune_label + " TEXT, " + code_commune + " TEXT, " +
                fokontany_label + " TEXT, " + code_fokontany + " TEXT, " + cv_label + " TEXT, "
                + code_cv + " TEXT, " + bv_label + " TEXT, " + code_bv + " TEXT)";

        String query3 = "CREATE TABLE " +  TABLE_BV + "( " + CODE_BV + " TEXT, "
                + NUM_USERINFO + " TEXT, "
                + CODE_CV + " TEXT, "
                + LIBELLE_BV + " TEXT, "
                + EMPLACEMENT + " TEXT, "
                + INSCRITSLISTE + " TEXT, "
                + INSCRITS + " TEXT, "
                + VOTANT + " TEXT, "
                + BLANCS_NULS + " TEXT, "
                + NUMPV + " TEXT, "
                + SEXPRIMES_BV + " TEXT, "
                + OBSERVDATA_BV +  " TEXT, "
                + ETAT_BV + " TEXT, "
                + ETAT_BVMODIFIE + " TEXT, "
                + DHMAJ + " TEXT, "
                + RAJOUT + " TEXT, "
                + ESTANOMALIE  + " TEXT, "
                + RAJOUTPV + " TEXT, "
                + BULTURNE + " TEXT, "
                + BULTDESINSUF + " TEXT, "
                + BULTSP + " TEXT, "
                + BULTAUTRMOTIF + " TEXT, "
                + BULTNONSIGNDEU + " TEXT, "
                + BULTNULL + " TEXT, "
                + BULTBLANC + " TEXT, "
                + ACORRIGER + " TEXT, "
                + DATE_SAISIE + " TEXT, "
                + STATUT + " TEXT, "
                + HOMME + " TEXT, "
                + FEMME + " TEXT, "
                + BULTSURNOMBR + " TEXT, "
                + BULTMEME + " TEXT, "
                + BULTINF + " TEXT, "
                + BULTENLEVE + " TEXT, "
                + CARNETRECU + " TEXT, "
                + NUMCARNETRECU + " TEXT, "
                + BULTRECU + " TEXT, "
                + BULTRECUUT + " TEXT, "
                + BULTRECUNUT + " TEXT, "
                + NUMBV + " TEXT, "
                + MARQUAGE + " TEXT)";

        String query4 = "CREATE TABLE " +  TABLE_VOIXOBTENUE + "( " + NUM_CANDIDAT + " TEXT, "
                + CODE_BV + " TEXT, "
                + NBVOIX + " TEXT, "
                + DHMAJ_VOIX + " TEXT)";

        String createTableCandidatQuery = "CREATE TABLE " + TABLE_CANDIDAT + " ("
                + NUM_ORDRE + " TEXT PRIMARY KEY, "
                + LOGO + " TEXT, "
                + NOM_PRENOMS + " TEXT, "
                + ENTITE + " TEXT,"
                + VOIXOBTENUE + " TEXT)";

        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);
        sqLiteDatabase.execSQL(createTableCandidatQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALISATION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_User);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BV);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VOIXOBTENUE);
        onCreate(sqLiteDatabase);
    }

    public boolean deleteAllUser() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_User, null, null) > 0;
    }

    public boolean deleteAllLocalisation() {
        Log.d("DELETE", "LOCALISATION !!");
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_LOCALISATION, null, null) > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Region> selectRegion() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select region_label, code_region from Localisation GROUP BY region_label", null);
        List<Region> listDistrict = new ArrayList<>();
        try {
            Log.d("TAILLE REGION ","LENGTH : "+cursor.getCount());
            while (cursor.moveToNext()) {
                Region c = new Region();
                c.setId_region(0);
                c.setCode_region(cursor.getString(1));
                c.setLabel_region(cursor.getString(0));
                c.setLieu_region(cursor.getString(0));
                listDistrict.add(c);
            }
        } catch (Exception e) {
            Log.e("error Select SQLITE", "ERROR SELECT COMMUNE");
        } finally {
            cursor.close();
            MyDB.close();
        }
        return listDistrict;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<District> selectDistrictFromRegion(String codeRegion) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Localisation where " + code_region + "=? group by code_district", new String[]{codeRegion});
        List<District> listDistrict = new ArrayList<>();
        try {
            Log.d("TAILLE DISTRICT ","LENGTH : "+cursor.getCount());
            while (cursor.moveToNext()) {
                District c = new District();
                c.setId_district(0);
                c.setCode_district(cursor.getString(3));
                c.setCode_region(cursor.getString(1));
                c.setLabel_district(cursor.getString(2));
                listDistrict.add(c);
            }
        } catch (Exception e) {
            Log.e("error Select SQLITE", "ERROR SELECT COMMUNE");
        } finally {
            cursor.close();
            MyDB.close();
        }
        return listDistrict;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Commune> selectCommuneFromDistrict(String codeDistrict) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Localisation where " + code_district + "=? group by code_commune", new String[]{codeDistrict});
        List<Commune> listCommune = new ArrayList<>();
        try {
            Log.d("TAILLE COMMUNE ","LENGTH : "+cursor.getCount());
            while (cursor.moveToNext()) {
                Commune c = new Commune();
                c.setId_commune(0);
                c.setCode_commune(cursor.getString(5));
                c.setCode_district(cursor.getString(3));
                c.setLabel_commune(cursor.getString(4));
                listCommune.add(c);
            }
        } catch (Exception e) {
            Log.e("error Select SQLITE", "ERROR SELECT COMMUNE");
        } finally {
            cursor.close();
            MyDB.close();
        }
        return listCommune;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Fokontany> selectFokotanyFromCommune(String codeCommune) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Localisation where " + code_commune + "=? group by code_fokontany", new String[]{codeCommune});
        List<Fokontany> listFokontany = new ArrayList<>();
        try {
            Log.d("TAILLE FKT ","LENGTH : "+cursor.getCount());
            while (cursor.moveToNext()) {
                Fokontany c = new Fokontany();
                c.setId_fokontany(0);
                c.setCode_fokontany(cursor.getString(7));
                c.setCode_commune(cursor.getString(5));
                c.setLabel_fokontany(cursor.getString(6));
                listFokontany.add(c);
            }
        } catch (Exception e) {
            Log.e("error Select SQLITE", "ERROR SELECT FOKONTANY");
        } finally {
            cursor.close();
            MyDB.close();
        }
        return listFokontany;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<CV> selectCvFromFokontany(String codeFokontany) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Localisation where " + code_fokontany + "=? group by code_cv", new String[]{codeFokontany});
        List<CV> listCv = new ArrayList<>();
        try {
            Log.d("TAILLE CV ","LENGTH : "+cursor.getCount());
            while (cursor.moveToNext()) {

               
                CV c = new CV();
                c.setId_cv(0);
                c.setCode_cv(cursor.getString(9));
                c.setCode_fokontany(cursor.getString(7));
                c.setLabel_cv(cursor.getString(8));
                listCv.add(c);
            }
        } catch (Exception e) {
            Log.e("error Select SQLITE", "ERROR SELECT CV");
        } finally {
            cursor.close();
            MyDB.close();
        }
        return listCv;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<BV> selectBvFromCv(String code_Bv) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from Localisation where " + code_cv + "=? group by code_bv", new String[]{code_Bv});
        List<BV> listBv = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                BV c = new BV();
                c.setId_bv(cursor.getInt(0));
                c.setCode_bv(cursor.getString(11));
                c.setCode_cv(cursor.getString(9));
                c.setLabel_bv(cursor.getString(10));
                listBv.add(c);
            }
        } catch (Exception e) {
            Log.e("error Select SQLITE", "ERROR SELECT BV");
        } finally {
            cursor.close();
            MyDB.close();
        }
        return listBv;
    }

    public void insertLocalisation(Context c) {
        Log.d("INSERTION LOCALISATION", "INSERTION STARTED !!");
        SQLiteDatabase MyDB = this.getWritableDatabase();
        try {
            Resources res = c.getResources();
            String[] localisation = res.getStringArray(R.array.localisation);
            MyDB.beginTransaction();
            for (int i = 0; i < localisation.length; i++) {
                String sql = "INSERT INTO Localisation (region_label,code_region,district_label,code_district,commune_label,code_commune," +
                        "fokontany_label,code_fokontany,cv_label,code_cv,bv_label,code_bv) VALUES " +
                        localisation[i];
                MyDB.execSQL(sql);
            }
            MyDB.setTransactionSuccessful();
            MyDB.endTransaction();
            Log.d("INSERTION LOCALISATION", "LOCALISATION INSERTED");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyDB.close();
        }
    }

    public void insertbv(Context c) {
        Log.d("INSERTION BV", "INSERTION STARTED !!");
        SQLiteDatabase MyDB = this.getWritableDatabase();
        try {
            Resources res = c.getResources();
            String[] localisation = res.getStringArray(R.array.bv);
            MyDB.beginTransaction();
            for (int i = 0; i < localisation.length; i++) {
                String sql = "INSERT INTO bv (CODE_BV, NUM_USERINFO, CODE_CV, LIBELLE_BV, EMPLACEMENT, INSCRITSLISTE, INSCRITS, VOTANT, BLANCS_NULS," +
                        " NUMPV, SEXPRIMES_BV, OBSERVDATA_BV, ETAT_BV, ETAT_BVMODIFIE, DHMAJ, RAJOUT, ESTANOMALIE, RAJOUTPV, BULTURNE, BULTDESINSUF," +
                        " BULTSP, BULTAUTRMOTIF, BULTNONSIGNDEU, BULTNULL, BULTBLANC, ACORRIGER, DATE_SAISIE, STATUT, HOMME, FEMME, BULTSURNOMBR," +
                        " BULTMEME, BULTINF, BULTENLEVE, CARNETRECU, NUMCARNETRECU, BULTRECU, BULTRECUUT, BULTRECUNUT, NUMBV, MARQUAGE) VALUES " +
                        localisation[i];
                MyDB.execSQL(sql);
            }
            MyDB.setTransactionSuccessful();
            MyDB.endTransaction();
            Log.d("INSERTION BV", "BV INSERTED");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyDB.close();
        }
    }


    public void insertCandidat(Context c) {
        Log.d("INSERTION CANDIDAT", "INSERTION STARTED !!");
        SQLiteDatabase MyDB = this.getWritableDatabase();

        try {
            Resources res = c.getResources();
            String[] candidatsArray = res.getStringArray(R.array.candidats_array);

            MyDB.beginTransaction();

            for (int i = 0; i < candidatsArray.length; i++) {
                String sql = "INSERT INTO CANDIDAT (NUM_ORDRE, LOGO, NOM_PRENOMS, ENTITE, VOIXOBTENUE) VALUES " +
                        candidatsArray[i];
                MyDB.execSQL(sql);
            }

            MyDB.setTransactionSuccessful();
            Log.d("INSERTION CANDIDAT", "CANDIDAT INSERTED");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyDB.endTransaction();
            MyDB.close();
        }
    }


    public boolean insertBV(BV bv) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Vérifier si le CODE_BV existe déjà dans la base de données
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BV + " WHERE CODE_BV=?", new String[]{bv.getCode_bv()});
        if (cursor.moveToFirst()) {
            // CODE_BV existe déjà, ne pas effectuer l'insertion
            cursor.close();
            return false;
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put("CODE_BV", bv.getCode_bv());
        values.put("NUM_USERINFO", bv.getResponsable());
        values.put("CODE_CV", bv.getCode_cv());
        values.put("LIBELLE_BV", bv.getBureau_de_vote());
        values.put("EMPLACEMENT", bv.getCentre_de_vote());
        values.put("INSCRITSLISTE", bv.getInscritsliste());
        values.put("INSCRITS", bv.getINSCRITS());
        values.put("VOTANT", bv.getVOTANT());
        values.put("BLANCS_NULS", bv.getBLANCS_NULS());
        values.put("NUMPV", bv.getNB_PV());
        values.put("SEXPRIMES_BV", bv.getV_Manankery());
        values.put("OBSERVDATA_BV", bv.getOBSERVDATA_BV());
        values.put("ETAT_BV", bv.getETAT_BV());
        //values.put("ETAT_BVMODIFIE", bv.getBULTBLANC());
        //values.put("DHMAJ", bv.getVOTANT());
        values.put("RAJOUT", bv.getRAJOUT());
        values.put("ESTANOMALIE", bv.getESTANOMALIE());
        //values.put("RAJOUTPV", bv.getHOMME());
        values.put("BULTURNE", bv.getBULTURNE());
        //values.put("BULTDESINSUF", bv.getBU());
        //values.put("BULTSP", bv.getI_biletàTokanaKarine());
        //values.put("BULTAUTRMOTIF", bv.getI_biletàTokanaNampiasaina());
        //values.put("BULTNONSIGNDEU", bv.getI_biletàTokanaTsyNampiasaina());
        values.put("BULTNULL", bv.getBULTNULL());
        values.put("BULTBLANC", bv.getBULTBLANC());
        //values.put("ACORRIGER", bv.getI_biletàTokanaKarine());
        values.put("DATE_SAISIE", bv.getDATE_SAISIE());
        //values.put("STATUE", bv.getSTATU());
        values.put("HOMME", bv.getHOMME());
        values.put("FEMME", bv.getFEMME());
        values.put("BULTSURNOMBR", bv.getBULTSURNOMBR());
        values.put("BULTMEME", bv.getBULTMEME());
        values.put("BULTINF", bv.getBULTINF());
        values.put("BULTENLEVE", bv.getBULTENLEVE());
        values.put("CARNETRECU", bv.getI_KarineVoaray());
        values.put("NUMCARNETRECU", bv.getL_KarineVoaray());
        values.put("BULTRECU", bv.getI_biletàTokanaKarine());
        values.put("BULTRECUUT", bv.getI_biletàTokanaNampiasaina());
        values.put("BULTRECUNUT", bv.getI_biletàTokanaTsyNampiasaina());

        long result = db.insert(TABLE_BV, null, values);
        Log.d("INSERTION BV", "BV INSERTED");
        return result != -1;
    }


    public boolean insertVoixObtenue(String numCandidat, String codeBV, String nbVoix, String dhmajVoix) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NUM_CANDIDAT, numCandidat);
        contentValues.put(CODE_BV, codeBV);
        contentValues.put(NBVOIX, nbVoix);
        contentValues.put(DHMAJ_VOIX, dhmajVoix);

        long result = db.insert(TABLE_VOIXOBTENUE, null, contentValues);
        return result != -1; // Renvoie true si l'insertion a réussi, sinon false
    }


    public boolean updateBV(BV bv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Ajoutez les valeurs de chaque colonne à mettre à jour


        values.put(NUM_USERINFO, bv.getResponsable());
        values.put(LIBELLE_BV, bv.getBureau_de_vote());
        values.put(EMPLACEMENT, bv.getCentre_de_vote());
        values.put(INSCRITS, bv.getINSCRITS());
        values.put(VOTANT, bv.getVOTANT());
        values.put(BLANCS_NULS, bv.getBLANCS_NULS());
        values.put(NUMPV, bv.getNB_PV());
        values.put(SEXPRIMES_BV, bv.getV_Manankery());
        values.put(OBSERVDATA_BV, bv.getOBSERVDATA_BV());
        values.put(ETAT_BV, bv.getETAT_BV());
        //values.put("ETAT_BVMODIFIE", bv.getBULTBLANC());
        values.put(DHMAJ, bv.getDHMAJ());
        values.put(RAJOUT, bv.getRAJOUT());
        values.put(ESTANOMALIE, bv.getESTANOMALIE());
        //values.put("RAJOUTPV", bv.getHOMME());
        values.put(BULTURNE, bv.getBULTURNE());
        //values.put("BULTDESINSUF", bv.getBU());
        //values.put("BULTSP", bv.getI_biletàTokanaKarine());
        //values.put("BULTAUTRMOTIF", bv.getI_biletàTokanaNampiasaina());
        //values.put("BULTNONSIGNDEU", bv.getI_biletàTokanaTsyNampiasaina());
        values.put(BULTNULL, bv.getBULTNULL());
        values.put(BULTBLANC, bv.getBULTBLANC());
        //values.put("ACORRIGER", bv.getI_biletàTokanaKarine());
        values.put(DATE_SAISIE, bv.getDATE_SAISIE());
        values.put(HOMME, bv.getHOMME());
        values.put(FEMME, bv.getFEMME());
        values.put(BULTSURNOMBR, bv.getBULTSURNOMBR());
        values.put(BULTMEME, bv.getBULTMEME());
        values.put(BULTINF, bv.getBULTINF());
        values.put(BULTENLEVE, bv.getBULTENLEVE());
        values.put(CARNETRECU, bv.getI_KarineVoaray());
        values.put(NUMCARNETRECU, bv.getL_KarineVoaray());
        values.put(BULTRECU, bv.getI_biletàTokanaKarine());
        values.put(BULTRECUUT, bv.getI_biletàTokanaNampiasaina());
        values.put(BULTRECUNUT, bv.getI_biletàTokanaTsyNampiasaina());
        // Ajoutez les autres colonnes avec leurs valeurs correspondantes

        if (values.size() > 0) {
            db.update(TABLE_BV, values, CODE_BV + " = ?", new String[]{String.valueOf(bv.getCode_bv())});
        }
        db.close();

        // Renvoyer true si au moins une ligne a été affectée (mise à jour réussie)

        return true;
    }

    public boolean updateVoixObtenue(String numCandidat, String nbVoix) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NBVOIX, nbVoix);

        // Définir la clause WHERE pour mettre à jour le candidat spécifique
        String whereClause = NUM_CANDIDAT + " = ?";
        String[] whereArgs = { numCandidat };

        // Effectuer la mise à jour dans la base de données
        int rowsAffected = db.update(VOIXOBTENUE, contentValues, whereClause, whereArgs);

        // Fermer la connexion à la base de données
        db.close();

        // Vérifier si la mise à jour a été effectuée avec succès
        return rowsAffected > 0;
    }


    @SuppressLint("Range")
    public List<BV> getAllBV() {
        List<BV> bvList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            //cursor = db.rawQuery("SELECT * FROM " + TABLE_BV, null);
            cursor = db.rawQuery("SELECT * FROM " + TABLE_BV + " WHERE VOTANT IS NOT NULL AND VOTANT <> 'NULL'", null);

            while (cursor.moveToNext()) {
                BV bv = new BV();
                bv.setCode_bv(cursor.getString(cursor.getColumnIndex(CODE_BV)));
                bv.setResponsable(cursor.getString(cursor.getColumnIndex(NUM_USERINFO)));
                bv.setCode_cv(cursor.getString(cursor.getColumnIndex(CODE_CV)));
                bv.setBureau_de_vote(cursor.getString(cursor.getColumnIndex(LIBELLE_BV)));
                bv.setCentre_de_vote(cursor.getString(cursor.getColumnIndex(EMPLACEMENT)));
                bv.setInscritsliste(cursor.getString(cursor.getColumnIndex(INSCRITSLISTE)));
                bv.setINSCRITS(cursor.getString(cursor.getColumnIndex(INSCRITS)));
                bv.setVOTANT(cursor.getString(cursor.getColumnIndex(VOTANT)));
                bv.setNB_PV(cursor.getString(cursor.getColumnIndex(NUMPV)));
                bv.setV_Manankery(cursor.getString(cursor.getColumnIndex(SEXPRIMES_BV)));
                bv.setOBSERVDATA_BV(cursor.getString(cursor.getColumnIndex(OBSERVDATA_BV)));
                bv.setRAJOUT(cursor.getString(cursor.getColumnIndex(RAJOUT)));
                bv.setBULTURNE(cursor.getString(cursor.getColumnIndex(BULTURNE)));
                bv.setBULTNULL(cursor.getString(cursor.getColumnIndex(BULTNULL)));
                bv.setBULTBLANC(cursor.getString(cursor.getColumnIndex(BULTBLANC)));
                bv.setHOMME(cursor.getString(cursor.getColumnIndex(HOMME)));
                bv.setFEMME(cursor.getString(cursor.getColumnIndex(FEMME)));
                bv.setBULTSURNOMBR(cursor.getString(cursor.getColumnIndex(BULTSURNOMBR)));
                bv.setBULTMEME(cursor.getString(cursor.getColumnIndex(BULTMEME)));
                bv.setBULTINF(cursor.getString(cursor.getColumnIndex(BULTINF)));
                bv.setBULTENLEVE(cursor.getString(cursor.getColumnIndex(BULTENLEVE)));
                bv.setI_KarineVoaray(cursor.getString(cursor.getColumnIndex(CARNETRECU)));
                bv.setL_KarineVoaray(cursor.getString(cursor.getColumnIndex(NUMCARNETRECU)));
                bv.setI_biletàTokanaKarine(cursor.getString(cursor.getColumnIndex(BULTRECU)));
                bv.setI_biletàTokanaNampiasaina(cursor.getString(cursor.getColumnIndex(BULTRECUUT)));
                bv.setI_biletàTokanaTsyNampiasaina(cursor.getString(cursor.getColumnIndex(BULTRECUNUT)));
                bvList.add(bv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return bvList;
    }

    public boolean checkBVExists(String codeBV) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BV + " WHERE CODE_BV=?", new String[]{codeBV});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    public List<Candidat> getAllCandidats() {
        List<Candidat> candidats = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            // Ajouter la clause "ORDER BY" à votre requête SQL
            cursor = db.rawQuery("SELECT * FROM " + TABLE_CANDIDAT , null);
            while (cursor.moveToNext()) {
                Candidat candidat = new Candidat();

                candidat.setNumOrdre(cursor.getInt(cursor.getColumnIndex(NUM_ORDRE)));
                candidat.setLogo(cursor.getString(cursor.getColumnIndex(LOGO)));
                candidat.setNomPrenom(cursor.getString(cursor.getColumnIndex(NOM_PRENOMS)));
                candidat.setEntite(cursor.getString(cursor.getColumnIndex(ENTITE)));
                candidat.setVoixObtenue(cursor.getString(cursor.getColumnIndex(VOIXOBTENUE)));

                candidats.add(candidat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return candidats;
    }



    @SuppressLint("Range")
    public List<VoixObtenue> getVoixObtenuesForBV(String codeBV) {
        List<VoixObtenue> voixObtenuesList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_VOIXOBTENUE + " WHERE " + CODE_BV + "=?", new String[]{codeBV});

            while (cursor.moveToNext()) {
                VoixObtenue voixObtenue = new VoixObtenue();

                voixObtenue.setNumcandidat(cursor.getString(cursor.getColumnIndex(NUM_CANDIDAT)));
                voixObtenue.setCodeBV(cursor.getString(cursor.getColumnIndex(CODE_BV)));
                voixObtenue.setNbvoix(cursor.getString(cursor.getColumnIndex(NBVOIX)));

                voixObtenuesList.add(voixObtenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return voixObtenuesList;
    }
    public String getInscritsListeFromBV(String codeBV) {
        // Code pour récupérer les données de la table bv en fonction du code BV
        // Supposons que tu as une colonne "inscritsliste" dans ta table bv
        // Utilise une requête SQL pour récupérer la valeur associée au codeBV

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT INSCRITSLISTE FROM bv WHERE code_bv = ?", new String[]{codeBV});

        String inscritsListe = "";

        if (cursor.moveToFirst()) {
            inscritsListe = cursor.getString(cursor.getColumnIndex("INSCRITSLISTE"));
        }

        cursor.close();
        db.close();

        return inscritsListe;
    }

}
