package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.packag.ezeety.Pojos.LoginResponse;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ezeety_login extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonConnexion, buttonConnexionFacebook,
            buttonConnexionGoogle;
    TextView textViewResetPassword
            ,textViewInscrire;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonConnexion = findViewById(R.id.buttonConnexion);
        buttonConnexionFacebook = findViewById(R.id.buttonConnexionFacebook);
        buttonConnexionGoogle = findViewById(R.id.buttonConnexionGoogle);
        textViewResetPassword = findViewById(R.id.textViewMotdepaaseOublier);
        textViewInscrire = findViewById(R.id.textViewInscrire);

        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString(),
                        password = editTextPassword.getText().toString();
                if(LoginFormEmpty(email,password)){
                    LoginProcessing(email,password);
                } return;
            }


        });
        textViewInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistration = new Intent(ezeety_login.this, ezeety_registration.class);
                startActivity(intentRegistration);
            }
        });
        textViewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void LoginProcessing(String email, String password) {

        Retrofit retrofit = RetrofitFactory.getRetrofit();
        IWsServices iWsServices = retrofit.create(IWsServices.class);
        Call<LoginResponse> call = iWsServices.isValideUser(email,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    LoginResponse result = response.body();
                    if(result.getMessage().equals("login successful")){
                        //Show Home activity
                        Intent intent = new Intent(ezeety_login.this, ezeety_home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ezeety_login.this,"email ou mot de passe incorrect",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(ezeety_login.this,"Erreur durant le Chargement, veuillez Ressayer",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(ezeety_login.this,"Erreur de connexion Internet",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean LoginFormEmpty(String email, String password){

        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Entrez votre adresse e-mail",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Entrez votre mot de passe",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}
