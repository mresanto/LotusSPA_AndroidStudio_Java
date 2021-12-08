package com.example.lotus_spa.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lotus_spa.Class.Packages;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.Interface.ApiPackages;
import com.example.lotus_spa.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndReserveActivity extends AppCompatActivity {

    private Call<List<Packages>> call;
    private static final String TAG = "EndReserve";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private TextView DateReserve, NameReserve, PriceReserve;
    private Button btnReserve;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_reserve);


        DateReserve = findViewById(R.id.txtDateEndReserve);
        NameReserve = findViewById(R.id.txtNameEndReserve);
        PriceReserve = findViewById(R.id.txtPriceEndReserve);
        btnReserve = findViewById(R.id.btnEndReserve);

        int packcode = 0;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            packcode = extras.getInt("Package");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiPackages apiPackages = retrofit.create(ApiPackages.class);

        if(packcode != 0)
            Request(packcode, apiPackages);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
    private void Request(int packcode, ApiPackages apiPackages) {
        call = apiPackages.getDetailsPackages(packcode);
        call.enqueue(new Callback<List<Packages>>() {
            @Override
            public void onResponse(Call<List<Packages>> call, Response<List<Packages>> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                Log.d(TAG, "onResponse: received information: " + response.body().toString());


                List<Packages> packages = response.body();
                Log.d(TAG, "Teste" + packages.get(0).getPackname());

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                try {
                    c.setTime(sdf.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, +30);
                Date newDate = c.getTime();

                ///Calendar calendar = Calendar.getInstance();
                String currentDate = sdf.format(newDate);

                //String[] data = currentDate.split("/");
                //String finaldate = data[0] + "/" + data[1] + "/" + data[2];

                DateReserve.setText(currentDate);
                NameReserve.setText("Nome do Pacote: " + packages.get(0).getPackname());
                PriceReserve.setText("Preço do Pacote: R$" +packages.get(0).getPackprice());
            }
            @Override
            public void onFailure(Call<List<Packages>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(EndReserveActivity.this);
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
}