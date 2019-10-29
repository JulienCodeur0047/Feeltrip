package com.packag.ezeety.model;

import java.util.ArrayList;

public class ModelImage {
    String foldername;
    ArrayList<String> allimagepath;

    public String getFolderName() {
        return foldername;
    }

    public void setFolderName(String foldername) {
        this.foldername = foldername;
    }

    public ArrayList<String> getAllimagePath() {
        return allimagepath;
    }

    public void setAllimagePath(ArrayList<String> allimagepath) {
        this.allimagepath = allimagepath;
    }
}
