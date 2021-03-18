package com.example.testdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        private EditText Name;
        private EditText Password;
        private TextView Info;
        private Button login;
        private int counter = 5;
        private ImageButton secretReset;
    //This is the beginning of the app code. Inside we assign the private variables
    // declared above to the elements laid out in the .xml file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvAttempts);
        login = (Button)findViewById(R.id.LoginButton);
        secretReset = (ImageButton)findViewById(R.id.ResetButton);
        //setting the initial value of the info text
        Info.setText("# of attempts remaining: 5");

        //Giving instructions to the button element using the validate method
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });


        //seting the image button of doge to reset login attempts
        secretReset.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v) {
                reset();
          }
        });


        //setting a long press of doge image button to reveal easter egg
        secretReset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                secret();
                return false;
            }
        });
    }

    private void validate(String userName, String userPassword){
        if((userName.equals("KingDoge")) && (userPassword.equals("password"))){
            //Setting up action to switch from main activity to second activity
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            //Starting the new activity (loggin in)
            startActivity(intent);
        }
        else{
            counter--;
            //Displaying number of attempts left to log in
            Info.setText("# of attempts remaining: " + String.valueOf(counter));
            if(counter == 0){
                login.setEnabled(false);
            }
        }

    }
    //reset button function for if you run out of attempts
    private void reset(){
        counter = 5;
        login.setEnabled(true);
        Info.setText("# of attempts remaining: 5");
    }

    //easter egg method
    private void secret(){
            Info.setText("YOU FOUND THE SECRET!!!");
        }


}
