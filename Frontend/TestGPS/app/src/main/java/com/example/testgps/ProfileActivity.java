package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.testgps.InfoActivity;

public class ProfileActivity extends AppCompatActivity {
    Button b_back;
    TextView user_name;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        b_back = findViewById(R.id.buttonBack);
        user_name = findViewById(R.id.myName);
        user_name.setText("No user selected");

        Intent k = getIntent();
        if(getIntent().getExtras() != null) {
            userName = k.getStringExtra("Uname");
            k.putExtra("Uname", userName);
            user_name.setText(userName);
        }


        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}