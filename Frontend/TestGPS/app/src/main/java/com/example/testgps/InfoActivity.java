package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    //TODO
    //Make is so this data is sent somewhere
    //Declairing user input fields

    TextView ip_userName, ip_password;
    String userName, password;
    Button b_Login;
    private static final String TAG = "InfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //initializing EditTexts and login button
        ip_userName = findViewById(R.id.ip_userName);
        ip_password = findViewById(R.id.ip_password);
        b_Login = findViewById(R.id.b_Login);

        b_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting username and password from the login page
                userName = ip_userName.getText().toString();
                password = ip_password.getText().toString();
                Intent i = new Intent(InfoActivity.this, MainActivity.class);

                i.putExtra("Uname", userName);
                i.putExtra("Pword", password);

                Log.v(TAG, "UserName: " + userName);
                Log.v(TAG, "Password: " + password);
                startActivity(i);
            }
        });

    }
}