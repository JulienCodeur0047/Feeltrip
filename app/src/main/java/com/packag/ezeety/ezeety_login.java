package com.packag.ezeety;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.packag.ezeety.Pojos.LoginResponse;
import com.packag.ezeety.Pojos.MessageBodyHeader;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ezeety_login extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonConnexion,
            buttonConnexionGoogle;

    LoginButton buttonConnexionFacebook;
    TextView textViewResetPassword
            ,textViewInscrire,textViewErrorPassword;

    private String token;
    private int currentUserId;

    private CallbackManager callbackManager;


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
        textViewErrorPassword = findViewById(R.id.textViewErrorPassword);



        callbackManager = CallbackManager.Factory.create();


        final AccessToken accessToken =AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && accessToken.isExpired();

        buttonConnexionFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(ezeety_login.this, Arrays.asList("public_profile"));
            }
        });

        buttonConnexionFacebook.setReadPermissions(Arrays.asList("email","public_profile"));

        buttonConnexionFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginProcessViafacebook(accessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });




        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                    textViewErrorPassword.setText("");
                    editTextPassword.setBackground(Drawable.createFromPath("@drawable/ezeety_edittext_style"));

                return true;
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void LoginProcessViafacebook(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try {
                    final String first_nameUserFb = object.getString("first_name");
                    final String last_nameUserFb = object.getString("last_name");
                    final String email = object.getString("email");
                    final String idUserFb = object.getString("id");
                    final String image_url = "https://graph.facebook.com/"+idUserFb+ "/picture?type=normal";

                    Retrofit retrofit = RetrofitFactory.getRetrofit();
                    IWsServices iWsServices = retrofit.create(IWsServices.class);

                    Call<LoginResponse> call = iWsServices.isValideUser(email,null,0);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.isSuccessful()){
                                LoginResponse result = response.body();
                                if(result.getMessage().equals("Login Successful")){
                                    token = result.getUser().getToken();

                                    Intent intent = new Intent(ezeety_login.this, ezeety_home.class);
                                    intent.putExtra("token", token);
                                    intent.putExtra("currentProfilFBUserId",idUserFb);
                                    intent.putExtra("currentUserProfilFBLastName", last_nameUserFb);
                                    intent.putExtra("currentUserProfilFBFirstName",first_nameUserFb);
                                    intent.putExtra("currentUserProfilFBEmail", email);
                                    intent.putExtra("currentUserProfilFBimageProfilUri", image_url);
                                    startActivity(intent);
                                }
                                else {

                                    AlertDialog.Builder alerteNoEmail = new AlertDialog.Builder(ezeety_login.this);
                                    alerteNoEmail.setTitle("E-mail Incorrect");
                                    alerteNoEmail.setMessage("L'adresse e-mail que vous avez saisie ne correspond à aucun compte via facebook");
                                    alerteNoEmail.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            return;
                                        }
                                    });
                                    alerteNoEmail.show();

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void LoginProcessing(String email, String password) {

        Retrofit retrofit = RetrofitFactory.getRetrofit();
        IWsServices iWsServices = retrofit.create(IWsServices.class);

        Call<LoginResponse> call = iWsServices.isValideUser(email,password,1);
        Call<MessageBodyHeader> callAlreadyEmail = iWsServices.isEmailAlready002(email);


        callAlreadyEmail.enqueue(new Callback<MessageBodyHeader>() {
                @Override
                public void onResponse(Call<MessageBodyHeader> call, Response<MessageBodyHeader> response) {
                    Integer AlreadyExist = response.body().getBodyData().getEmailExists();
                    if(AlreadyExist == 0){
                        AlertDialog.Builder alerteNoEmail = new AlertDialog.Builder(ezeety_login.this);
                        alerteNoEmail.setTitle("E-mail Incorrect");
                        alerteNoEmail.setMessage("L'adresse e-mail que vous avez saisie ne correspond à aucun compte");
                        alerteNoEmail.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        alerteNoEmail.show();
                    }
                }

                @Override
                public void onFailure(Call<MessageBodyHeader> call, Throwable t) {

                }
            });

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    LoginResponse result = response.body();
                    if(result.getMessage().equals("Login Successful")){
                        token = result.getUser().getToken();
                        currentUserId = result.getUser().getId();
                        Intent intent = new Intent(ezeety_login.this, ezeety_home.class);
                        intent.putExtra("token", token);
                        intent.putExtra("currentUserId",currentUserId);
                        startActivity(intent);
                    }
                    else {

                        textViewErrorPassword.setText("Ce mot de passe est incorrect, Réessayer");
                        editTextPassword.setBackground(Drawable.createFromPath("@drawable/ezeety_edittext_style_error"));

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
