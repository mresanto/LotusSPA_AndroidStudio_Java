package com.example.lotus_spa.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.Packages;
import com.example.lotus_spa.Class.Reserve;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.Interface.ApiPackages;
import com.example.lotus_spa.Interface.ApiReserve;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public String opPayOp;
    private static final String TAG = "EndReserve";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private TextView DateReserve, NameReserve, PriceReserve;
    private Button btnReserve;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_reserve);


        DateReserve = findViewById(R.id.txtDateEndReserve);
        NameReserve = findViewById(R.id.txtNameEndReserve);
        PriceReserve = findViewById(R.id.txtPriceEndReserve);
        btnReserve = findViewById(R.id.btnEndReserve);

        spinner = findViewById(R.id.spiGreetingCardReserve);


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

        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String email_s = s.getString("email","Email");

        RequestCpf(email_s);

        SharedPreferences s1 = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String cpf = s1.getString("cpf","");

        Log.d(TAG, "CPF: " + cpf);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reserve reserve = new Reserve();

                String primarydate = DateReserve.getText().toString();
                String[] data = primarydate.split("/");
                String finaldate = data[2] + "-" + data[0] + "-" + data[1];

                SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
                String cpf = s.getString("cpf","");

                SharedPreferences s1 = getSharedPreferences("Reserve", MODE_PRIVATE);
                int packcode1 = s1.getInt("packcode",0);
                String payvalue = s1.getString("payvalue","0");

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                reserve.setResValidity(finaldate);
                reserve.setResAmount(1);
                reserve.setStatusReserve('U');
                reserve.setIsDeleted('N');
                reserve.setCustCPF(cpf);
                reserve.setPackCode(packcode1);
                reserve.setPayDate(date);
                reserve.setPayValue(payvalue.replace(",","."));
                reserve.setStatusPayment('U');
                reserve.setPayOption(opPayOp);



                Log.d(TAG, "Reserve Itens: " + reserve.getResValidity());
                Log.d(TAG, "Reserve Itens: " + reserve.getResAmount());
                Log.d(TAG, "Reserve Itens: " + reserve.getStatusReserve());
                Log.d(TAG, "Reserve Itens: " + reserve.getIsDeleted());
                Log.d(TAG, "Reserve Itens: " + reserve.getPackCode());
                Log.d(TAG, "Reserve Itens: " + reserve.getCustCPF());
                Log.d(TAG, "Reserve Itens: " + reserve.getPayDate());
                Log.d(TAG, "Reserve Itens: " + reserve.getPayValue());
                Log.d(TAG, "Reserve Itens: " + reserve.getStatusPayment());
                Log.d(TAG, "Reserve Itens: " + reserve.getPayOption());

                RequestReserve(reserve);
            }
        });

        ConfigSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opPayOp = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void ConfigSpinner(){
        ArrayList<String> categoryspinner= new ArrayList<>();
        categoryspinner.add("Débito");
        categoryspinner.add("Crédito");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void RequestReserve(Reserve reserve){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiReserve apiReserve = retrofit.create(ApiReserve.class);
        Call<Reserve> call2;

        call2 = apiReserve.createReserve(reserve);
        call2.enqueue(new Callback<Reserve>() {
            @Override
            public void onResponse(Call<Reserve> call, Response<Reserve> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EndReserveActivity.this, "Reserva não realizada", Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(EndReserveActivity.this, "Agendamento da Reserva Completa", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Reserve> call, Throwable t) {
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

                SharedPreferences login = getSharedPreferences("Reserve", MODE_PRIVATE);
                SharedPreferences.Editor editor = login.edit();
                editor.putInt("packcode", packcode);
                editor.putString("payvalue", String.valueOf(packages.get(0).getPackprice()));
                editor.commit();
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

    private void RequestCpf(String email) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCustomer apiCustomer = retrofit.create(ApiCustomer.class);

        Call<List<Customer>> call;
        call = apiCustomer.getByEmail(email);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                String cpf = response.body().get(0).getCustcpf();

                SharedPreferences login = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
                SharedPreferences.Editor editor = login.edit();
                editor.putString("cpf", cpf);
                editor.commit();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
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

    @Override
    public void finish() {

        ActionOrderItem actionOrderItem = new ActionOrderItem(EndReserveActivity.this);
        actionOrderItem.UpdateAllOrderItem();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            super.finish();
            // Swap without transition
        }
    }
}