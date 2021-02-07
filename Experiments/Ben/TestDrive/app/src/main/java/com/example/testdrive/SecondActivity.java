package com.example.testdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Logout = (Button)findViewById(R.id.LogOutButton);

        Logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { returnToSender(); }
        });
    }

    private void returnToSender(){
        //Switching back to main activity
            Intent logOut = new Intent(SecondActivity.this, MainActivity.class);
            //Starting the new activity (loggin in)
            startActivity(logOut);
        }
}