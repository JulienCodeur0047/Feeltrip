package com.packag.ezeety;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ezeety_registration extends AppCompatActivity {

    TextView textViewLogin;
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
                String emailReg = editTextEmailReg.getText().toString(),
                        passwordReg = editTextPasswordReg.getText().toString();
                if(checkFormt(emailReg,passwordReg)){
                    Intent intentSecond = new Intent(ezeety_registration.this, ezeety_registration_second.class);
                        intentSecond.putExtra("emailReg", emailReg);
                        intentSecond.putExtra("passwordReg", passwordReg);
                    startActivity(intentSecond);
                } return;

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
}
