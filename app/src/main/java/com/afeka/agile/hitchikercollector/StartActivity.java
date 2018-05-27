package com.afeka.agile.hitchikercollector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import utils.LocationUtil;
import android.support.v7.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button driver_button = findViewById(R.id.driver_button);
        Button hitchhiker_button = findViewById(R.id.hitchhiker_button);
        driver_button.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 startActivity(new Intent(StartActivity.this, MainActivity.class));
                                             }
                                         }
        );
        hitchhiker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, HitchHikerActivity.class));
            }

        });
    }
}
