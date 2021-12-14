package com.example.lotus_spa.Activitys.Mains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotus_spa.Activitys.Details.DetailsProductActivity;
import com.example.lotus_spa.Adapters.AdapterEndOrder;
import com.example.lotus_spa.Adapters.AdapterOrder;
import com.example.lotus_spa.Adapters.AdapterOrderItem;
import com.example.lotus_spa.Adapters.AdapterPurshase;
import com.example.lotus_spa.Adapters.AdapterReserve;
import com.example.lotus_spa.Class.Order;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Class.Reserve;
import com.example.lotus_spa.Interface.ApiOrder;
import com.example.lotus_spa.Interface.ApiProduct;
import com.example.lotus_spa.Interface.ApiReserve;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;
import com.google.android.gms.common.api.Api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReserveActivity extends AppCompatActivity {

    private static final String TAG = "ReserveActivity";
    private static final String BASE_URL = "https://apilotusspa.herokuapp.com/api/v1/";
    private Spinner spinner;
    RecyclerView myrv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        myrv = findViewById(R.id.rvReserveType);

        // first clear the recycler view so items are not populated twice

        //spiner

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiOrder apiOrder = retrofit.create(ApiOrder.class);
        ApiReserve apiReserve = retrofit.create(ApiReserve.class);

        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String email = s.getString("email","Email");


        ConfigSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = spinner.getItemAtPosition(position).toString();
                if(type.equals("Pedido"))
                    RequestOrder(email, apiOrder);
                //Request(type, apiOrder);
                if(type.equals("Reserva"))
                    RequestReserve(email, apiReserve);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void ConfigSpinner(){
        ArrayList<String> categoryspinner= new ArrayList<>();
        categoryspinner.add("Reserva");
        categoryspinner.add("Pedido");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = findViewById(R.id.spCategoryReserve);
        spinner.setAdapter(adapter);
    }

    private void RequestReserve(String email, ApiReserve apiReserve) {
        Call<List<Reserve>> call;
        call = apiReserve.getByCode(email);
        call.enqueue(new Callback<List<Reserve>>() {
            @Override
            public void onResponse(@NotNull Call<List<Reserve>> call, @NotNull Response<List<Reserve>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                response.body().toString();
                List<Reserve> lstReserve = new ArrayList<>(response.body());
                AdapterReserve myAdapter;

                //Spinner
                myAdapter = new AdapterReserve(ReserveActivity.this, lstReserve);
                myrv.setLayoutManager(new LinearLayoutManager(ReserveActivity.this));
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(@NotNull Call<List<Reserve>> call, @NotNull Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco2: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(ReserveActivity.this);
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

    private void RequestOrder(String email, ApiOrder apiOrder) {
        Call<List<Order>> call;
        call = apiOrder.getByCode(email);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NotNull Call<List<Order>> call, @NotNull Response<List<Order>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());



                response.body().toString();
                List<Order> lstOrder = new ArrayList<>(response.body());
                AdapterOrder myAdapter;

                //Spinner
                myAdapter = new AdapterOrder(ReserveActivity.this, lstOrder);
                myrv.setLayoutManager(new LinearLayoutManager(ReserveActivity.this));
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(@NotNull Call<List<Order>> call, @NotNull Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco2: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(ReserveActivity.this);
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

}