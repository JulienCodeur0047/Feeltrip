package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class StructuredF {
    @SerializedName("secondary_text")
    private String Pays;

    public String getPays() {
        return Pays;
    }

    public void setPays(String pays) {
        Pays = pays;
    }
}
