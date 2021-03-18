package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView info;
    private Button login;
    private int numIncorrect = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.etUsername);
        password = (EditText)findViewById(R.id.etPassword);
        info = (TextView) findViewById(R.id.tvInfo);
        login = (Button)findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void checkLogin(String userName, String Password){
        if(userName.equals("Austin") && Password.equals("1234")){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        else{
            numIncorrect --;


            info.setText("No of incorrect attempts remaining: " + String.valueOf(numIncorrect));

            if(numIncorrect == 0){
                login.setEnabled(false);
            }
        }
    }
}