package com.example.consultation4.model;

public class Region {

   private int id_region;
   private String code_region;
   private String label_region;
   private String lieu_region;

   public Region() {
   }

   public Region(String code_region, String label_region) {
      this.code_region = code_region;
      this.label_region = label_region;
   }

   public Region(int id_region, String code_region, String label_region, String lieu_region) {
      this.id_region = id_region;
      this.code_region = code_region;
      this.label_region = label_region;
      this.lieu_region = lieu_region;
   }

   public int getId_region() {
      return id_region;
   }

   public void setId_region(int id_region) {
      this.id_region = id_region;
   }

   public String getCode_region() {
      return code_region;
   }

   public void setCode_region(String code_region) {
      this.code_region = code_region;
   }

   public String getLabel_region() {
      return label_region;
   }

   public void setLabel_region(String label_region) {
      this.label_region = label_region;
   }

   public String getLieu_region() {
      return lieu_region;
   }

   public void setLieu_region(String lieu_region) {
      this.lieu_region = lieu_region;
   }
}
