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

import org.json.JSONObject;

public class JoinTeamActivity extends AppCompatActivity {
    Button b_back, b_search;
    EditText t_code;
    String code;


    private static final String TAG = "JoinTeamActivity";

    UserSingleton user = UserSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);

        b_back = findViewById(R.id.buttonBack);
        b_search = findViewById(R.id.buttonSearch);
        t_code = findViewById(R.id.codeText);


        /**
         * When search button clicked join team go to profile activity
         */
        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code = t_code.getText().toString();

                Log.v(TAG, "UserName using singleton " + user.getName());
                Log.v(TAG, "Password using singleton " + user.getPass());
                Log.v(TAG, "Id using singleton " + user.getID());

                joinTeam();

                Intent i = new Intent(JoinTeamActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        /**
         * When back button clicked go back to ProfileActivity
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(JoinTeamActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }


    /**
     * addFriend takes the found user from code and adds them to the logged in users friends list
     */
    private void joinTeam() {
        JSONObject object = new JSONObject();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_JSON_OBJECTTEAM +code +"/" +user.getID() , object,
                new Response.Listener<JSONObject>() {
                    /**
                     * onResponse prints a log command to show that the volley request completed successfully
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {
            /**\
             * onErrorResponse handles any unsuccessful volley request
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}