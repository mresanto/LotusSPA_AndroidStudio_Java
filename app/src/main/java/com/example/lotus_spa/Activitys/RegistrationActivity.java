package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.Feed;
import com.example.lotus_spa.Class.Resultado;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.R;
import com.santalu.maskara.widget.MaskEditText;


import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private String finaldate;

    private Button btnCad;
    private Button btnDate;
    private EditText CadEmail;
    private EditText CadPassword;
    private EditText CadName;
    private MaskEditText CadCpf;
    private Switch switchsex;
    private MaskEditText CadTelephone;
    private TextView tvDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final String TAG = "RegistrationActivity.this";
    private static final String BASE_URL = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCustomer apiCustomer = retrofit.create(ApiCustomer.class);


        CadEmail = (EditText) findViewById(R.id.edCadEmail);
        CadPassword = (EditText) findViewById(R.id.edCadPassword);
        CadName = (EditText) findViewById(R.id.edCadNome);
        CadCpf = (MaskEditText) findViewById(R.id.edCadCpf);
        switchsex = (Switch) findViewById(R.id.switchsex);
        CadTelephone = (MaskEditText) findViewById(R.id.edCadTelephone);

        tvDialog = (TextView) findViewById(R.id.tvDate);

        tvDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date ="Data Seleciona:" + dayOfMonth + "/" + month + "/" + year;
                tvDialog.setText(date);
                finaldate = year+"-"+month+"-"+dayOfMonth;
            }
        };

        switchsex.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked)
                switchsex.setText("Feminino");
            else
                switchsex.setText("Masculino");
        });

        btnCad = (Button) findViewById(R.id.btnCad);
        btnCad.setOnClickListener(v -> {
            Customer customer = new Customer();

            customer.setCustname(CadName.getText().toString());

            if(switchsex.getText() == "Feminino"){
                customer.setCustsex("F");
            }
            else{
                customer.setCustsex("M");
            }

            customer.setCustcpf(CadCpf.getUnMasked());
            customer.setCustbirthdate(finaldate);
            customer.setCusttelephone(CadTelephone.getUnMasked());
            customer.setCustemail(CadEmail.getText().toString());
            customer.setCustpassword(CadPassword.getText().toString());

            if(Validation(customer) == true) {

                Call<Feed> call2;

                String email, cpf;
                email = customer.getCustemail();
                cpf = customer.getCustcpf();
                //call2 = apiCustomer.getValidation(email, cpf);
                call2 = apiCustomer.getValidation(email, cpf);
                call2.enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        List<Resultado> s;

                        s = response.body().getResultado();
                        String a = s.get(0).getMensagem();
                        if(a.length() != 0) {
                            Toast.makeText(RegistrationActivity.this, s.get(0).getMensagem(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        CadCostumer(customer);
                    }

                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        Toast.makeText(RegistrationActivity.this, "Cadastro não realizado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
    public void CadCostumer(Customer customer){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCustomer apiCustomer = retrofit.create(ApiCustomer.class);

        Call<Customer> call;

        call = apiCustomer.createCostumer(customer);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
//
                if(!response.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Cadastro não realizado", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegistrationActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
            //
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
//
            }
        });
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

    public void Toat(String resposta){
        Toast.makeText(this,resposta,Toast.LENGTH_SHORT).show();
    }

    public boolean Validation(Customer customer){


        if(customer.getCustemail().length() == 0)
        {
            Toat("Email não preenchido");
            return false;
        }
        else if (isValidEmailId(customer.getCustemail().trim()) != true)
        {
            Toat("Email não valido");
            return false;
        }
        else if(customer.getCustpassword().length() == 0){
            Toat("Senha não preenchida");
            return false;
        }
        else if(customer.getCustcpf().length() != 11){
            Toat("Cpf não preenchido corretamente");
            return false;
        }
        else if(customer.getCustname().length() == 0){
            Toat("Nome não preenchido");
            return false;
        }
        return true;
    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}