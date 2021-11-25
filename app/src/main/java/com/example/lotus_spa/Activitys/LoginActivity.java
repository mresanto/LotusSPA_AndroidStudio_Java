package com.example.lotus_spa.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LoginActivity extends AppCompatActivity {
    private Button btnLog;
    private Button btnMakeLog;
    private EditText txtEmail;
    private EditText txtPassword;
    private Call<List<Customer>> call;

    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(VerificarLogin()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLog = (Button) findViewById(R.id.btnLog);
        txtEmail = (EditText) findViewById(R.id.edEmail);
        txtPassword = (EditText) findViewById(R.id.edPassword);
        btnMakeLog = (Button) findViewById(R.id.btnMakeLog);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCustomer apiCustomer = retrofit.create(ApiCustomer.class);


        btnLog.setOnClickListener(v -> {

            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            if(Validation(email, password)) {
                Request(email,password,apiCustomer);
            }
        });


        btnMakeLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if we're running on Android 5.0 or higher
                if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    // Apply activity transition
                } else {
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    // Swap without transition
                }

            }
        });
    }

    private void Request(String email, String password, ApiCustomer apiCustomer) {

        call = apiCustomer.getValidation("0", email, password);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                List<Customer> customers = response.body();

                String msg = customers.get(0).getMensagem();
                if (msg.equals("Login OK")) {

                    if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        // Apply activity transition
                        SalvarCustomer(email,password);
                        finish();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                        // Swap without transition
                    }
                } else if (msg.equals("Email OK")) {
                    txtPassword.setError("Senha não correspondente");
                } else if (msg.equals("Not Found Email and Password")) {
                    txtEmail.setError("Email não encontrado");
                    txtPassword.setError("Senha não encontrada");
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(LoginActivity.this);
                missingserver.setTitle("Server not found!");
                missingserver.setMessage("Servidores Offline, por favor tente novamente mais tarde");
                missingserver.create().show();
            }
        });
    }


    public void SalvarCustomer(String email, String password){
        SharedPreferences login = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        SharedPreferences.Editor edit = login.edit();
        edit.putBoolean("savelogin",true);
        edit.putString("email",email);
        edit.putString("password",password);
        edit.commit();

        //ActionCustomer salvar = new ActionCustomer(this);
        //if(salvar.AdicionarCustomer(customer))
        //Log.i("INFO Salvar", "Customer criado");
        //else
        //    Log.i("INFO Salvar", "Customer não criado");

    }

    public boolean VerificarLogin(){
        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        Boolean b = s.getBoolean("savelogin",false);
        return b;
    }

    public boolean Validation(String email, String password){
        if(email.equals(""))
            return false;
        else if (password.equals(""))
            return false;
        return true;
    }
}