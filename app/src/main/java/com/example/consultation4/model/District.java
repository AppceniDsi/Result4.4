package com.example.consultation4.model;

public class District {

   private int id_district;
   private String code_district;
   private String code_region;
   private String label_district;

   public District() {
   }

   public District(int id_district, String code_district, String code_region, String label_district) {
      this.id_district = id_district;
      this.code_district = code_district;
      this.code_region = code_region;
      this.label_district = label_district;
   }

   public int getId_district() {
      return id_district;
   }

   public void setId_district(int id_district) {
      this.id_district = id_district;
   }

   public String getCode_district() {
      return code_district;
   }

   public void setCode_district(String code_district) {
      this.code_district = code_district;
   }

   public String getCode_region() {
      return code_region;
   }

   public void setCode_region(String code_region) {
      this.code_region = code_region;
   }

   public String getLabel_district() {
      return label_district;
   }

   public void setLabel_district(String label_district) {
      this.label_district = label_district;
   }
}
