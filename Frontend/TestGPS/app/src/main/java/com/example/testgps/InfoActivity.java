package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Anthony Nuss
 * Login page for user, asks for user info to login
 */

public class InfoActivity extends AppCompatActivity {


    //variables to hold and print username and password
    TextView ip_userName, ip_password;
    String userName, password;
    //login button to end intent
    Button b_Login;
    //log.v tag for debugging
    private static final String TAG = "InfoActivity";

    /**
     * onCreate, set textviews for users to enter info and button to login
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //initializing EditTexts and login button
        ip_userName = findViewById(R.id.ip_userName);
        ip_password = findViewById(R.id.ip_password);
        b_Login = findViewById(R.id.b_Login);

        b_Login.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick stores user entered information to able to be used in other classes. Sends user to main activity
             * @param v
             */
            @Override
            public void onClick(View v) {

                //getting username and password from the login page
                userName = ip_userName.getText().toString();
                password = ip_password.getText().toString();
                Intent k = new Intent(InfoActivity.this, MainActivity.class);

                k.putExtra("Uname", userName);
                k.putExtra("Pword", password);

                Log.v(TAG, "UserName: " + userName);
                Log.v(TAG, "Password: " + password);
                startActivity(k);
            }
        });

    }
}