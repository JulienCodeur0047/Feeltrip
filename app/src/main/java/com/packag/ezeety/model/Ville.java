package com.packag.ezeety.model;

import com.google.gson.annotations.SerializedName;

public class Ville {

    @SerializedName("id_ville")
    private int id_ville;
    @SerializedName("ville")
    private String ville;
    @SerializedName("ville_ascii")
    private String ville_ascii;
    @SerializedName("pays")
    private String pays;

    public int getId_ville() {
        return id_ville;
    }

    public void setId_ville(int id_ville) {
        this.id_ville = id_ville;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVille_ascii() {
        return ville_ascii;
    }

    public void setVille_ascii(String ville_ascii) {
        this.ville_ascii = ville_ascii;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
