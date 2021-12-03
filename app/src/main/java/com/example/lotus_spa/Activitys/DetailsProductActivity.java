package com.example.lotus_spa.Activitys;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Interface.ApiProduct;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsProductActivity extends AppCompatActivity {

    private Call<List<Product>> call;
    private static final String TAG = "DetailsProduct";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    public TextView txtname,txtprice,txtcat;
    public Button btnCadProd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        int prodcode = 0;


        txtname = findViewById(R.id.txtprodname);
        txtprice = findViewById(R.id.txtprodprice);
        txtcat = findViewById(R.id.txtcatname);
        btnCadProd = findViewById(R.id.btnAddProd);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            prodcode = extras.getInt("Product");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiProduct apiProduct = retrofit.create(ApiProduct.class);

        if(prodcode != 0)
        Request(prodcode, apiProduct);

    }

    private void Request(int procode, ApiProduct apiProduct) {

        call = apiProduct.getDetailsProduct(procode);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                List<Product> products = response.body();

                txtname.setText(products.get(0).getProdName());
                txtprice.setText(products.get(0).getProdPrice());
                txtcat.setText(products.get(0).getCatName());

                btnCadProd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddOrderItem(products);
                        finish();
                    }
                });
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(DetailsProductActivity.this);
                missingserver.setTitle("Server not found!");
                missingserver.setMessage("Servidores Offline, por favor tente novamente mais tarde");
                missingserver.create().show();
            }
        });
    }
    public void AddOrderItem(List<Product> product){
        ActionOrderItem actionOrderItem = new ActionOrderItem(DetailsProductActivity.this);
        OrderItem orderItem = new OrderItem();
        orderItem.setProdBarCode(product.get(0).getProdBarCode());
        orderItem.setProdName(product.get(0).getProdName());
        orderItem.setItemUnitaryPrice(product.get(0).getProdPrice());
        orderItem.setItemAmount(1);
        Log.e(TAG, "Teste: " + orderItem.getProdBarCode());

        if(actionOrderItem.DetailsOrderItem(orderItem))
        {
            actionOrderItem.AddOrderItem(orderItem);
        }else {
            Toast.makeText(getApplicationContext(),"Produto já adicionar ao carrinho",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Produto já adicionado");
        }
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