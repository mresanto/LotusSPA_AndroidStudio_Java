package com.example.lotus_spa.Activitys.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lotus_spa.Activitys.EndReserveActivity;
import com.example.lotus_spa.Class.Packages;
import com.example.lotus_spa.Interface.ApiPackages;
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
    private static final String BASE_URL = "https://apilotusspa.herokuapp.com/api/v1/";
    public TextView txtname,txtprice,txtdescription;
    public Button btnBuyPackage;
    public ImageView imagepackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_packages);
        int packcode = 0;


        txtname = findViewById(R.id.txtpackname);
        txtprice = findViewById(R.id.txtpackprice);
        txtdescription = findViewById(R.id.txtpackdescription);
        btnBuyPackage = findViewById(R.id.btnBuyPackage);
        imagepackage = findViewById(R.id.imgDetailsPackages);


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
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

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


                switch (packages.get(0).getPackcode())
                {
                    case 1:
                        imagepackage.setImageResource(R.drawable.beleza_spa);
                        break;
                    case 2:
                        imagepackage.setImageResource(R.drawable.spa_relax);
                }

                btnBuyPackage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //AddOrderItem(products);
                        Intent intent = new Intent(DetailsPackagesActivity.this, EndReserveActivity.class);
                        intent.putExtra("Package",packages.get(0).getPackcode());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }
            @Override
            public void onFailure(Call<List<Packages>> call, Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(DetailsPackagesActivity.this);
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