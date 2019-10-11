package com.packag.ezeety;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.packag.ezeety.Pojos.User;
import com.packag.ezeety.Pojos.UserSignUpEzeety;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;
import com.packag.ezeety.tabbed.ui.tabbedGallerie.ezeety_gallery_activity;

import java.io.File;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ezeety_profil_getstart extends AppCompatActivity {

    ImageView imageViewProfilePicture;
    TextView textViewbuttonPlusTard, textViewbuttonNexAll, textViewbuttonBack;
    Button buttonChangeProfilPics;
    ezeety_registration ezeety_registration = new ezeety_registration();
    ezeety_registration_second ezeety_registration_second = new ezeety_registration_second();
    ezeety_gallery_activity ezeety_gallery_activity = new ezeety_gallery_activity();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_profil_getstart);
        textViewbuttonPlusTard = (TextView) findViewById(R.id.textViewButtonPluTard);
        buttonChangeProfilPics = (Button) findViewById(R.id.button_picture_profil_change);
        imageViewProfilePicture = findViewById(R.id.profile_image_getstart);
        textViewbuttonNexAll = findViewById(R.id.txtViewNextAll);
        textViewbuttonBack = findViewById(R.id.textViewButtonBackProfil);

        textViewbuttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (ezeety_gallery_activity != null){
            String imageProfileUri = com.packag.ezeety.tabbed.ui.tabbedGallerie.ezeety_gallery_activity.imageUri;
            if (imageProfileUri !=null){
                imageViewProfilePicture.setImageURI(Uri.parse(imageProfileUri));
            }
        }

        buttonChangeProfilPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallerie = new Intent(ezeety_profil_getstart.this, ezeety_gallery_activity.class);
                startActivity(intentGallerie);
            }
        });

        String
                RegEmail = ezeety_registration.RegEmail,
                RegPassword = ezeety_registration.RegPassword,
                RegUsername = ezeety_registration_second.RegUsername,
                RegNomPrenom = ezeety_registration_second.RegNomPrenom,
                RegDateNaissance = ezeety_registration_second.RegDateNaissance,
                RegVille = ezeety_registration_second.RegVille,
                RegPays = ezeety_registration_second.RegPays,
                RegPlaceId = ezeety_registration_second.RegGooglePlaceId;

        RequestBody requestBodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"), RegEmail);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), RegPassword);
        RequestBody requestBodyUsername = RequestBody.create(MediaType.parse("multipart/form-data"), RegUsername);
        RequestBody requestBodyNomPrenom = RequestBody.create(MediaType.parse("multipart/form-data"), RegNomPrenom);
        RequestBody requestBodyDateNaissance = RequestBody.create(MediaType.parse("multipart/form-data"), RegDateNaissance);
        RequestBody requestBodyVille = RequestBody.create(MediaType.parse("multipart/form-data"), RegVille);
        RequestBody requestBodyPays = RequestBody.create(MediaType.parse("multipart/form-data"), RegPays);
        RequestBody requestBodyPlaceId = RequestBody.create(MediaType.parse("multipart/form-data"), RegPlaceId);

        textViewbuttonNexAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration(requestBodyUsername,
                        requestBodyNomPrenom,
                        requestBodyEmail,
                        requestBodyPassword,
                        requestBodyDateNaissance,
                        requestBodyPlaceId,
                        requestBodyVille,
                        requestBodyPays,null);
            }
        });

        textViewbuttonPlusTard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Registration(requestBodyUsername,
                        requestBodyNomPrenom,
                        requestBodyEmail,
                        requestBodyPassword,
                        requestBodyDateNaissance,
                        requestBodyPlaceId,
                        requestBodyVille,
                        requestBodyPays,null);
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

    private void Registration(RequestBody username,
                              RequestBody Nom_prenom,
                              RequestBody email,
                              RequestBody password,
                              RequestBody date_naissance,
                              RequestBody google_place_id,
                              RequestBody ville,
                              RequestBody pays,
                              @Nullable File profile_picture){
        IWsServices iWsServices = RetrofitFactory.createService(IWsServices.class);
        Call<UserSignUpEzeety> userSignUpEzeetyCall = iWsServices.singInUptoEzeeety(
                username,
                Nom_prenom,
                email,
                password,
                date_naissance,
                google_place_id,
                ville,
                pays,null);
        userSignUpEzeetyCall.enqueue(new Callback<UserSignUpEzeety>() {
            @Override
            public void onResponse(Call<UserSignUpEzeety> call, Response<UserSignUpEzeety> response) {
                /*UserSignUpEzeety userSignUpEzeety = response.body();
                String message = userSignUpEzeety.getMessage();
                String status = userSignUpEzeety.getStatus();*/

                if (response.isSuccessful()){
                    /*if (message.equals("User Signed Up Successfully")){

                     */
                        AlertDialog.Builder alertDialogusernamExist = new AlertDialog.Builder(ezeety_profil_getstart.this);
                        alertDialogusernamExist.setTitle("Information");
                        alertDialogusernamExist.setMessage("vous etes inscrit sur Ezeety, veuillez vous indentifier.");
                        alertDialogusernamExist.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intentLogin = new Intent(ezeety_profil_getstart.this, ezeety_login.class);
                                startActivity(intentLogin);
                                return;
                            }
                        });
                        alertDialogusernamExist.show();
                    //}
                }
            }

            @Override
            public void onFailure(Call<UserSignUpEzeety> call, Throwable t) {

            }
        });

    }
}
