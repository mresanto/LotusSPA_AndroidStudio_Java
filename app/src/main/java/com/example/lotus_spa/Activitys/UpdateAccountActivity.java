package com.example.lotus_spa.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.R;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateAccountActivity extends AppCompatActivity {


    private EditText UpPassword, UpName;
    private MaskEditText UpCep, UpNA;
    private Switch switchUpSex;
    private TextView uptvDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String finaldate;
    private Button btnUpdate;
    private static final String TAG = "UpdateAccount";
    private static final String BASE_URL = "https://apilotusspa.herokuapp.com/api/v1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        UpPassword = findViewById(R.id.edUpPassword);
        UpName = findViewById(R.id.edUpNome);
        UpCep = findViewById(R.id.edUpCep);
        switchUpSex = findViewById(R.id.switchupsex);
        uptvDialog = findViewById(R.id.tvDate);
        btnUpdate = findViewById(R.id.btnUp);
        UpNA = findViewById(R.id.edUpNumberAddress);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCustomer apiCustomer = retrofit.create(ApiCustomer.class);

        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String email = s.getString("email","Email");

        Request(email,apiCustomer);

        uptvDialog.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(UpdateAccountActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date ="Data Seleciona:" + dayOfMonth + "/" + month + "/" + year;
                uptvDialog.setText(date);
                finaldate = year+"-"+month+"-"+dayOfMonth;
            }
        };

        switchUpSex.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked)
                switchUpSex.setText("Feminino");
            else
                switchUpSex.setText("Masculino");
        });

        btnUpdate.setOnClickListener(v -> {

            Customer customer = new Customer();

            customer.setCustname(UpName.getText().toString());

            uptvDialog.setError(null);

            if(switchUpSex.getText() == "Feminino"){
                customer.setCustsex("F");
            }
            else{
                customer.setCustsex("M");
            }

            customer.setCustbirthdate(finaldate);
            customer.setCustpassword(UpPassword.getText().toString());
            customer.setCustnumberaddress(UpNA.getUnMasked());
            customer.setCep(UpCep.getUnMasked());
            customer.setCustemail(email);

            if(Validation(customer)) {
                UpCostumer(customer, apiCustomer);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void finish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            super.finish();
            // Swap without transition
        }
    }

    public void UpCostumer(Customer customer, ApiCustomer apiCustomer){



        Call<Customer> call;

        call = apiCustomer.updateCostumer(customer);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
//
                if(!response.isSuccessful()){
                    Toast.makeText(UpdateAccountActivity.this, "Update não realizado", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(UpdateAccountActivity.this, "Update realizado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
            //
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(UpdateAccountActivity.this);
                missingserver.setTitle("Atualização não realizada com sucesso!");
                missingserver.setMessage("Servidores Offline, por favor tente novamente mais tarde");
                missingserver.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                missingserver.create().show();
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
            }
        });
    }
    private void Request(String email, ApiCustomer apiCustomer) {

        Call<List<Customer>> call;
        call = apiCustomer.getByEmail(email);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                Customer customer = response.body().get(0);

                UpPassword.setText(customer.getCustpassword());
                UpName.setText(customer.getCustname());
                UpCep.setText(customer.getCep());
                if(customer.getCustsex().equals("Feminino"))
                    switchUpSex.isChecked();
                uptvDialog.setText("Data Selecionada: "+customer.getCustbirthdate());
                String[] data = customer.getCustbirthdate().split("/");
                finaldate = data[2] + "-" + data[1] + "-" + data[0];
                UpNA.setText(customer.getCustnumberaddress());


                //if (msg.equals("Login OK")) {
//
                //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //        startActivity(intent);
                //        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //        // Apply activity transition
                //        SalvarCustomer(email,password);
                //        finish();
                //    } else {
                //        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                //        startActivity(intent);
                //        // Swap without transition
                //    }
                //} else if (msg.equals("Email OK")) {
                //    txtPassword.setError("Senha não correspondente");
                //} else if (msg.equals("Not Found Email and Password")) {
                //    txtEmail.setError("Email não encontrado");
                //    txtPassword.setError("Senha não encontrada");
                //}
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
            }
        });
    }

    public boolean Validation(Customer customer){


        if(customer.getCustpassword().length() == 0){
            UpPassword.setError("Senha não preenchida");
            return false;
        }
        else if(customer.getCustname().length() == 0){
            UpName.setError("Nome não preenchido");
            return false;
        }
        else if(customer.getCep().length() == 0){
            UpCep.setError("Cep não preenchido");
            return false;
        }
        else if(customer.getCep().length() != 8){
            UpCep.setError("Cep não preenchido corretamente");
            return false;
        }
        else if(customer.getCustnumberaddress().length() == 0)
        {
            UpNA.setError("Numero Endereço não preenchido");
            return false;
        }
        else if(customer.getCustnumberaddress().length() != 2)
        {
            UpNA.setError("Numero Endereço não preenchido corretamente");
            return false;
        }
        else if(customer.getCustbirthdate().equals("Select Date"))
        {
            uptvDialog.setError("data não preenchida");
            return false;
        }

        return true;
    }
}