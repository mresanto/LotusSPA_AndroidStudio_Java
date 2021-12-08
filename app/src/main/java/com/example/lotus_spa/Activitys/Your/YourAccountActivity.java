package com.example.lotus_spa.Activitys.Your;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lotus_spa.Activitys.Mains.LoginActivity;
import com.example.lotus_spa.Activitys.UpdateAccountActivity;
import com.example.lotus_spa.R;

public class YourAccountActivity extends AppCompatActivity {

    TextView email;
    Button btnLogout, btnUpCad;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account);

        email = findViewById(R.id.txtEmail);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpCad= findViewById(R.id.btnUpCad);

        SharedPreferences s = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
        String email_s = s.getString("email","Email");

        email.setText("E-mail: " + email_s);

        btnLogout.setOnClickListener(v -> {
            AlertDialog.Builder endlogin = new AlertDialog.Builder(YourAccountActivity.this);
            endlogin.setTitle("Finalizar Sessão");
            endlogin.setMessage("Deseja finalizar a sessão em andamento");
            endlogin.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences login = getSharedPreferences("LOGINOUT", MODE_PRIVATE);
                    SharedPreferences.Editor edit = login.edit();
                    edit.clear();
                    edit.commit();

                    Intent intent = new Intent(YourAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);                    }
            });
            endlogin.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            endlogin.create().show();
        });
        btnUpCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourAccountActivity.this, UpdateAccountActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
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