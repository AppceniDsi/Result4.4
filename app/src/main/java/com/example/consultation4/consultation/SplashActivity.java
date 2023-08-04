package com.example.consultation4.consultation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consultation4.R;
import com.example.consultation4.service.dbSqLite;

public class SplashActivity extends AppCompatActivity {
    dbSqLite DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DB = new dbSqLite(this);
        DB.deleteAllLocalisation();
        DB.insertLocalisation(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }
}
