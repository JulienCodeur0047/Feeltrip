package com.packag.ezeety;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.packag.ezeety.Pojos.LoginResponse;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonConnexion, buttonConnexionFacebook,
            buttonConnexionGoogle;
    TextView textViewResetPassword
            ,textViewInscrire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAllWidget();
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
    }

    private void setAllWidget() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonConnexion = (Button) findViewById(R.id.buttonConnexion);
        buttonConnexionFacebook = (Button) findViewById(R.id.buttonConnexionFacebook);
        buttonConnexionGoogle = (Button) findViewById(R.id.buttonConnexionGoogle);
        textViewResetPassword = (TextView) findViewById(R.id.textViewMotdepaaseOublier);
        textViewInscrire = (TextView) findViewById(R.id.textViewInscrire);
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
                    String message = result.getMessage().toString();
                    if(result.getMessage().equals("login successful")){
                        //Show Home activity
                        Intent intent = new Intent(MainActivity.this, ezeety_home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this,"email ou mot de passe incorrect",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this,"Erreur durant le Chargement, veuillez Ressayer",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(MainActivity.this,"Erreur de connexion Internet",Toast.LENGTH_SHORT).show();
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
