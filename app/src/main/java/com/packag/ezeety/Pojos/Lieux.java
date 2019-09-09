package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Lieux {


    private ArrayList<Lieu> listLieu;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Lieu> getListLieu() {
        return listLieu;
    }

    public void setListLieu(ArrayList<Lieu> listLieu) {
        this.listLieu = listLieu;
    }
}
