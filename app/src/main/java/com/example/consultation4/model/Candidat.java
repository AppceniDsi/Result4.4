package com.example.consultation4.model;

public class Candidat {
    private int numOrdre;
    private Candidat candidat;
    private String logo;
    private String nomPrenom;
    private String entite;
    private String voixObtenue;

    public Candidat(int numOrdre, Candidat candidat,String logo, String nomPrenom, String entite, String Voixobtenue) {
        this.numOrdre = numOrdre;
        this.logo = logo;
        this.candidat = candidat;
        this.nomPrenom = nomPrenom;
        this.entite = entite;
        this.voixObtenue = Voixobtenue;
    }

    public Candidat() {

    }

    public int getNumOrdre() {
        return numOrdre;
    }

    public void setNumOrdre(int NumOrdre) {
        this.numOrdre = NumOrdre;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public Candidat getCandidat() {
        return candidat;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String NomPrenom) {
        this.nomPrenom = NomPrenom;
    }

    public String getEntite() {
        return entite;
    }

    public void setEntite(String entite) {
        this.entite = entite;
    }
    public String getVoixObtenue(){return voixObtenue;};

    public void setVoixObtenue(String voixObtenue) {
        this.voixObtenue = voixObtenue;
    }
}

