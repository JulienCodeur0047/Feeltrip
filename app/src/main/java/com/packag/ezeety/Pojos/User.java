package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("token")
    private String token;
    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("reset_password_code")
    private String reset_password_code;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("genre")
    private String genre;
    @SerializedName("date_naissance")
    private java.util.Date date_naissance;
    @SerializedName("metier")
    private String metier;
    @SerializedName("email")
    private String email;
    @SerializedName("site_web")
    private String site_web;
    @SerializedName("photo")
    private String photo;
    @SerializedName("bio")
    private String bio;
    @SerializedName("id_lieu")
    private int id_lieu;
    @SerializedName("type_ambassadeur")
    private int type_ambassadeur;
    @SerializedName("date_inscription")
    private java.util.Date date_inscription;
    @SerializedName("is_certified")
    private int is_certified;
    @SerializedName("is_blacklist")
    private int is_blacklist;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getReset_password_code(){
        return reset_password_code;
    }

    public void setReset_password_code(String reset_password_code){
        this.reset_password_code=reset_password_code;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre(String genre){
        this.genre=genre;
    }

    public java.util.Date getDate_naissance(){
        return date_naissance;
    }

    public void setDate_naissance(java.util.Date date_naissance){
        this.date_naissance=date_naissance;
    }

    public String getMetier(){
        return metier;
    }

    public void setMetier(String metier){
        this.metier=metier;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getSite_web(){
        return site_web;
    }

    public void setSite_web(String site_web){
        this.site_web=site_web;
    }

    public String getPhoto(){
        return photo;
    }

    public void setPhoto(String photo){
        this.photo=photo;
    }

    public String getBio(){
        return bio;
    }

    public void setBio(String bio){
        this.bio=bio;
    }

    public int getId_lieu(){
        return id_lieu;
    }

    public void setId_lieu(int id_lieu){
        this.id_lieu=id_lieu;
    }

    public int getType_ambassadeur(){
        return type_ambassadeur;
    }

    public void setType_ambassadeur(int type_ambassadeur){
        this.type_ambassadeur=type_ambassadeur;
    }

    public java.util.Date getDate_inscription(){
        return date_inscription;
    }

    public void setDate_inscription(java.util.Date date_inscription){
        this.date_inscription=date_inscription;
    }

    public int getIs_certified(){
        return is_certified;
    }

    public void setIs_certified(int is_certified){
        this.is_certified=is_certified;
    }

    public int getIs_blacklist(){
        return is_blacklist;
    }

    public void setIs_blacklist(int is_blacklist){
        this.is_blacklist=is_blacklist;
    }
}
