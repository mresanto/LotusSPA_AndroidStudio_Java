package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lotus_spa.Adapters.AdapterOrderItem;
import com.example.lotus_spa.Adapters.AdapterPurshase;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Helper.MyButtonClickListener;
import com.example.lotus_spa.Helper.MySwipeHelper;
import com.example.lotus_spa.Interface.ApiProduct;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;
import com.google.android.material.snackbar.Snackbar;

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
    private List<OrderItem> lstOrder;
    Button btnEndOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_products);
        myrv = findViewById(R.id.rvOrderItem);
        btnEndOrder = findViewById(R.id.btnEndOrder);

        Request();


        btnEndOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myAdapter.getOrderItems().size() != 0){
                    ActionOrderItem actionOrderItem = new ActionOrderItem(YourProductsActivity.this);
                    actionOrderItem.UpdateOrderItem(myAdapter.getOrderItems());
                    Intent intent = new Intent(YourProductsActivity.this, EndOrderActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else{
                    Log.e(TAG, "CheckBox não selecionada:");
                    AlertDialog.Builder missingserver = new AlertDialog.Builder(YourProductsActivity.this);
                    missingserver.setTitle("Selecionar Produto");
                    missingserver.setMessage("Você não selecionou quaisquer itens para checkout");
                    missingserver.create().show();
                }

            }
        });
    }

    private void Request() {

        ActionOrderItem actionOrderItem = new ActionOrderItem(this);
        lstOrder = new ArrayList<>(actionOrderItem.ListarOrderItem());
        myAdapter = new AdapterOrderItem(getApplicationContext(),lstOrder);
        myrv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        myrv.setAdapter(myAdapter);

        Log.d(TAG, "Teste Checked" + actionOrderItem.ListarOrderItem());


        MySwipeHelper swipeHelper = new MySwipeHelper(this, myrv, 200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MySwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(YourProductsActivity.this,
                        "Excluir",
                        30,
                        0, //image
                        Color.parseColor("#FF3c30"),
                        new MyButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(YourProductsActivity.this, "Delete Click", Toast.LENGTH_SHORT);
                                actionOrderItem.DeleteOrderItem(lstOrder.get(pos));
                                myAdapter.removeItem(pos);
                            }
                        }));
            }
        };

    }

// attaching the touch helper to recycler view

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