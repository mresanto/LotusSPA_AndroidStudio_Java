package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotus_spa.Adapters.AdapterEndOrder;
import com.example.lotus_spa.Adapters.AdapterOrderItem;
import com.example.lotus_spa.Class.Cep;
import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Helper.MyButtonClickListener;
import com.example.lotus_spa.Helper.MySwipeHelper;
import com.example.lotus_spa.Interface.ApiCep;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndOrderActivity extends AppCompatActivity {

    private static final String TAG = "EndOrder";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private static final String BASE_URL_CEP = "https://viacep.com.br/ws/";
    public String cep,name, numberAddress, telephone;
    TextView NameTelephone, Logradouro, CidadeUf, TotalPrice;
    AdapterEndOrder myAdapter;
    List<OrderItem> lstChecked;
    RecyclerView myrv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_order);


        NameTelephone = findViewById(R.id.txtNameTeleEndOrder);
        Logradouro = findViewById(R.id.txtLogEndOrder);
        CidadeUf = findViewById(R.id.txtCidadeUfEndOrder);
        TotalPrice = findViewById(R.id.txtTotalPrice);
        
        myrv = findViewById(R.id.rvEndOrder);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCustomer apiCustomer = retrofit.create(ApiCustomer.class);

        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String email = s.getString("email","Email");

        Request(email,apiCustomer);


        AddRecycle();
    }

    private void Request(String email, ApiCustomer apiCustomer) {

        Call<List<Customer>> call;
        call = apiCustomer.getByEmail(email);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                cep = response.body().get(0).getCep();
                name = response.body().get(0).getCustname();
                telephone = response.body().get(0).getCusttelephone();
                numberAddress = response.body().get(0).getCustnumberaddress();

                RequestCep(cep);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
            }
        });
    }

    private void RequestCep(String cep) {

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL_CEP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCep apiCep = retrofit2.create(ApiCep.class);

        Call<Cep> call;
        call = apiCep.getEnd(cep);
        Log.d(TAG, "Cep: " + cep);
        call.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                Cep cepCust = new Cep();

                cepCust.setCep(response.body().getCep());
                cepCust.setLocalidade(response.body().getLocalidade());
                cepCust.setBairro(response.body().getBairro());
                cepCust.setUf(response.body().getUf());
                cepCust.setLogradouro(response.body().getLogradouro());

                NameTelephone.setText(name + " | " + telephone);
                Logradouro.setText(cepCust.getLogradouro());
                CidadeUf.setText(cepCust.getLocalidade() + " | " + cepCust.getUf());
            }
            @Override
            public void onFailure(Call<Cep> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
            }
        });
    }

    private void AddRecycle() {

        ActionOrderItem actionOrderItem = new ActionOrderItem(this);
        lstChecked = new ArrayList<>(actionOrderItem.ListarOrderItemOrd());
        myAdapter = new AdapterEndOrder(getApplicationContext(),lstChecked);
        myrv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        myrv.setAdapter(myAdapter);

        Log.d(TAG, "Teste Checked" + actionOrderItem.ListarOrderItem());
        
        double total_price = 0;
        for (int i = 0; i< lstChecked.size(); i++) {
            total_price = total_price + Double.parseDouble(lstChecked.get(i).getItemUnitaryPrice().replace(",","."));
        }

        TotalPrice.setText("Preço Total: " + String.valueOf(total_price).replace(".",","));
        


    }


    @Override
    public void finish() {

        ActionOrderItem actionOrderItem = new ActionOrderItem(EndOrderActivity.this);
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