package com.example.lotus_spa.Activitys.Mains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lotus_spa.Activitys.PlacesActivity;
import com.example.lotus_spa.Activitys.Your.YourAccountActivity;
import com.example.lotus_spa.R;
import com.example.lotus_spa.Utilits.ActionDB.ActionOrderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Button places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide(); //hide bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE); //remove title action bar
        setContentView(R.layout.activity_main);

        ActionOrderItem actionOrderItem = new ActionOrderItem(MainActivity.this);
        actionOrderItem.UpdateAllOrderItem();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        places = findViewById(R.id.btnPlaces);

        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlacesActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
//
        //Toolbar toolbar = findViewById(R.id.toolbar);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent intent;
                    switch (item.getItemId()){

                        case R.id.our_products_menu:
                            intent = new Intent(MainActivity.this, PurchaseActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;

                        case R.id.our_packages_menu:
                            intent = new Intent(MainActivity.this, PackagesActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;

                        case R.id.action_settings:
                            intent = new Intent(MainActivity.this, YourAccountActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;

                        case R.id.make_your_reservation_menu:
                            intent = new Intent(MainActivity.this, ReserveActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            break;
//
                    //    case R.id.nav_pesquisar:
                    //        fragSelecionada= new PesquisaFragment();
                    //        break;
//
                    }
//
                    return true;
                }
            };
}