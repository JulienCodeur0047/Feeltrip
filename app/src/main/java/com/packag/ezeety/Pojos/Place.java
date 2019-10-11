package com.packag.ezeety.Pojos;

import com.google.gson.annotations.SerializedName;

public class Place {
    @SerializedName("id")
    private String id_place;
    @SerializedName("description")
    private String description;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("reference")
    private String reference;
    @SerializedName("structured_formatting")
    private StructuredF structuredF;

    public StructuredF getStructuredF() {
        return structuredF;
    }

    public void setStructuredF(StructuredF structuredF) {
        this.structuredF = structuredF;
    }

    public String getId_place() {
        return id_place;
    }

    public void setId_place(String id_place) {
        this.id_place = id_place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
