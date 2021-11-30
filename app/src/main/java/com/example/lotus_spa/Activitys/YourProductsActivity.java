package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.lotus_spa.Adapters.AdapterOrderItem;
import com.example.lotus_spa.Adapters.AdapterPurshase;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Interface.ApiProduct;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourProductsActivity extends AppCompatActivity {

    private static final String TAG = "YourProducts";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    RecyclerView myrv;
    AdapterOrderItem myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_products);

        myrv = findViewById(R.id.rvOrderItem);


        Request();
    }

    private void Request() {

        ActionOrderItem actionOrderItem = new ActionOrderItem(this);
                List<OrderItem> lstOrder = new ArrayList<>(actionOrderItem.ListarOrderItem());
        Log.e(TAG, "Size: " + lstOrder.size());

        myAdapter = new AdapterOrderItem(YourProductsActivity.this, lstOrder, new AdapterOrderItem.ItemClickListener() {
            @Override
            public void onItemClick(Product product) {

            }

            @Override
            public void onAddClick(Product product) {

            }
        });
        myrv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        myrv.setAdapter(myAdapter);

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