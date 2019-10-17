package com.packag.ezeety;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.packag.ezeety.Pojos.UserSignUpEzeety;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;
import com.packag.ezeety.tabbed.ui.tabbedGallerie.ezeety_gallery_activity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ezeety_profil_getstart extends AppCompatActivity {

    ImageView imageViewProfilePicture;
    TextView buttonNext, textViewChangeProfilPics;
    ImageView imageViewbuttonBack;
    Drawable picture_profil_default;
    ezeety_registration ezeety_registration = new ezeety_registration();
    ezeety_registration_second ezeety_registration_second = new ezeety_registration_second();
    ezeety_gallery_activity ezeety_gallery_activity = new ezeety_gallery_activity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_profil_getstart);
        buttonNext = (TextView) findViewById(R.id.buttonNext);
        textViewChangeProfilPics = (TextView) findViewById(R.id.textViewprofilPicchange);
        imageViewProfilePicture = findViewById(R.id.profile_image_getstart);
        imageViewbuttonBack = findViewById(R.id.imageViewButtonBackProfil);
        picture_profil_default = getResources().getDrawable(R.drawable.photo);

        imageViewbuttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (ezeety_gallery_activity != null){
            String imageProfileUri = ezeety_gallery_activity.imageUri;
            if (imageProfileUri !=null){
                imageViewProfilePicture.setImageURI(Uri.parse(imageProfileUri));
            } else {
                imageViewProfilePicture.setImageDrawable(picture_profil_default);
            }
        }

        textViewChangeProfilPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ezeety_gallery_activity.imageFilePick = null;
                ezeety_gallery_activity.imageUri = null;
                Intent intentGallerie = new Intent(ezeety_profil_getstart.this, ezeety_gallery_activity.class);
                startActivity(intentGallerie);
            }
        });

        if(ezeety_registration_second != null || ezeety_registration != null ){
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Registration(getrequestbody(ezeety_registration_second.RegUsername),
                            getrequestbody(ezeety_registration_second.RegNomPrenom),
                            getrequestbody(ezeety_registration.RegEmail),
                            getrequestbody(ezeety_registration.RegPassword),
                            getrequestbody(ezeety_registration_second.RegDateNaissance),
                            getrequestbody(ezeety_registration_second.RegGooglePlaceId),
                            getrequestbody(ezeety_registration_second.RegVille),
                            getrequestbody(ezeety_registration_second.RegPays),
                            ezeety_gallery_activity.imageFilePick);
                }
            });
        }
    }



    private String getToken() {
      return null;
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
                pays,
                profile_picture);
        userSignUpEzeetyCall.enqueue(new Callback<UserSignUpEzeety>() {
            @Override
            public void onResponse(Call<UserSignUpEzeety> call, Response<UserSignUpEzeety> response) {
                if (response.isSuccessful()){
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
                }
            }

            @Override
            public void onFailure(Call<UserSignUpEzeety> call, Throwable t) {

            }
        });

    }
    private RequestBody getrequestbody(String stringValue){
        RequestBody  requestBodyByStringValue = RequestBody.create(MediaType.parse("multipart/form-data"), stringValue);
        return requestBodyByStringValue;
    }
}
