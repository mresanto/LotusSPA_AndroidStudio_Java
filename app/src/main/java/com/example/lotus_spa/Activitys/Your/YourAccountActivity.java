package com.example.lotus_spa.Activitys.Your;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotus_spa.Activitys.Details.DetailsProductActivity;
import com.example.lotus_spa.Activitys.Mains.LoginActivity;
import com.example.lotus_spa.Activitys.Mains.PurchaseActivity;
import com.example.lotus_spa.Activitys.UpdateAccountActivity;
import com.example.lotus_spa.Adapters.AdapterPurshase;
import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.Order;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.Interface.ApiProduct;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YourAccountActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://apilotusspa.herokuapp.com/api/v1/";
    private static final String TAG = "YourAccount";

    TextView email;
    Button btnLogout, btnUpCad;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account);

        email = findViewById(R.id.txtEmail);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpCad= findViewById(R.id.btnUpCad);

        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String email_s = s.getString("email","Email");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCustomer apiCustomer = retrofit.create(ApiCustomer.class);
        Request(email_s, apiCustomer);

        //email.setText("E-mail: " + email_s);

        btnLogout.setOnClickListener(v -> {
            AlertDialog.Builder endlogin = new AlertDialog.Builder(YourAccountActivity.this);
            endlogin.setTitle("Finalizar Sessão");
            endlogin.setMessage("Deseja finalizar a sessão em andamento");
            endlogin.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences login = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
                    SharedPreferences.Editor edit = login.edit();
                    edit.clear();
                    edit.commit();

                    Intent intent = new Intent(YourAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);                    }
            });
            endlogin.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            endlogin.create().show();
        });
        btnUpCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourAccountActivity.this, UpdateAccountActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
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

    private void Request(String email_s, ApiCustomer apiCustomer) {
        Call<List<Customer>> call;
        call = apiCustomer.getByEmail(email_s);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(@NotNull Call<List<Customer>> call, @NotNull Response<List<Customer>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                List<Customer> lstCustomer = new ArrayList<>(response.body());
                email.setText("E-mail: " + lstCustomer.get(0).getCustemail() + "\n"
                + "Nome: " + lstCustomer.get(0).getCustname() + "\n"
                + "Telefone: " + lstCustomer.get(0).getCusttelephone() + "\n"
                + "CPF: " + lstCustomer.get(0).getCustcpf() + "\n"
                + "Cep: " + lstCustomer.get(0).getCep() + "\n"
                + "Data de Nascimento: " + lstCustomer.get(0).getCustbirthdate() + "\n");


            }

            @Override
            public void onFailure(@NotNull Call<List<Customer>> call, @NotNull Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco2: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(YourAccountActivity.this);
                missingserver.setTitle("Server not found!");
                missingserver.setMessage("Servidores Offline, por favor tente novamente mais tarde");
                missingserver.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                missingserver.create().show();
            }
        });


    }

}