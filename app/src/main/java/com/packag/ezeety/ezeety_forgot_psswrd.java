package com.packag.ezeety;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.packag.ezeety.Pojos.BodyData;
import com.packag.ezeety.Pojos.MessageBodyHeader;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.interfaces.IWsServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ezeety_forgot_psswrd extends AppCompatActivity {

    EditText editTextVerifyEmail;
    Button buttonGetPasswordChange;
    ImageView imageViewbuttonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_forgot_password);
        editTextVerifyEmail = findViewById(R.id.editTextEmailVerify);
        buttonGetPasswordChange = findViewById(R.id.buttonRecuperePasswrd);
        imageViewbuttonBack = findViewById(R.id.imagebuttonbackChangePsswrd);


        buttonGetPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verifiyEmail = editTextVerifyEmail.getText().toString();

                    ForgotPassword(verifiyEmail);

            }
        });

        imageViewbuttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void ForgotPassword(String email){
        EditText resetCodeEdittext = new EditText(ezeety_forgot_psswrd.this);
        resetCodeEdittext.setTextAlignment(EditText.TEXT_ALIGNMENT_CENTER);
        resetCodeEdittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout lnLayout = new LinearLayout(ezeety_forgot_psswrd.this);
        lnLayout.setOrientation(LinearLayout.VERTICAL);
        lnLayout.addView(resetCodeEdittext);

        Retrofit retrofit = RetrofitFactory.getRetrofit();
        IWsServices iWsServices = retrofit.create(IWsServices.class);
        Call<MessageBodyHeader> callMsgbody = iWsServices.isEmailAlreadyForgotpasswrd(email);
        callMsgbody.enqueue(new Callback<MessageBodyHeader>() {
            @Override
            public void onResponse(Call<MessageBodyHeader> call, Response<MessageBodyHeader> response) {
                MessageBodyHeader msgHeader = response.body();
                BodyData bodyData = response.body().getBodyData();

                    if (response.isSuccessful()){
                        String status = msgHeader.getStatus();
                        int mailExist = bodyData.getEmailExists();
                        String resetCode = bodyData.getResetCode();
                        int userId = bodyData.getUser_id();

                            if (mailExist==0){
                                AlertDialog.Builder alerteNoEmail = new AlertDialog.Builder(ezeety_forgot_psswrd.this);
                                alerteNoEmail.setTitle("E-mail Incorrect");
                                alerteNoEmail.setMessage("L' e-mail que vous avez saisie ne correspond à aucun compte");
                                alerteNoEmail.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        return;
                                    }
                                });
                                alerteNoEmail.show();
                            }
                            if (mailExist==1){
                                AlertDialog.Builder alerteResetCode = new AlertDialog.Builder(ezeety_forgot_psswrd.this);
                                alerteResetCode.setTitle("Code de confirmation");
                                alerteResetCode.setMessage("Un code de confirmation a été envoyé à votre Email, " +
                                        "veuillez le saisir pour continuer. ");
                                alerteResetCode.setView(lnLayout);
                                alerteResetCode.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String resultResetCode = resetCodeEdittext.getText().toString();
                                        if(resultResetCode.equals(resetCode)){
                                            Intent intentForgotPasswrd = new Intent(ezeety_forgot_psswrd.this, ezeety_forgot_password_processing.class);
                                            intentForgotPasswrd.putExtra("userIdForgotPassd", userId);
                                            intentForgotPasswrd.putExtra("resetCode", resetCode);
                                            startActivity(intentForgotPasswrd);
                                            return;
                                        }else {
                                            AlertDialog.Builder alerteErrorCode = new AlertDialog.Builder(ezeety_forgot_psswrd.this);
                                            alerteErrorCode.setTitle("Erreur code");
                                            alerteErrorCode.setMessage("Le code que vous avez saisi est incorrect");
                                            alerteErrorCode.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    return;
                                                }
                                            });
                                            alerteErrorCode.show();
                                        }
                                    }
                                });
                                alerteResetCode.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        return;
                                    }
                                });
                                alerteResetCode.show();
                            }


                    } else {
                        Toast.makeText(ezeety_forgot_psswrd.this, "Error", Toast.LENGTH_SHORT).show();
                    }


            }
            @Override
            public void onFailure(Call<MessageBodyHeader> call, Throwable t) {
                Toast.makeText(ezeety_forgot_psswrd.this, "Server Error or down", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
