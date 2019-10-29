package com.packag.ezeety.model;

import android.net.Uri;


public class MessageEvent {
    private Uri message;

    public MessageEvent(Uri message) {
        this.message = message;
    }

    public Uri getMessage() {
        return message;
    }

    public void setMessage(Uri message) {
        this.message = message;
    }

}
