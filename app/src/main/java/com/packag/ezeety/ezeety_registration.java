package com.packag.ezeety;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.packag.ezeety.Pojos.MessageBodyHeader;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ezeety_registration extends AppCompatActivity {

    TextView textViewLogin, textViewErrorEmailAlready;
    Button buttonNextStep1, buttonRegByfacebook, buttonRegByGoogle;
    CheckBox checkBoxSavePswd;
    EditText editTextEmailReg, editTextPasswordReg;

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

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(ezeety_registration.this, ezeety_login.class);
                startActivity(intentLogin);
            }
        });

        buttonNextStep1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String emailReg = editTextEmailReg.getText().toString(),
                        passwordReg = editTextPasswordReg.getText().toString();

                if(!(emailReg.contains("@") || emailReg.contains("."))){
                    AlertDialog.Builder messageAlerte = new AlertDialog.Builder(ezeety_registration.this);
                    messageAlerte.setTitle("Alerte");
                    messageAlerte.setMessage("e-mail invalid");
                    messageAlerte.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    messageAlerte.show();

                    return;
                }

                if(passwordReg.length() < 8){
                    Toast.makeText(ezeety_registration.this, "Le mot de passe ne doit pas être moins de 8 caractères.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = RetrofitFactory.getRetrofit();
                IWsServices iWsServices = retrofit.create(IWsServices.class);
                Call<MessageBodyHeader> callAlreadyEmail = iWsServices.isEmailAlready002(emailReg);
                callAlreadyEmail.enqueue(new Callback<MessageBodyHeader>() {
                    @Override
                    public void onResponse(Call<MessageBodyHeader> call, Response<MessageBodyHeader> response) {
                        if(response.body().getBodyData().getEmailExists() == 0){
                            if(checkFormt(emailReg,passwordReg)){
                                Intent intentSecond = new Intent(ezeety_registration.this, ezeety_registration_second.class);
                                intentSecond.putExtra("emailReg", emailReg);
                                intentSecond.putExtra("passwordReg", passwordReg);
                                startActivity(intentSecond);
                            } return;
                        }else {
                            textViewErrorEmailAlready.setText("Il semblerait qu’il y a déjà un compte ezeety avec cette adresse e-mail.");
                        }



                    }

                    @Override
                    public void onFailure(Call<MessageBodyHeader> call, Throwable t) {
                        Toast.makeText(ezeety_registration.this, "No internet connection", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

    private boolean checkFormt(String emailReg, String passwordReg) {
        if(emailReg == null || emailReg.trim().length() == 0){
            Toast.makeText(ezeety_registration.this,"Entrez votre adresse e-mail",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordReg == null || passwordReg.trim().length() == 0){
            Toast.makeText(ezeety_registration.this,"Entrez votre mot de passe",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkEmailAlready(String email){
        final int[] emailexist = new int[1];
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        IWsServices iWsServices = retrofit.create(IWsServices.class);
        Call<MessageBodyHeader> callAlreadyEmail = iWsServices.isEmailAlready002(email);
        callAlreadyEmail.enqueue(new Callback<MessageBodyHeader>() {
    @Override
    public void onResponse(Call<MessageBodyHeader> call, Response<MessageBodyHeader> response) {

        MessageBodyHeader result = response.body();
        emailexist[0] = result.getBodyData().getEmailExists();

    }

    @Override
    public void onFailure(Call<MessageBodyHeader> call, Throwable t) {

    }
});

        if(emailexist[0] == 1){
            return true;
        } return false;
    }
}
