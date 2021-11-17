package com.example.lotus_spa.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lotus_spa.Adapters.AdapterPurshase;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.R;

import java.util.ArrayList;
import java.util.List;

public class PurshaseActivity extends AppCompatActivity {
    Adapter adapter;
    List<Product> lstProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purshase);

        //spiner

        ArrayList<String> categoryspinner= new ArrayList<String>();
        categoryspinner.add("Frango");
        categoryspinner.add("Carne");
        categoryspinner.add("Banco");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spCategoryProduct);
        spinner.setAdapter(adapter);

        //Pegar lista de produtos
        lstProducts = new ArrayList<>();

        Product p = new Product();
        p.setProdName("Miojo");
        p.setImageProduct(R.drawable.ic_launcher_foreground);

        lstProducts.add(p);
        lstProducts.add(p);
        lstProducts.add(p);
        lstProducts.add(p);
        lstProducts.add(p);
        lstProducts.add(p);


        RecyclerView myrv = (RecyclerView) findViewById(R.id.rvPurshaseProd);
        AdapterPurshase myAdapter = new AdapterPurshase(this,lstProducts);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);
    }
}