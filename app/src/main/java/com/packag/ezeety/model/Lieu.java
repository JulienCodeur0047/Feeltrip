package com.packag.ezeety.model;

import com.google.gson.annotations.SerializedName;

public class Lieu {

  @SerializedName("nom")
  private String nom;
  @SerializedName("ville")
  private String ville;
  @SerializedName("adresse")
  private String adresse;
  @SerializedName("pays")
  private int pays;
  @SerializedName("code_postal")
  private String code_postal;
  @SerializedName("hash_tag")
  private String hash_tag;
  @SerializedName("photo")
  private String photo;
  @SerializedName("date_inscription")
  private java.util.Date date_inscription;
  @SerializedName("statut")
  private int statut;
  @SerializedName("type")
  private int type;
  @SerializedName("added_by")
  private int added_by;
  @SerializedName("etat")
  private String etat;

  @SerializedName("id_type_lieu")
  private int id_type_lieu;
  @SerializedName("type_lieu")
  private String type_lieu;
  @SerializedName("is_default")
  private int is_default;

  public int getId_type_lieu() {
    return id_type_lieu;
  }

  public void setId_type_lieu(int id_type_lieu) {
    this.id_type_lieu = id_type_lieu;
  }

  public String getType_lieu() {
    return type_lieu;
  }

  public void setType_lieu(String type_lieu) {
    this.type_lieu = type_lieu;
  }

  public int getIs_default() {
    return is_default;
  }

  public void setIs_default(int is_default) {
    this.is_default = is_default;
  }

  public String getNom(){
    return nom;
  }

  public void setNom(String nom){
    this.nom=nom;
  }

  public String getVille(){
    return ville;
  }

  public void setVille(String ville){
    this.ville=ville;
  }

  public String getAdresse(){
    return adresse;
  }

  public void setAdresse(String adresse){
    this.adresse=adresse;
  }

  public int getPays(){
    return pays;
  }

  public void setPays(int pays){
    this.pays=pays;
  }

  public String getCode_postal(){
    return code_postal;
  }

  public void setCode_postal(String code_postal){
    this.code_postal=code_postal;
  }

  public String getHash_tag(){
    return hash_tag;
  }

  public void setHash_tag(String hash_tag){
    this.hash_tag=hash_tag;
  }

  public String getPhoto(){
    return photo;
  }

  public void setPhoto(String photo){
    this.photo=photo;
  }

  public java.util.Date getDate_inscription(){
    return date_inscription;
  }

  public void setDate_inscription(java.util.Date date_inscription){
    this.date_inscription=date_inscription;
  }

  public int getStatut(){
    return statut;
  }

  public void setStatut(int statut){
    this.statut=statut;
  }

  public int getType(){
    return type;
  }

  public void setType(int type){
    this.type=type;
  }

  public int getAdded_by(){
    return added_by;
  }

  public void setAdded_by(int added_by){
    this.added_by=added_by;
  }

  public String getEtat(){
    return etat;
  }

  public void setEtat(String etat){
    this.etat=etat;
  }
}