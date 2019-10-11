package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class UserSocialNetwork {

    @SerializedName("email")
    private String email;
    @SerializedName("nom_prenom")
    private String nom_prenom;
    @SerializedName("profile_picture")
    private String profil_picture;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom_prenom() {
        return nom_prenom;
    }

    public void setNom_prenom(String nom_prenom) {
        this.nom_prenom = nom_prenom;
    }

    public String getProfil_picture() {
        return profil_picture;
    }

    public void setProfil_picture(String profil_picture) {
        this.profil_picture = profil_picture;
    }
}
