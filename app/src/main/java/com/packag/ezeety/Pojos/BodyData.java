package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class BodyData {

    @SerializedName("emailExists")
    private int emailExists;
    @SerializedName("reset_code")
    private int resetCode;
    public int getResetCode() {
        return resetCode;
    }

    public void setResetCode(int resetCode) {
        this.resetCode = resetCode;
    }



    public int getEmailExists() {
        return emailExists;
    }

    public void setEmailExists(int emailExists) {
        this.emailExists = emailExists;
    }



}
