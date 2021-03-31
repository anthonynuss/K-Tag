package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testgps.InfoActivity;

public class ProfileActivity extends AppCompatActivity {
    Button b_back, b_friends, b_jointeam, b_createteam;
    TextView user_name;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        b_back = findViewById(R.id.buttonBack);
        b_friends = findViewById(R.id.buttonFriends);
        b_jointeam = findViewById(R.id.buttonJoinTeam);
        b_createteam = findViewById(R.id.buttonCreateTeam);
        user_name = findViewById(R.id.myName);
        user_name.setText("No user selected");
        UserSingleton user = UserSingleton.getInstance();

        user_name.setText(user.getName());


        /**
         * Goes to create team on click
         */
        b_createteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, CreateTeamActivity.class);
                startActivity(i);
            }
        });

        /**
         * Goes to join team on click
         */
        b_jointeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, JoinTeamActivity.class);
                startActivity(i);
            }
        });

        /**
         * Goes to friends list on click
         */
        b_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, FriendsActivity.class);
                startActivity(i);
            }
        });

        /**
         * When back button clicked go back to MainActivity
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}