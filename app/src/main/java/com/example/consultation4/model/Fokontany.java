package com.example.consultation4.model;

public class Fokontany {

   private int id_fokontany;
   private String code_fokontany;
   private String code_commune;
   private String label_fokontany;

   public Fokontany() {
   }

   public Fokontany(int id_fokontany, String code_fokontany, String code_commune, String label_fokontany) {
      this.id_fokontany = id_fokontany;
      this.code_fokontany = code_fokontany;
      this.code_commune = code_commune;
      this.label_fokontany = label_fokontany;
   }

   public int getId_fokontany() {
      return id_fokontany;
   }

   public void setId_fokontany(int id_fokontany) {
      this.id_fokontany = id_fokontany;
   }

   public String getCode_fokontany() {
      return code_fokontany;
   }

   public void setCode_fokontany(String code_fokontany) {
      this.code_fokontany = code_fokontany;
   }

   public String getCode_commune() {
      return code_commune;
   }

   public void setCode_commune(String code_commune) {
      this.code_commune = code_commune;
   }

   public String getLabel_fokontany() {
      return label_fokontany;
   }

   public void setLabel_fokontany(String label_fokontany) {
      this.label_fokontany = label_fokontany;
   }
}
