package com.packag.ezeety.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypesLieux {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<TypeLieu> list_type_lieu;

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

    public List<TypeLieu> getList_type_lieu() {
        return list_type_lieu;
    }

    public void setList_type_lieu(List<TypeLieu> list_type_lieu) {
        this.list_type_lieu = list_type_lieu;
    }
}
