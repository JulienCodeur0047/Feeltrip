package com.packag.ezeety.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.packag.ezeety.R;
import com.packag.ezeety.model.MessageBodyHeader;
import com.packag.ezeety.model.UserSocialNetwork;
import com.packag.ezeety.Services.RetrofitFactory;
import com.packag.ezeety.Services.IWsServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationAcivity extends AppCompatActivity {

    TextView textViewLogin, textViewErrorEmailAlready;
    Button buttonNextStep1,  buttonRegByGoogle;
    LoginButton buttonRegByfacebook;
    CheckBox checkBoxSavePswd;
    EditText editTextEmailReg, editTextPasswordReg;
    Drawable drawablebackFroundError;
    private String token;
    private int currentUserId;
    private CallbackManager callbackManager;
    public static String RegEmail, RegPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_registration_first_step);

        textViewLogin = findViewById(R.id.textViewLogin);
        buttonNextStep1 = findViewById(R.id.buttonNextRegistrationFirst);
        buttonRegByfacebook = findViewById(R.id.buttonRegistrationFacebook);
        buttonRegByGoogle = findViewById(R.id.buttonRegistrationGoogle);
        checkBoxSavePswd = findViewById(R.id.checkboxSavePasswordRegistration);
        editTextEmailReg = findViewById(R.id.editTextEmailRegistration);
        editTextPasswordReg = findViewById(R.id.editTextPasswordRegistration);
        textViewErrorEmailAlready = findViewById(R.id.textViewErrorMailAlreadyExist);
        drawablebackFroundError = getResources().getDrawable(R.drawable.ezeety_edittext_style_error);
        final AccessToken accessToken =AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && accessToken.isExpired();
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(RegistrationAcivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });
        callbackManager = CallbackManager.Factory.create();
        buttonRegByfacebook.setReadPermissions(Arrays.asList("email","public_profile"));

        buttonRegByfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(RegistrationAcivity.this, Arrays.asList("public_profile"));
            }
        });
        buttonRegByfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                RegisterProcessViafacebook(accessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        buttonNextStep1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String emailReg = editTextEmailReg.getText().toString(),
                        passwordReg = editTextPasswordReg.getText().toString();

                RegEmail = emailReg;
                RegPassword = passwordReg;


                if(!(emailReg.contains("@") || emailReg.contains("."))){
                    AlertDialog.Builder messageAlerte = new AlertDialog.Builder(RegistrationAcivity.this);
                    messageAlerte.setTitle("Alerte");
                    messageAlerte.setMessage("e-mail invalid");
                    messageAlerte.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                        }
                    });
                    messageAlerte.show();

                    return;
                }

                if(passwordReg.length() < 8){
                    Toast.makeText(RegistrationAcivity.this, "Le mot de passe ne doit pas être moins de 8 caractères.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = RetrofitFactory.getRetrofit();
                IWsServices iWsServices = retrofit.create(IWsServices.class);
                Call<MessageBodyHeader> callAlreadyEmail = iWsServices.isEmailAlready(emailReg);
                callAlreadyEmail.enqueue(new Callback<MessageBodyHeader>() {
                    @Override
                    public void onResponse(Call<MessageBodyHeader> call, Response<MessageBodyHeader> response) {
                        if(response.body().getBodyData().getEmailExists() == 0){
                            if(checkFormt(emailReg,passwordReg)){
                                Intent intentSecond = new Intent(RegistrationAcivity.this, RegistrationNextActivity.class);
                                startActivity(intentSecond);
                            } return;
                        }else {
                            textViewErrorEmailAlready.setText("Il semblerait qu’il y a déjà un compte ezeety avec cette adresse e-mail.");
                            editTextEmailReg.setBackground(drawablebackFroundError);
                        }



                    }

                    @Override
                    public void onFailure(Call<MessageBodyHeader> call, Throwable t) {
                        Toast.makeText(RegistrationAcivity.this, "Erreur", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

    }
    private void RegisterProcessViafacebook(AccessToken accessToken){
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

                    /*if(email==null||
                            email==""||
                            !email.contains("@")||
                            !email.contains(".")||
                            !email.getClass().equals(Type.class)||
                            !email.matches("^[a-zA-Z]*$")){
                        AlertDialog.Builder alerteNoEmail = new AlertDialog.Builder(RegistrationAcivity.this);
                        alerteNoEmail.setTitle("E-mail Incorrect");
                        alerteNoEmail.setMessage("vous devez inserer un e-mail correspond a un compte facebook");
                        alerteNoEmail.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        alerteNoEmail.show();

                    }else {*/
                        Call<UserSocialNetwork> calluserSocialNetwork = iWsServices.RegisterBySosialNetwork(email,
                                first_nameUserFb+"_"+last_nameUserFb,
                                image_url);
                        calluserSocialNetwork.enqueue(new Callback<UserSocialNetwork>() {
                            @Override
                            public void onResponse(Call<UserSocialNetwork> call, Response<UserSocialNetwork> response) {


                                if(response.isSuccessful()){
                                    AlertDialog.Builder alerteNoEmail = new AlertDialog.Builder(RegistrationAcivity.this);
                                    alerteNoEmail.setTitle("Information");
                                    alerteNoEmail.setMessage("Vous êtes maintenant inscrit sur Ezeety, veuillez-vous connecter via Facebook.");
                                    alerteNoEmail.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intentLogin = new Intent(RegistrationAcivity.this, LoginActivity.class);
                                            startActivity(intentLogin);
                                            return;
                                        }
                                    });
                                    alerteNoEmail.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserSocialNetwork> call, Throwable t) {

                            }
                        });
                    //}





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
    private boolean checkFormt(String emailReg, String passwordReg) {
        if(emailReg == null || emailReg.trim().length() == 0){
            Toast.makeText(RegistrationAcivity.this,"Entrez votre adresse e-mail",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordReg == null || passwordReg.trim().length() == 0){
            Toast.makeText(RegistrationAcivity.this,"Entrez votre mot de passe",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
