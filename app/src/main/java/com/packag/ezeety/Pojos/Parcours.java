package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class Parcours {

    @SerializedName("id_user")
    private int id_user;
    @SerializedName("id_lieu")
    private int id_lieu;
    @SerializedName("photo")
    private String photo;

    public int getId_user(){
        return id_user;
    }

    public void setId_user(int id_user){
        this.id_user=id_user;
    }

    public int getId_lieu(){
        return id_lieu;
    }

    public void setId_lieu(int id_lieu){
        this.id_lieu=id_lieu;
    }

    public String getPhoto(){
        return photo;
    }

    public void setPhoto(String photo){
        this.photo=photo;
    }

}
