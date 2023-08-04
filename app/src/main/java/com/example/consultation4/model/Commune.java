package com.example.consultation4.model;

public class Commune {

    private int id_commune;
    private String code_commune;
    private String code_district;
    private String label_commune;

    public Commune() {
    }

    public Commune(int id_commune, String code_commune, String code_district, String label_commune) {
        this.id_commune = id_commune;
        this.code_commune = code_commune;
        this.code_district = code_district;
        this.label_commune = label_commune;
    }

    public int getId_commune() {
        return id_commune;
    }

    public void setId_commune(int id_commune) {
        this.id_commune = id_commune;
    }

    public String getCode_commune() {
        return code_commune;
    }

    public void setCode_commune(String code_commune) {
        this.code_commune = code_commune;
    }

    public String getCode_district() {
        return code_district;
    }

    public void setCode_district(String code_district) {
        this.code_district = code_district;
    }

    public String getLabel_commune() {
        return label_commune;
    }

    public void setLabel_commune(String label_commune) {
        this.label_commune = label_commune;
    }
}
