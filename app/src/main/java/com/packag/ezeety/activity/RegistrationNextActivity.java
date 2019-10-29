package com.packag.ezeety.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.packag.ezeety.R;
import com.packag.ezeety.model.BodyData;
import com.packag.ezeety.model.GooglePlaces;
import com.packag.ezeety.model.MessageBodyHeader;
import com.packag.ezeety.model.Place;
import com.packag.ezeety.model.Ville;
import com.packag.ezeety.model.Villes;
import com.packag.ezeety.Services.RetrofitFactory;
import com.packag.ezeety.adpter.CustomeListAdapter;
import com.packag.ezeety.Services.IWsServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationNextActivity extends AppCompatActivity {

    Button buttonNext2;
    EditText editTextNameuserReg, editTextUserNameReg, editTextdateNaissanceReg, editTextVilleReg001;
    AutoCompleteTextView editTextVilleReg;
    Drawable drawableErrorEditText;
    public static String RegPays, RegGooglePlaceId, RegDateNaissance, RegVille, RegUsername, RegNomPrenom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_registration_second_step);
        buttonNext2 = findViewById(R.id.buttonNextRegistrationSecond);
        editTextNameuserReg = findViewById(R.id.edittextNameFirstNameReg);
        editTextUserNameReg = findViewById(R.id.editTextUserNamesecondeReg);
        editTextdateNaissanceReg = findViewById(R.id.edittextDatenaissanceReg);
        editTextVilleReg = findViewById(R.id.editTextVilleSecondReg);
        drawableErrorEditText = getResources().getDrawable(R.drawable.ezeety_edittext_style_error);
        editTextVilleReg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Toast.makeText(RegistrationNextActivity.this,
                        "Clicked item from auto completion list "
                                + adapterView.getItemAtPosition(i)
                        , Toast.LENGTH_SHORT).show();*/
            }
        });
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRENCH);

                editTextdateNaissanceReg.setText(sdf.format(myCalendar.getTime()));
            }

        };
        editTextdateNaissanceReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegistrationNextActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        buttonNext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 RegNomPrenom = editTextNameuserReg.getText().toString();
                        RegUsername = editTextUserNameReg.getText().toString();
                        RegVille = editTextVilleReg.getText().toString();
                RegDateNaissance = editTextdateNaissanceReg.getText().toString();
                checkUsername(RegUsername);
                if(Checkform(RegNomPrenom,RegUsername, RegVille, RegDateNaissance)){
                    Intent intentWelcom = new Intent(RegistrationNextActivity.this, WelcomeActivity.class);
                    startActivity(intentWelcom);
                } return;

            }
        });
        editTextVilleReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                getPlacesfromGoogle(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //if (charSequence.length() >= 4){
                    getPlacesfromGoogle(charSequence.toString());
               // }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean Checkform(String nameFirstNameUser, String userName, String ville, String dateNaissance) {
        if(nameFirstNameUser == null || nameFirstNameUser.trim().length() == 0){
            Toast.makeText(RegistrationNextActivity.this, "veuillez preciser votre Nom et prenom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userName == null || userName.trim().length() == 0){
            Toast.makeText(RegistrationNextActivity.this, "veuillez preciser le nom d'utilisateur", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ville == null || ville.trim().length() == 0){
            Toast.makeText(RegistrationNextActivity.this, "veuillez preciser votre Nom Ville", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateNaissance == null){
            Toast.makeText(RegistrationNextActivity.this, "veuillez preciser votre date de naissance ou date invalide", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void getPlacesfromGoogle(String City){
        if(CheckConnection()){
            Retrofit retrofit = RetrofitFactory.getRetrofit();
            IWsServices iWsServices = retrofit.create(IWsServices.class);
            Call<GooglePlaces> callPlaces = iWsServices.getPlacesAutocompletion(City);
            callPlaces.enqueue(new Callback<GooglePlaces>() {
                @Override
                public void onResponse(Call<GooglePlaces> call, Response<GooglePlaces> response) {
                    if(response.isSuccessful()) {
                        List<String> stringList = new ArrayList<String>();
                        for (Place v : response.body().getListPlace()) {
                            stringList.add(v.getDescription());
                            RegVille = v.getDescription();
                            RegPays = v.getStructuredF().getPays();
                            RegGooglePlaceId = v.getPlace_id();

                        }
                        CustomeListAdapter adapterListVille = new CustomeListAdapter(RegistrationNextActivity.this,
                                R.layout.layout_ezeety_simple_dropdown_item, Arrays.asList(stringList.toArray(new String[0])));
                        editTextVilleReg.setAdapter(adapterListVille);
                    }
                    else {
                        Toast.makeText(RegistrationNextActivity.this, "Erreur de chargement de données ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GooglePlaces> call, Throwable t) {
                    Toast.makeText(RegistrationNextActivity.this, "Erreur de Serveur.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getVille(String ville){
        if(CheckConnection()){
            Retrofit retrofit = RetrofitFactory.getRetrofit();
            IWsServices iWsServices = retrofit.create(IWsServices.class);
            Call<Villes> callVilles = iWsServices.getVillesAutoCompletion(ville);
            callVilles.enqueue(new Callback<Villes>() {
                @Override
                public void onResponse(Call<Villes> call, Response<Villes> response) {
                    if(response.isSuccessful()){
                        List<String> stringList = new ArrayList<String>();
                        for(Ville v : response.body().getListVille()){
                            stringList.add(v.getVille());
                        }

                        CustomeListAdapter adapterListVille = new CustomeListAdapter(RegistrationNextActivity.this,
                                R.layout.layout_ezeety_simple_dropdown_item, Arrays.asList(stringList.toArray(new String[0])));
                        editTextVilleReg.setAdapter(adapterListVille);
                    }
                    else {
                        Toast.makeText(RegistrationNextActivity.this, "Erreur de chargement de données ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Villes> call, Throwable t) {
                    Toast.makeText(RegistrationNextActivity.this, "Erreur de Serveur.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(RegistrationNextActivity.this,"No internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean CheckConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            Toast.makeText(RegistrationNextActivity.this,"No internet connection", Toast.LENGTH_SHORT).show();
            return false;
    }

    private void checkUsername(String usename){
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        IWsServices iWsServices = retrofit.create(IWsServices.class);
        Call<MessageBodyHeader> messageBodyHeaderCall = iWsServices.checkUserName(usename);
        messageBodyHeaderCall.enqueue(new Callback<MessageBodyHeader>() {
            @Override
            public void onResponse(Call<MessageBodyHeader> call, Response<MessageBodyHeader> response) {
                if (response.isSuccessful()){
                    MessageBodyHeader messageBodyHeader = response.body();
                    BodyData bodyData = messageBodyHeader.getBodyData();
                    int usernameExist = bodyData.getUsernameExists();
                    if (usernameExist==1){
                        AlertDialog.Builder alertDialogusernamExist = new AlertDialog.Builder(RegistrationNextActivity.this);
                        alertDialogusernamExist.setTitle("Erreur Nom d'utilisateur");
                        alertDialogusernamExist.setMessage("Le nom d'utilisateur que vous allez utiliser est déjà appartient a un utilisateur.");
                        alertDialogusernamExist.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        alertDialogusernamExist.show();
                        editTextUserNameReg.setBackground(drawableErrorEditText);

                    }
                }

            }

            @Override
            public void onFailure(Call<MessageBodyHeader> call, Throwable t) {
                Toast.makeText(RegistrationNextActivity.this,"Server Error or down", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
