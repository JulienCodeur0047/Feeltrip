package com.packag.ezeety;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.packag.ezeety.activity.LoginActivity;
import com.packag.ezeety.activity.RegistrationAcivity;
import com.packag.ezeety.activity.RegistrationNextActivity;
import com.packag.ezeety.fragment.CameraFragment;
import com.packag.ezeety.model.UserSignUpEzeety;
import com.packag.ezeety.Services.RetrofitFactory;
import com.packag.ezeety.Services.IWsServices;
import com.packag.ezeety.activity.TabAcrivity;
import com.packag.ezeety.tabbed.ui.tabbedGallerie.ezeety_gallery_activity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ezeety_profil_getstart extends AppCompatActivity {

    public static ImageView imageViewProfilePicture;
    TextView buttonNext, textViewChangeProfilPics;
    ImageView imageViewbuttonBack;
    Drawable picture_profil_default;
    RegistrationAcivity ezeety_registration = new RegistrationAcivity();
    RegistrationNextActivity ezeety_registration_second = new RegistrationNextActivity();
    ezeety_gallery_activity ezeety_gallery_activity = new ezeety_gallery_activity();
    private static final int CAMERA_REQUEST = 1888;
    public static Uri uriImage;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    CameraFragment cameraFragment = new CameraFragment();
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

        imageViewProfilePicture.setOnClickListener(new CircleImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewProfilePicture.setImageResource(R.drawable.photo);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
                else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });


        Intent intent =getIntent();
        String imageurix = intent.getStringExtra("uri");

        uriImage = cameraFragment.uriImage;


        if(uriImage != null){
            imageViewProfilePicture.setImageURI(uriImage);
        } else {
            imageViewProfilePicture.setImageDrawable(picture_profil_default);
        }

       /* if (ezeety_gallery_activity != null){
            String imageProfileUri = ezeety_gallery_activity.imageUri;
            if (imageProfileUri !=null){
                imageViewProfilePicture.setImageURI(Uri.parse(intent.getStringExtra("uri")));
            } else {
                imageViewProfilePicture.setImageDrawable(picture_profil_default);
            }
        } */

        textViewChangeProfilPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ezeety_gallery_activity.imageFilePick = null;
                ezeety_gallery_activity.imageUri = null;

                Intent intentGallerie = new Intent(ezeety_profil_getstart.this, TabAcrivity.class);
                startActivity(intentGallerie);
            }
        });

       /* if(RegistrationNextActivity != null || RegistrationAcivity != null ){ */
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Registration(getrequestbody(RegistrationNextActivity.RegUsername),
                            getrequestbody(RegistrationNextActivity.RegNomPrenom),
                            getrequestbody(RegistrationAcivity.RegEmail),
                            getrequestbody(RegistrationAcivity.RegPassword),
                            getrequestbody(RegistrationNextActivity.RegDateNaissance),
                            getrequestbody(RegistrationNextActivity.RegGooglePlaceId),
                            getrequestbody(RegistrationNextActivity.RegVille),
                            getrequestbody(RegistrationNextActivity.RegPays),
                            new File(uriImage.toString()));
                }
            });

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
                                Intent intentLogin = new Intent(ezeety_profil_getstart.this, LoginActivity.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            uriImage = data.getData();
            if (uriImage != null){
                imageViewProfilePicture.setImageURI(null);
            }
            //imageViewProfilePicture.setImageBitmap(photo);
            imageViewProfilePicture.setImageURI(uriImage);
            imageViewProfilePicture.setRotation(-90);
        }
    }
}
