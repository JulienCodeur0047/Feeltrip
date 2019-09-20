package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class TypeLieu {

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
}
