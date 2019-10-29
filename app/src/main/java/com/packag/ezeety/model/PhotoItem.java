package com.packag.ezeety.model;

import android.graphics.drawable.Drawable;

public class PhotoItem {
    private String id;  // Id from MediaColums
    private String label;
    private String path;
    private Drawable image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getDisplayName(){
        return getPath().substring(getPath().lastIndexOf("/") + 1);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
