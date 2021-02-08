package com.example.helloworld;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView mytext;
    Button mybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mytext = (TextView) findViewById(R.id.textView2);
        mybutton = (Button) findViewById(R.id.button);

        mybutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mytext.setText("Hello World!");
            }
        });
    }
}
