package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lotus_spa.Class.Packages;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Interface.ApiPackages;
import com.example.lotus_spa.Interface.ApiProduct;
import com.example.lotus_spa.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsPackagesActivity extends AppCompatActivity {

    private Call<List<Packages>> call;
    private static final String TAG = "DetailsPackages";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    public TextView txtname,txtprice,txtdescription;
    public Button btnBuyPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_packages);
        int packcode = 0;


        txtname = findViewById(R.id.txtpackname);
        txtprice = findViewById(R.id.txtpackprice);
        txtdescription = findViewById(R.id.txtpackdescription);
        btnBuyPackage = findViewById(R.id.btnBuyPackage);


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

    }

    private void Request(int packcode, ApiPackages apiPackages) {
        call = apiPackages.getDetailsPackages(packcode);
        call.enqueue(new Callback<List<Packages>>() {
            @Override
            public void onResponse(Call<List<Packages>> call, Response<List<Packages>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                List<Packages> packages = response.body();

                txtname.setText(packages.get(0).getPackname());
                txtprice.setText(packages.get(0).getPackprice());
                txtdescription.setText(packages.get(0).getPackdescription());

                btnBuyPackage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //AddOrderItem(products);
                        finish();
                    }
                });
            }
            @Override
            public void onFailure(Call<List<Packages>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(DetailsPackagesActivity.this);
                missingserver.setTitle("Server not found!");
                missingserver.setMessage("Servidores Offline, por favor tente novamente mais tarde");
                missingserver.create().show();
            }
        });
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