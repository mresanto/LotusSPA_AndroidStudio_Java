package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lotus_spa.Adapters.AdapterPackages;
import com.example.lotus_spa.Adapters.AdapterPurshase;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Packages;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Interface.ApiPackages;
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

public class PackagesActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private static final String TAG = "Packages";

    AdapterPackages myAdapter;
    RecyclerView myrv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        myrv = findViewById(R.id.rvPackagesGrid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiPackages apiPackages = retrofit.create(ApiPackages.class);

        Request(apiPackages);

    }

    private void Request(ApiPackages apiPackages) {
        Call<List<Packages>> call;
        call = apiPackages.getPackages();

        call.enqueue(new Callback<List<Packages>>() {
            @Override
            public void onResponse(Call<List<Packages>> call, Response<List<Packages>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: received information" + response.body().toString());
                response.body().toString();

                List<Packages> lstPackages = new ArrayList<>(response.body());
                myAdapter = new AdapterPackages(PackagesActivity.this, lstPackages , new AdapterPackages.ItemClickListener() {
                    @Override
                    public void onItemClick(Packages packages) {

                        Intent intent = new Intent(PackagesActivity.this, DetailsPackagesActivity.class);
                        intent.putExtra("Package", packages.getPackcode());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                });
                myrv.setLayoutManager(new GridLayoutManager(PackagesActivity.this, 3));
                myrv.setAdapter(myAdapter);

            }
            @Override
            public void onFailure(Call<List<Packages>> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onRestart() {
        //setupBadged();
        super.onRestart();
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