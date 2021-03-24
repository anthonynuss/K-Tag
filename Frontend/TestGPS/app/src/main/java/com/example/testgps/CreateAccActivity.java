package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccActivity extends AppCompatActivity {

    Button b_back, b_Register;
    EditText ip_password1, ip_password2;
    TextView t_verify;
    String password, passwordVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        b_back = findViewById(R.id.buttonBack);
        b_Register = findViewById(R.id.buttonRegister);
        ip_password1 = findViewById(R.id.editTextPassword);
        ip_password2 = findViewById(R.id.editTextPassword2);
        t_verify = findViewById(R.id.textPassVerified);

        /**
         * onclick registers user if passwords lineup
         */
        b_Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                password = ip_password1.getText().toString();
                passwordVerify = ip_password2.getText().toString();

                Intent i = new Intent(CreateAccActivity.this, InfoActivity.class);
                //verify passwords match
                if(password.equals(passwordVerify)) {
                    startActivity(i);
                }else {
                    t_verify.setText("Passwords do not match!");
                }

            }
        });

        /**
         * onclick goes back to InfoActivity
         */
        b_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });
    }
}