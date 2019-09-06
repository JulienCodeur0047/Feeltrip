package com.packag.ezeety;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.packag.ezeety.Pojos.Lieux;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.SettingView.AdapterHomeLieu;
import com.packag.ezeety.interfaces.IWsServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ezeety_home extends AppCompatActivity {


    ListView recyclerViewTypeLieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_home);

        recyclerViewTypeLieu  = findViewById(R.id.recyclerViewTypeLieu);
        getListTypeLieu();
    }

    public void getListTypeLieu(){
        IWsServices iWsServices = RetrofitFactory.createService(IWsServices.class,"");
        Call<Lieux> call = iWsServices.GetAllLieu();
        call.enqueue(new Callback<Lieux>() {
            @Override
            public void onResponse(Call<Lieux> call, Response<Lieux> response) {
                Lieux lieux = response.body();

                    recyclerViewTypeLieu.setAdapter(new AdapterHomeLieu(ezeety_home.this,lieux));

            }

            @Override
            public void onFailure(Call<Lieux> call, Throwable t) {
                Toast.makeText(ezeety_home.this,"Erreur de serveur durant la chargement des lieux",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
