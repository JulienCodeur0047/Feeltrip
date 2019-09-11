package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.packag.ezeety.Pojos.Lieux;
import com.packag.ezeety.Remote.RetrofitFactory;
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


        String token = getAuthToken();
        IWsServices iWsServices = RetrofitFactory.createService(IWsServices.class,"barea " + token
                );
        Call<Lieux> call = iWsServices.GetAllLieu();
        call.enqueue(new Callback<Lieux>() {
            @Override
            public void onResponse(Call<Lieux> call, Response<Lieux> response) {

                if(response.body() == null){
                    Intent intentReLogin = new Intent(ezeety_home.this, ezeety_login.class);
                    startActivity(intentReLogin);
                    Toast.makeText(ezeety_home.this, "Session expirée veuillez-vous reconnecter à nouveau.", Toast.LENGTH_SHORT).show();
                }

                    //recyclerViewTypeLieu.setAdapter(new AdapterHomeLieu(ezeety_home.this,lieux));

            }

            @Override
            public void onFailure(Call<Lieux> call, Throwable t) {
                Toast.makeText(ezeety_home.this,"Erreur de serveur durant la chargement des lieux",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAuthToken() {
        Intent i = getIntent();
        return i.getStringExtra("token");
    }
}
