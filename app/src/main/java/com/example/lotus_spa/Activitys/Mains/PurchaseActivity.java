package com.example.lotus_spa.Activitys.Mains;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotus_spa.Activitys.Details.DetailsProductActivity;
import com.example.lotus_spa.Activitys.Your.YourProductsActivity;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PurchaseActivity extends AppCompatActivity {
    private static final String TAG = "PurshaseActivity";
    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private Spinner spinner;
    RecyclerView myrv;
    AdapterPurshase myAdapter;
    TextView txtCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purshase);

        myrv = findViewById(R.id.rvPurshaseProd);
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



        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void ConfigSpinner(){
        ArrayList<String> categoryspinner= new ArrayList<>();
        categoryspinner.add("Todos");
        categoryspinner.add("Creme");
        categoryspinner.add("Shampoo");
        categoryspinner.add("Pente");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = findViewById(R.id.spCategoryProduct);
        spinner.setAdapter(adapter);
    }
     private void Request(String category, ApiProduct apiProduct) {
         Call<List<Product>> call;
        call = apiProduct.getProductsCategory(category);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NotNull Call<List<Product>> call, @NotNull Response<List<Product>> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: received information" + response.body().toString());

                response.body().toString();
                List<Product> lstProducts = new ArrayList<>(response.body());


                //Spinner
                myAdapter = new AdapterPurshase(PurchaseActivity.this, lstProducts, new AdapterPurshase.ItemClickListener(){
                    @Override
                    public void onItemClick(Product product) {
                        Intent intent = new Intent(PurchaseActivity.this, DetailsProductActivity.class);
                        intent.putExtra("Product", product.getProdBarCode());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    @Override
                    public void onAddClick(Product product) {
                        ActionOrderItem actionOrderItem = new ActionOrderItem(PurchaseActivity.this);
                        OrderItem orderItem = new OrderItem();

                        orderItem.setProdBarCode(product.getProdBarCode());
                        orderItem.setProdName(product.getProdName());
                        orderItem.setItemUnitaryPrice(product.getProdPrice());
                        orderItem.setItemAmount(1);

                        if(actionOrderItem.DetailsOrderItem(orderItem))
                        {
                            actionOrderItem.AddOrderItem(orderItem);
                            setupBadged();
                        }else {
                            Log.e(TAG, "Produto já adicionado");
                            Toast.makeText(PurchaseActivity.this,"Produto já adicionar ao carrinho",Toast.LENGTH_SHORT).show();

                        }
                        //terminar
                    }
                });
                myrv.setLayoutManager(new GridLayoutManager(PurchaseActivity.this, 3));
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(@NotNull Call<List<Product>> call, @NotNull Throwable t) {
                Log.e(TAG, "Infelizmente algo deu errado na chamada do banco2: " + t.getMessage());
                AlertDialog.Builder missingserver = new AlertDialog.Builder(PurchaseActivity.this);
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.cart_notification_menu, menu);

        final MenuItem cart = menu.findItem(R.id.action_cart);

        View actionView = cart.getActionView();

        txtCart = actionView.findViewById(R.id.cart_badge);

        setupBadged();

        actionView.setOnClickListener(v -> onOptionsItemSelected(cart));

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_cart){
            Intent intent = new Intent(PurchaseActivity.this, YourProductsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadged(){
        ActionOrderItem actionOrderItem = new ActionOrderItem(this);
        if(txtCart != null){
            if(actionOrderItem.VerificarOrderItem() == 0) {
                if (txtCart.getVisibility() != View.GONE) {
                    txtCart.setVisibility(View.GONE);
                }
            }else {
                txtCart.setText(String.valueOf(Math.min(actionOrderItem.VerificarOrderItem(), 99)));
                if (txtCart.getVisibility() != View.VISIBLE) {
                    txtCart.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        setupBadged();
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