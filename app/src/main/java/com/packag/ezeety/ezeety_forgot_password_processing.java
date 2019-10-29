package com.packag.ezeety;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.packag.ezeety.activity.LoginActivity;
import com.packag.ezeety.model.MessageBodyHeader;
import com.packag.ezeety.Services.RetrofitFactory;
import com.packag.ezeety.Services.IWsServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ezeety_forgot_password_processing extends AppCompatActivity {

    EditText editTextNewPassword, editTextConfirmNewPassword;
    TextView textViewbuttonrecuPassword, textViewbuttonOk;
    ImageView imageViewButtonBack;
    Drawable drawableErroreditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_changepsswd);
        editTextNewPassword = findViewById(R.id.edittextnewPsswrd);
        editTextConfirmNewPassword = findViewById(R.id.edittextConfirmNewPsswrd);
        textViewbuttonrecuPassword = findViewById(R.id.textViewbuttonRecupPsswrd);
        imageViewButtonBack = findViewById(R.id.imagebuttonbackChangePsswrd);
        textViewbuttonOk = findViewById(R.id.textViewbuttonOkChangePsswd);
        drawableErroreditText = getResources().getDrawable(R.drawable.ezeety_edittext_style_error);

        textViewbuttonrecuPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPasswrd = editTextNewPassword.getText().toString();
                ResetPasswordProssessing(newPasswrd);
            }
        });

        imageViewButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textViewbuttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPasswrd = editTextNewPassword.getText().toString();
                ResetPasswordProssessing(newPasswrd);
            }
        });

    }
    private void ResetPasswordProssessing(String password){
        String newPasswrd = editTextNewPassword.getText().toString();
        String confirmNewPasswrd = editTextConfirmNewPassword.getText().toString();
        Intent i = getIntent();
        int user_id = i.getIntExtra("userIdForgotPassd",0);
        String reset_code = i.getStringExtra("resetCode");
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        IWsServices iWsServices = retrofit.create(IWsServices.class);
        Call<MessageBodyHeader> messageBodyHeaderCall = iWsServices.resetPassword2(user_id,password,reset_code);
        if(checkNewPasswrd(newPasswrd,confirmNewPasswrd)){
            messageBodyHeaderCall.enqueue(new Callback<MessageBodyHeader>() {
                @Override
                public void onResponse(Call<MessageBodyHeader> call, Response<MessageBodyHeader> response) {
                    String messageStatus = response.body().getMessage();
                    if(response.isSuccessful()){
                        if(messageStatus.equals("Password Reset Successfully")){
                            AlertDialog.Builder AlertSucces = new AlertDialog.Builder(ezeety_forgot_password_processing.this);
                            AlertSucces.setTitle("Confirmation");
                            AlertSucces.setMessage("Votre mot de passe a été modifié avec succès, Veuillez-vous s'identifier");
                            AlertSucces.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intentLogin = new Intent(ezeety_forgot_password_processing.this, LoginActivity.class);
                                    startActivity(intentLogin);
                                    return;
                                }
                            });
                            AlertSucces.show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageBodyHeader> call, Throwable t) {
                    Toast.makeText(ezeety_forgot_password_processing.this, "Server Error or down", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            return;
        }
    }
    public boolean checkNewPasswrd(String newPsswrd, String confirmNew){
        if(newPsswrd.equals(confirmNew)){
            return true;
        } else {
            editTextConfirmNewPassword.setBackground(drawableErroreditText);
            return false;
        }
    }
}
