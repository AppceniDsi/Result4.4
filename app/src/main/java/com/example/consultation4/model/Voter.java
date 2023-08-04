package com.example.consultation4.model;

public class Voter {
    private  String NB_PV;
    private String INSCRITS;
    private String RAJOUT;
    private String VOTANT;
    private String BULTBLANC;
    private String BULTNULL;
    private String V_Manankery;
    private String Lera;
    private String FEMME;

    private String HOMME;

    private String I_KarineVoaray;

    private String L_KarineVoaray;

    private String I_biletàTokanaKarine;

    private String I_biletàTokanaNampiasaina;

    private String I_biletàTokanaTsyNampiasaina;

    private String bulturne,blanc_nuls,bultmeme,bultsurnombr,bultinf,bultenleve,observdata_bv, inscritsliste,estanomalie;

    public Voter(String nB_PV,String iNSCRITS, String rAJOUT, String vOTANT, String bULTBLANC, String bULTNULL, String v_Manankery, String lera
            , String fEMME, String hOMME, String i_KarineVoaray, String l_KarineVoaray, String i_biletàTokanaKarine, String i_biletàTokanaNampiasaina
            , String i_biletàTokanaTsyNampiasaina,String Bulturne,String Bultmeme,String Bultsurnombr,String Bultinf,String Bultenleve
            , String Observdata_bv, String Inscritsliste, String Blanc_nuls , String Estanomalie) {
        NB_PV = nB_PV;
        INSCRITS = iNSCRITS;
        RAJOUT = rAJOUT;
        VOTANT = vOTANT;
        BULTBLANC = bULTBLANC;
        BULTNULL = bULTNULL;
        V_Manankery = v_Manankery;
        Lera = lera;
        FEMME = fEMME;
        HOMME = hOMME;
        I_KarineVoaray = i_KarineVoaray;
        L_KarineVoaray = l_KarineVoaray;
        I_biletàTokanaKarine = i_biletàTokanaKarine;
        I_biletàTokanaNampiasaina = i_biletàTokanaNampiasaina;
        I_biletàTokanaTsyNampiasaina = i_biletàTokanaTsyNampiasaina;
        bulturne = Bulturne;
        bultmeme = Bultmeme;
        bultsurnombr = Bultsurnombr;
        bultinf = Bultinf;

        bultenleve = Bultenleve;
        observdata_bv = Observdata_bv;
        inscritsliste = Inscritsliste;
        blanc_nuls = Blanc_nuls;
        estanomalie = Estanomalie;
    }

    public Voter() {

    }

    public String getNB_PV() {
        return NB_PV;
    }

    public void setNB_PV(String nB_PV) {
        NB_PV = nB_PV;
    }
    public String getINSCRITS() {
        return INSCRITS;
    }

    public void setINSCRITS(String iNSCRITS) {
        INSCRITS = iNSCRITS;
    }

    public String getRAJOUT() {
        return RAJOUT;
    }

    public void setRAJOUT(String rAJOUT) {
        RAJOUT = rAJOUT;
    }

    public String getVOTANT() {
        return VOTANT;
    }

    public void setVOTANT(String vOTANT) {
        VOTANT = vOTANT;
    }

    public String getBULTBLANC() {
        return BULTBLANC;
    }

    public void setBULTBLANC(String bULTBLANC) {
        BULTBLANC = bULTBLANC;
    }

    public String getBULTNULL() {
        return BULTNULL;
    }

    public void setBULTNULL(String bULTNULL) {
        BULTNULL = bULTNULL;
    }

    public String getV_Manankery() {
        return V_Manankery;
    }

    public void setV_Manankery(String v_Manankery) {
        V_Manankery = v_Manankery;
    }

    public String getLera() {
        return Lera;
    }

    public void setLera(String lera) {
        Lera = lera;
    }

    public String getFEMME() {
        return FEMME;
    }

    public void setFEMME(String fEMME) {
        FEMME = fEMME;
    }

    public String getHOMME() {
        return HOMME;
    }

    public void setHOMME(String hOMME) {
        HOMME = hOMME;
    }

    public String getI_KarineVoaray() {
        return I_KarineVoaray;
    }

    public void setI_KarineVoaray(String i_KarineVoaray) {
        I_KarineVoaray = i_KarineVoaray;
    }

    public String getL_KarineVoaray() {
        return L_KarineVoaray;
    }

    public void setL_KarineVoaray(String l_KarineVoaray) {
        L_KarineVoaray = l_KarineVoaray;
    }

    public String getI_biletàTokanaKarine() {
        return I_biletàTokanaKarine;
    }

    public void setI_biletàTokanaKarine(String i_biletàTokanaKarine) {
        I_biletàTokanaKarine = i_biletàTokanaKarine;
    }

    public String getI_biletàTokanaNampiasaina() {
        return I_biletàTokanaNampiasaina;
    }

    public void setI_biletàTokanaNampiasaina(String i_biletàTokanaNampiasaina) {
        I_biletàTokanaNampiasaina = i_biletàTokanaNampiasaina;
    }

    public String getI_biletàTokanaTsyNampiasaina() {
        return I_biletàTokanaTsyNampiasaina;
    }

    public void setI_biletàTokanaTsyNampiasaina(String i_biletàTokanaTsyNampiasaina) {
        I_biletàTokanaTsyNampiasaina = i_biletàTokanaTsyNampiasaina;
    }

    public String getBulturne() {
        return bulturne;
    }

    public void setBulturne(String Bulturne) {
        bulturne = Bulturne;
    }
    public String getBultmeme() {
        return bultmeme;
    }

    public void setBultmeme(String Bultmeme) {
        bultmeme = Bultmeme;
    }
    public String getBultsurnombr() {
        return bultsurnombr;
    }

    public void setBultsurnombr(String Bultsurnombr) {
        bultsurnombr = Bultsurnombr;
    }
    public String getBultinf() {
        return bultinf;
    }

    public void setBultinf(String Bultinf) {
        bultinf = Bultinf;
    }

    public String getBultenleve(){return bultenleve;}

    public void setBultenleve(String bultenleve) {
        this.bultenleve = bultenleve;
    }

    public String getObservdata_bv() {
        return observdata_bv;
    }

    public void setObservdata_bv(String Observdata_bv) {
        observdata_bv = Observdata_bv;
    }
    public void setInscritsliste(){ inscritsliste = "10";};
    public String getBlanc_nuls(){return blanc_nuls;}
    public void setBlanc_nuls(String Blanc_nuls){ blanc_nuls = Blanc_nuls; }

    public String getEstanomalie() {
        return estanomalie;
    }

    public void setEstanomalie(String estanomalie) {
        this.estanomalie = estanomalie;
    }
}
