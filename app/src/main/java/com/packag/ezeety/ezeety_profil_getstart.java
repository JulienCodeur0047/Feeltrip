package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.packag.ezeety.Pojos.User;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ezeety_profil_getstart extends AppCompatActivity {

    TextView textViewbuttonPlusTard;
    Button buttonChangeProfilPics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_profil_getstart);
        textViewbuttonPlusTard = (TextView) findViewById(R.id.textViewButtonPluTard);
        buttonChangeProfilPics = (Button) findViewById(R.id.button_picture_profil_change);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNaissance = null;
        Intent i = getIntent();

        final String nomPrenom =  i.getStringExtra("nomPrenom");
        final String email = i.getStringExtra("email");
        final String password = i.getStringExtra("password");
       /* try {
            dateNaissance = dateFormat.parse(i.getStringExtra("dateNaissance"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Integer hometown = Integer.parseInt(i.getStringExtra("hometown"));
*/
        final Date finalDateNaissance = dateNaissance;
        textViewbuttonPlusTard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                singUpProcessing(null,nomPrenom,password, finalDateNaissance,email, 1);
            }
        });

        buttonChangeProfilPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGalleri = new Intent(ezeety_profil_getstart.this, ezeety_gallerie.class);
                startActivity(intentGalleri);
            }
        });

    }

    private void singUpProcessing(MultipartBody.Part pFile, String pNameFirstName,
                                  String pPassword, Date pBirthday,
                                  String pEmail, int pHometowne){

        String token = getToken();
        IWsServices iWsServices = RetrofitFactory.createService(IWsServices.class,"barea " + token);
        Call<User> callSingUp = iWsServices.signUp(pFile,pNameFirstName,pPassword,pBirthday,pEmail,pHometowne);
        callSingUp.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    Intent intenthome = new Intent(ezeety_profil_getstart.this, ezeety_home.class);
                    startActivity(intenthome);
                }else{
                    Toast.makeText(ezeety_profil_getstart.this,"Erreur durant l'enregistrement de donnees",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(ezeety_profil_getstart.this,"Erreur de serveur",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        Intent i = getIntent();
        return i.getStringExtra("token");
    }

}
