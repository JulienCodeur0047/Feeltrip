package com.packag.ezeety.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GooglePlaces {
    @SerializedName("status")
    private String status;
    @SerializedName("predictions")
    private ArrayList<Place> listPlace;

    public ArrayList<Place> getListPlace() {
        return listPlace;
    }

    public void setListPlace(ArrayList<Place> listPlace) {
        this.listPlace = listPlace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

