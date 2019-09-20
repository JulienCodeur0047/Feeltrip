package com.packag.ezeety;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.packag.ezeety.Pojos.Ville;
import com.packag.ezeety.Pojos.Villes;
import com.packag.ezeety.Remote.RetrofitFactory;
import com.packag.ezeety.SettingView.CustomeListAdapter;
import com.packag.ezeety.interfaces.IWsServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ezeety_registration_second extends AppCompatActivity {

    Button buttonNext2;
    EditText editTextNameuserReg, editTextUserNameReg, editTextdateNaissanceReg, editTextVilleReg001;
    AutoCompleteTextView editTextVilleReg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ezeety_registration_second_step);
        buttonNext2 = findViewById(R.id.buttonNextRegistrationSecond);
        editTextNameuserReg = findViewById(R.id.edittextNameFirstNameReg);
        editTextUserNameReg = findViewById(R.id.editTextUserNamesecondeReg);
        editTextdateNaissanceReg = findViewById(R.id.edittextDatenaissanceReg);
        editTextVilleReg = findViewById(R.id.editTextVilleSecondReg);



        editTextVilleReg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ezeety_registration_second.this,
                        "Clicked item from auto completion list "
                                + adapterView.getItemAtPosition(i)
                        , Toast.LENGTH_SHORT).show();
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
                new DatePickerDialog(ezeety_registration_second.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonNext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String NameFirstNameUser = editTextNameuserReg.getText().toString(),
                        UserName = editTextUserNameReg.getText().toString(),
                        Ville = editTextVilleReg.getText().toString();
                Date DateNaissance = null;
                try {
                    DateNaissance = dateFormat.parse(editTextdateNaissanceReg.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(Checkform(NameFirstNameUser,UserName, Ville, DateNaissance)){
                    Intent intentWelcom = new Intent(ezeety_registration_second.this, ezeety_welcome.class);
                    intentWelcom.putExtra("UserNameFirstNameReg",NameFirstNameUser);
                    intentWelcom.putExtra("UserNameReg", UserName);
                    intentWelcom.putExtra("VilleReg", Ville);
                    intentWelcom.putExtra("DateNaissance", DateNaissance);
                    startActivity(intentWelcom);
                } return;

            }
        });
        editTextVilleReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.equals("")){
                    getVille(charSequence.toString());
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean Checkform(String nameFirstNameUser, String userName, String ville, Date dateNaissance) {
        if(nameFirstNameUser == null || nameFirstNameUser.trim().length() == 0){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser votre Nom et prenom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userName == null || userName.trim().length() == 0){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser le nom d'utilisateur", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ville == null || ville.trim().length() == 0){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser votre Nom Ville", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateNaissance == null){
            Toast.makeText(ezeety_registration_second.this, "veuillez preciser votre date de naissance ou date invalide", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

                        CustomeListAdapter adapterListVille = new CustomeListAdapter(ezeety_registration_second.this,
                                R.layout.layout_ezeety_simple_dropdown_item, Arrays.asList(stringList.toArray(new String[0])));
                        editTextVilleReg.setAdapter(adapterListVille);
                    }
                    else {
                        Toast.makeText(ezeety_registration_second.this, "Erreur de chargement de données ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Villes> call, Throwable t) {
                    Toast.makeText(ezeety_registration_second.this, "Erreur de Serveur.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(ezeety_registration_second.this,"No internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean CheckConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            Toast.makeText(ezeety_registration_second.this,"No internet connection", Toast.LENGTH_SHORT).show();
            return false;

    }

}
