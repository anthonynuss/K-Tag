package com.example.androidvolleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button strReq = (Button)findViewById(R.id.btnStringRequest);
        Button JSONReq = (Button)findViewById(R.id.btnJsonRequest);
        Button imageReq = (Button)findViewById(R.id.btnImageRequest);
        strReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StringRequestActivity.class));
            }
        });

        JSONReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StringRequestActivity.class));
            }
        });

        imageReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageRequestActivity.class));
            }
        });
    }


}

