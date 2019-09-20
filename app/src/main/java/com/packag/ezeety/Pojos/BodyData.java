package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class BodyData {

    @SerializedName("emailExists;")
    private int emailExists;

    public int getEmailExists() {
        return emailExists;
    }

    public void setEmailExists(int emailExists) {
        this.emailExists = emailExists;
    }



}
