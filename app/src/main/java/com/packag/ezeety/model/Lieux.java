package com.packag.ezeety.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Lieux {


    @SerializedName("error")
    private int error;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<Lieu> listLieu;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
