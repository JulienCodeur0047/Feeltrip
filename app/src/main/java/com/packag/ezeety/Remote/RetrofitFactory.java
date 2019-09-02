package com.packag.ezeety.Remote;

import android.content.res.Resources;

import com.packag.ezeety.R;

import java.util.ResourceBundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static Retrofit retrofit = null;

    private RetrofitFactory(){}

    public static Retrofit getRetrofit(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.162.69.209:3001/api/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
