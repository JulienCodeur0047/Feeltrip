package com.packag.ezeety.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ListView;
import android.widget.Toast;

import com.packag.ezeety.R;
import com.packag.ezeety.adpter.RecyclerViewAdapter;
import com.packag.ezeety.model.Lieu;
import com.packag.ezeety.model.Lieux;
import com.packag.ezeety.Services.RetrofitFactory;
import com.packag.ezeety.Services.IWsServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {



    RecyclerView recyclerViewTypeLieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_home);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTypeLieu  = findViewById(R.id.recyclerViewTypeLieu);
        recyclerViewTypeLieu.setLayoutManager(layoutManager);
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

                List<String> listtypeLieudescription = new ArrayList<String>();
                Lieux lieux = response.body();
                if(response.body() == null){
                    Intent intentReLogin = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intentReLogin);
                    Toast.makeText(HomeActivity.this, "Session expirée veuillez-vous reconnecter à nouveau.", Toast.LENGTH_SHORT).show();
                }
                else {

                    for (Lieu l : response.body().getListLieu()){
                        listtypeLieudescription.add(l.getType_lieu());
                    }
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(listtypeLieudescription,null,HomeActivity.this);
                    recyclerViewTypeLieu.setAdapter(recyclerViewAdapter);

                }

                    //recyclerViewTypeLieu.setAdapter(new AdapterHomeLieu(HomeActivity.this,lieux));

            }

            @Override
            public void onFailure(Call<Lieux> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Erreur",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAuthToken() {
        Intent i = getIntent();
        return i.getStringExtra("token");
    }


}
