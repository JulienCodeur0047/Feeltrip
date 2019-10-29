package com.packag.ezeety.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {

    @SerializedName("data")
    private List<User> listUser;

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }
}
