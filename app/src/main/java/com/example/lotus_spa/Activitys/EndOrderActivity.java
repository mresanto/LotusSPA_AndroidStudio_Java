package com.example.lotus_spa.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.example.lotus_spa.Activitys.Mains.RegistrationActivity;
import com.example.lotus_spa.Adapters.AdapterEndOrder;
import com.example.lotus_spa.Adapters.AdapterOrderItem;
import com.example.lotus_spa.Class.Cep;
import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.Order;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Helper.MyButtonClickListener;
import com.example.lotus_spa.Helper.MySwipeHelper;
import com.example.lotus_spa.Interface.ApiCep;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.Interface.ApiOrder;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;

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

public class EndOrderActivity extends AppCompatActivity {

    private static final String TAG = "EndOrder";
    private static final String BASE_URL = "https://apilotusspa.herokuapp.com/api/v1/";
    private static final String BASE_URL_CEP = "https://viacep.com.br/ws/";
    public String cep,name, numberAddress, telephone, opPayOp;
    TextView NameTelephone, Logradouro, CidadeUf, TotalPrice;
    double total_price = 0;
    AdapterEndOrder myAdapter;
    List<OrderItem> lstChecked;
    RecyclerView myrv;
    Spinner spinner;
    Button btnOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_order);


        NameTelephone = findViewById(R.id.txtNameTeleEndOrder);
        Logradouro = findViewById(R.id.txtLogEndOrder);
        CidadeUf = findViewById(R.id.txtCidadeUfEndOrder);
        TotalPrice = findViewById(R.id.txtTotalPrice);
        spinner = findViewById(R.id.spiGreetingCard);
        btnOrder = findViewById(R.id.btnOrder);

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

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();

                Calendar c = Calendar.getInstance();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
                String cpf = s.getString("cpf","");

                order.setOrdDate(date);
                Log.e(TAG, "Preço Total: " + total_price);
                order.setTotalPrice(total_price);
                order.setStatusorder("U");
                order.setCustCPF(cpf);
                order.setPayDate(date);
                order.setIsDeleted("N");
                order.setStatusPayment("U");
                order.setPayOption(opPayOp);

                RequestOrder(order);
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

                cep = response.body().get(0).getCep();
                name = response.body().get(0).getCustname();
                telephone = response.body().get(0).getCusttelephone();
                numberAddress = response.body().get(0).getCustnumberaddress();

                String cpf = response.body().get(0).getCustcpf();

                SharedPreferences login = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
                SharedPreferences.Editor editor = login.edit();
                editor.putString("cpf", cpf);
                editor.commit();

                RequestCep(cep);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(EndOrderActivity.this);
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

    private void RequestOrder(Order order) {

        Log.d(TAG, "Order Completed");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiOrder apiOrder = retrofit.create(ApiOrder.class);

        Call<Order> call;
        call = apiOrder.createOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EndOrderActivity.this, "Cadastro não realizado", Toast.LENGTH_SHORT).show();

                }
                for(int i = 0; i < lstChecked.size(); i++) {
                    Log.e(TAG, "Reserve message:" + response.message());
                    Log.d(TAG, "OrderItem Pedido, size: " + lstChecked.size());
                    RequestOrderItens(lstChecked.get(i));
                }
                Toast.makeText(EndOrderActivity.this, "Agendamento dos Produtos Completos", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(EndOrderActivity.this);
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

    private void RequestOrderItens(OrderItem orderItem) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiOrder apiOrderItem = retrofit.create(ApiOrder.class);

        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String cpf = s.getString("cpf","");
        orderItem.setCustCPF(cpf);
        orderItem.setItemUnitaryPrice(orderItem.getItemUnitaryPrice().replace(",","."));
        orderItem.setItemAmount(1);
        orderItem.setTotalPrice(String.valueOf(Double.parseDouble(orderItem.getItemUnitaryPrice().replace(",",".")) * orderItem.getItemAmount()));

        Log.d(TAG, "OrderItem Pedido code: " + orderItem.getProdBarCode());
        Log.d(TAG, "OrderItem Pedido uni: " + orderItem.getItemUnitaryPrice());
        Log.d(TAG, "OrderItem Pedido amount: " + orderItem.getItemAmount());
        Log.d(TAG, "OrderItem Pedido total: " + orderItem.getTotalPrice());
        Log.d(TAG, "OrderItem Pedido cpf: " + orderItem.getCustCPF());


        Call<OrderItem> call;
        call = apiOrderItem.createOrderItem(orderItem);
        call.enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EndOrderActivity.this, "Item não realizado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(EndOrderActivity.this);
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

    private void ConfigSpinner(){
        ArrayList<String> categoryspinner= new ArrayList<>();
        categoryspinner.add("Débito");
        categoryspinner.add("Crédito");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
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
                try {
                    Log.d(TAG, "onResponse: Server Response" + response.toString());
                    Log.d(TAG, "onResponse: received information" + response.body().toString());
                }catch (Exception e){
                    Log.e(TAG, "Infelizmente algo deu errado na chamada do banco CEP: ");
                    AlertDialog.Builder missingserver = new AlertDialog.Builder(EndOrderActivity.this);
                    missingserver.setTitle("Server not found!");
                    missingserver.setMessage("Servidor ViaCEP Offline, por favor tente novamente mais tarde");
                    missingserver.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    missingserver.create().show();
                }

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
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco CEP: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(EndOrderActivity.this);
                missingserver.setTitle("Server not found!");
                missingserver.setMessage("Servidor ViaCEP Offline, por favor tente novamente mais tarde");
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

    private void AddRecycle() {

        ActionOrderItem actionOrderItem = new ActionOrderItem(this);
        lstChecked = new ArrayList<>(actionOrderItem.ListarOrderItemOrd());
        myAdapter = new AdapterEndOrder(getApplicationContext(),lstChecked);
        myrv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        myrv.setAdapter(myAdapter);

        Log.d(TAG, "Teste Checked" + actionOrderItem.ListarOrderItem());
        
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