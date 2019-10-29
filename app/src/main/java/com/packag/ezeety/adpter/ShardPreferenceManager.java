package com.packag.ezeety.adpter;

import android.content.Context;
import android.content.SharedPreferences;

public class ShardPreferenceManager {
    private static  ShardPreferenceManager mInstance = null;
    private static Context mCtx;
    private final String SHARED_PREF_NAME  = "ezeety";
    private final String KEY_IMAGE = "imageUri";

    private ShardPreferenceManager(Context context) {
        mCtx = context;
    }

    public static synchronized ShardPreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ShardPreferenceManager(mCtx);
        }
        return mInstance;
    }
    public boolean isImage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IMAGE,false);
      }

    public void putIsImage(Boolean isImg) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IMAGE, isImg);
        editor.apply();
    }


}
