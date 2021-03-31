package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LeaderboardActivity extends AppCompatActivity {
    Button b_back;
    TextView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        b_back = findViewById(R.id.buttonBack);
        userInfo = findViewById(R.id.name1);

        //users info
        UserSingleton user = UserSingleton.getInstance();
        userInfo.setText(user.getName());






        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeaderboardActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}