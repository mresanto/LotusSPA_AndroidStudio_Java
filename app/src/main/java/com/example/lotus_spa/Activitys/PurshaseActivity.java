package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lotus_spa.Adapters.AdapterPurshase;
import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Interface.ApiCustomer;
import com.example.lotus_spa.Interface.ApiProduct;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PurshaseActivity extends AppCompatActivity {
    private static final String TAG = "UpdateAccount";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private Spinner spinner;
    RecyclerView myrv;
    AdapterPurshase myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purshase);

        myrv = (RecyclerView) findViewById(R.id.rvPurshaseProd);
        // first clear the recycler view so items are not populated twice


        //spiner
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiProduct apiProduct = retrofit.create(ApiProduct.class);

        ConfigSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = spinner.getItemAtPosition(position).toString();
                Request(category, apiProduct);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void ConfigSpinner(){
        ArrayList<String> categoryspinner= new ArrayList<String>();
        categoryspinner.add("Todos");
        categoryspinner.add("Brinquedo");
        categoryspinner.add("Eletronico");
        categoryspinner.add("Banco");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spCategoryProduct);
        spinner.setAdapter(adapter);


    }
     private void Request(String category, ApiProduct apiProduct) {

        Call<List<Product>> call;
        call = apiProduct.getProductsCategory(category);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                List<Product> lstProducts = new ArrayList<>();

                if (response.body().toString() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Product product = response.body().get(i);
                        lstProducts.add(product);
                    }
                    //Spinner
                    myAdapter = new AdapterPurshase(PurshaseActivity.this, lstProducts, new AdapterPurshase.ItemClickListener() {
                        @Override
                        public void onItemClick(Product product) {
                            ActionOrderItem actionOrderItem = new ActionOrderItem(PurshaseActivity.this);
                            OrderItem orderItem = new OrderItem();
                            orderItem.setProdBarCode(product.getProdBarCode());
                            orderItem.setItemUnitaryPrice(product.getProdBarCode());
                            orderItem.setItemAmount(1);
                            actionOrderItem.AddOrderItem(orderItem);
                            //terminar
                        }
                    });
                    myrv.setLayoutManager(new GridLayoutManager(PurshaseActivity.this, 3));
                    myrv.setAdapter(myAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
            }
        });
    }
}