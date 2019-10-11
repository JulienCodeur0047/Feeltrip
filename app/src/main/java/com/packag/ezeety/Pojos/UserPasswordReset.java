package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class UserPasswordReset {

    @SerializedName("user_id")
    private int user_id;
    @SerializedName("password")
    private String password;
    @SerializedName("reset_code")
    private String reset_code;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReset_code() {
        return reset_code;
    }

    public void setReset_code(String reset_code) {
        this.reset_code = reset_code;
    }
}
