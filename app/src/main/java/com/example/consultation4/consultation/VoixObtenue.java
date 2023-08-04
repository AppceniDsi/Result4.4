package com.example.consultation4.consultation;

public class VoixObtenue {

    private String Numcandidat;
    private String CodeBV ;
    private String Nbvoix;
    private String DHmajvoix;

    public VoixObtenue(String numcandidat , String codeBV , String nbvoix , String dhmajvoix) {

        this.Numcandidat = numcandidat;
        this.CodeBV = codeBV;
        this.Nbvoix = nbvoix;
        this.DHmajvoix = dhmajvoix;
    }

    public VoixObtenue() {

    }

    public String getNumcandidat() {
        return Numcandidat;
    }

    public void setNumcandidat(String numcandidat) {
        Numcandidat = numcandidat;
    }

    public String getCodeBV() {
        return CodeBV;
    }

    public void setCodeBV(String codeBV) {
        CodeBV = codeBV;
    }

    public String getNbvoix() {
        return Nbvoix;
    }

    public void setNbvoix(String nbvoix) {
        Nbvoix = nbvoix;
    }

    public String getDHmajvoix() {
        return DHmajvoix;
    }

    public void setDHmajvoix(String DHmajvoix) {
        this.DHmajvoix = DHmajvoix;
    }
}

