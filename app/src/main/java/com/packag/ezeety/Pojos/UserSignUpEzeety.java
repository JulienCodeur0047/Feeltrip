package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class UserSignUpEzeety {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("nom_prenom")
    private String nom_prenom;
    @SerializedName("google_place_id")
    private String google_place_id;
    @SerializedName("ville")
    private String ville;
    @SerializedName("date_naissance")
    private String date_naissance;
    @SerializedName("pays")
    private String pays;
    @SerializedName("email")
    private String email;
    @SerializedName("profile_pictureString")
    private String profil_picture;

    public File getProfile_pictureFile() {
        return profile_pictureFile;
    }

    public void setProfile_pictureFile(File profile_pictureFile) {
        this.profile_pictureFile = profile_pictureFile;
    }

    @SerializedName("profile_picture")
    private File profile_pictureFile;

    public String getProfil_picture() {
        return profil_picture;
    }

    public void setProfil_picture(String profil_picture) {
        this.profil_picture = profil_picture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom_prenom() {
        return nom_prenom;
    }

    public void setNom_prenom(String nom_prenom) {
        this.nom_prenom = nom_prenom;
    }

    public String getGoogle_place_id() {
        return google_place_id;
    }

    public void setGoogle_place_id(String google_place_id) {
        this.google_place_id = google_place_id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
