package com.example.consultation4.model;

public class CV {

    private int id_cv;
    private String code_cv;
    private String code_fokontany;
    private String label_cv;

    public CV() {
    }

    public CV(int id_cv, String code_cv, String code_fokontany, String label_cv) {
        this.id_cv = id_cv;
        this.code_cv = code_cv;
        this.code_fokontany = code_fokontany;
        this.label_cv = label_cv;
    }

    public int getId_cv() {
        return id_cv;
    }

    public void setId_cv(int id_cv) {
        this.id_cv = id_cv;
    }

    public String getCode_cv() {
        return code_cv;
    }

    public void setCode_cv(String code_cv) {
        this.code_cv = code_cv;
    }

    public String getCode_fokontany() {
        return code_fokontany;
    }

    public void setCode_fokontany(String code_fokontany) {
        this.code_fokontany = code_fokontany;
    }

    public String getLabel_cv() {
        return label_cv;
    }

    public void setLabel_cv(String label_cv) {
        this.label_cv = label_cv;
    }
}
