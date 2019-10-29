package com.packag.ezeety.model;

import com.google.gson.annotations.SerializedName;

public class BodyData {

    @SerializedName("emailExists")
    private int emailExists;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("reset_code")
    private String resetCode;
    @SerializedName("password")
    private String password;
    @SerializedName("usernameExists")
    private int usernameExists;
    @SerializedName("username")
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUsernameExists() {
        return usernameExists;
    }

    public void setUsernameExists(int usernameExists) {
        this.usernameExists = usernameExists;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEmailExists() {
        return emailExists;
    }

    public void setEmailExists(int emailExists) {
        this.emailExists = emailExists;
    }



}
