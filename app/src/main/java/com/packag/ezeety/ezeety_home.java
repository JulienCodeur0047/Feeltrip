package com.packag.ezeety;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.packag.ezeety.Pojos.Lieux;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.SettingView.AdapterHomeLieu;
import com.packag.ezeety.interfaces.IWsServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ezeety_home extends AppCompatActivity {


    ListView recyclerViewTypeLieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_home);

        recyclerViewTypeLieu  = findViewById(R.id.recyclerViewTypeLieu);
    }

    public void getListTypeLieu(View view){
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        IWsServices iWsServices = retrofit.create(IWsServices.class);
        Call<Lieux> call = iWsServices.GetAllLieu();
        call.enqueue(new Callback<Lieux>() {
            @Override
            public void onResponse(Call<Lieux> call, Response<Lieux> response) {
                Lieux lieux = response.body();
                recyclerViewTypeLieu.setAdapter(new AdapterHomeLieu(ezeety_home.this,lieux));
            }

            @Override
            public void onFailure(Call<Lieux> call, Throwable t) {

            }
        });
    }
}
