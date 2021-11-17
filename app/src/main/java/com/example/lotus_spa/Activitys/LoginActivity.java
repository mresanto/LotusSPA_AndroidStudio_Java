package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lotus_spa.Class.Feed;
import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionCustomer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Button btnLog;
    private Button btnMakeLog;
    private EditText txtEmail;
    private EditText txtPassword;
    private Call<Feed> call;

    private static final String BASE_URL = "http://10.0.2.2:3000/";
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
            call = apiCustomer.getDataEmail(email, password);
            call.enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(Call<Feed> call, Response<Feed> response) {
                    Log.d(TAG, "onResponse: Server Response" + response.toString());
                    Log.d(TAG, "onResponse: received information" + response.body().toString());
                    if(response.body().getResponse().getQuantidade().equals("1")) {
                        ArrayList<Customer> customersList = response.body().getResponse().getCustomer();

                        String email_v = customersList.get(0).getCustemail();
                        String password_v = customersList.get(0).getCustpassword();

                        if (email_v.length() == email.length() && password_v.length() == password.length()) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                // Apply activity transition
                                SalvarCustomer(customersList.get(0));
                                finish();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                                startActivity(intent);
                                // Swap without transition
                            }
                        }
                    }
                    else{
                        txtEmail.setError("Email e Senha não encontrados");
                        txtPassword.setError("Email e Senha não encontrados");
                    }

                    //for( int i = 0; i < customersList.size(); i++){
                    //    Log.d("LoginActivity", "onResponse \n" +
                    //            "customercode : " + customersList.get(i).getCustcode() + "\n" +
                    //            "customername : " + customersList.get(i).getCustname());
                    //}
                }

                @Override
                public void onFailure(Call<Feed> call, Throwable t) {
                    Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                    AlertDialog.Builder missingserver = new AlertDialog.Builder(LoginActivity.this);
                    missingserver.setTitle("Server not found!");
                    missingserver.setMessage("Servidores Offline, por favor tente novamente mais tarde");
                    missingserver.create().show();
                }
            });
        });


        btnMakeLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    public void SalvarCustomer(Customer customer){
        ActionCustomer salvar = new ActionCustomer(this);
        if(salvar.AdicionarCustomer(customer))
        Log.i("INFO Salvar", "Customer criado");
        else
            Log.i("INFO Salvar", "Customer não criado");

    }

    public boolean VerificarLogin(){
        ActionCustomer verificar = new ActionCustomer(this);
        return verificar.VerificarLogin();
    }
}