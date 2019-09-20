package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Villes {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<Ville> listVille;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Ville> getListVille() {
        return listVille;
    }

    public void setListVille(List<Ville> listVille) {
        this.listVille = listVille;
    }
}
