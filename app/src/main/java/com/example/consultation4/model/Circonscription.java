package com.example.consultation4.model;

public class Circonscription {
   private String Region;
   private String District;
   private String Commune;
   private String Fokontany;
   private int code_Fokontany;
   private String Centre_de_vote;
   private String Bureau_de_vote;
   private String code_bv;

   public Circonscription() {
   }

   public Circonscription(String region, String district, String commune, String fokontany, String centre_de_vote, String bureau_de_vote) {
      Region = region;
      District = district;
      Commune = commune;
      Fokontany = fokontany;
      Centre_de_vote = centre_de_vote;
      Bureau_de_vote = bureau_de_vote;
   }

   public String getRegion() {
      return Region;
   }

   public void setRegion(String region) {
      Region = region;
   }

   public String getDistrict() {
      return District;
   }

   public void setDistrict(String district) {
      District = district;
   }

   public String getCommune() {
      return Commune;
   }

   public void setCommune(String commune) {
      Commune = commune;
   }

   public String getFokontany() {
      return Fokontany;
   }

   public void setFokontany(String fokontany) {
      Fokontany = fokontany;
   }

   public int getCode_Fokontany() {
      return code_Fokontany;
   }

   public void setCode_Fokontany(int code_fokontany) { code_Fokontany = code_fokontany;
   }

   public String getCentre_de_vote() {
      return Centre_de_vote;
   }

   public void setCentre_de_vote(String centre_de_vote) {
      Centre_de_vote = centre_de_vote;
   }

   public String getBureau_de_vote() {
      return Bureau_de_vote;
   }

   public void setBureau_de_vote(String bureau_de_vote) {
      Bureau_de_vote = bureau_de_vote;
   }

   public String getCode_bv() {
      return code_bv;
   }

   public void setCode_bv(String code_bv) {
      this.code_bv = code_bv;
   }
}
