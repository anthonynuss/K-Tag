package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateTeamActivity extends AppCompatActivity {
    Button b_back, b_createTeam;
    EditText t_name;
    String teamName;

    UserSingleton user = UserSingleton.getInstance(); //gets the logged in user info
    UserTeamSingleton team = UserTeamSingleton.getInstance();
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        b_back = findViewById(R.id.buttonBack);
        b_createTeam = findViewById(R.id.createTeamBtn);
        t_name = findViewById(R.id.teamNameView);


        b_createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamName = t_name.getText().toString();
                createTeam();

                Intent i = new Intent(CreateTeamActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });


        /**
         * When back button clicked go back to MainActivity
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateTeamActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }


    /**
     * addFriend takes the found user from code and adds them to the logged in users friends list
     */
    private void createTeam() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", teamName);
            object.put("captain", Integer.parseInt(user.getID()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.URL_JSON_OBJECTTEAM, object,
                new Response.Listener<JSONObject>() {
                    /**
                     * onResponse prints a log command to show that the volley request completed successfully
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());
                        team.setUserTeam(object);
                    }
                }, new Response.ErrorListener() {
            /**\
             * onErrorResponse handles any unsuccessful volley request
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                // volleyRec.setText("Error getting response");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}