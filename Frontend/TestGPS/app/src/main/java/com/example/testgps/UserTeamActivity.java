package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserTeamActivity extends AppCompatActivity {
    Button b_back, b_leave;
    TextView t_view;

    //for list
    ListView f_listView;
    List membersList = new ArrayList();
    ArrayAdapter adapter;

    UserSingleton user = UserSingleton.getInstance();
    //get an array of all the teammates on users team
    UserTeamSingleton team = UserTeamSingleton.getInstance();
    JSONArray teammates;
    {
        try {
            teammates = team.getUserTeam().getJSONArray("teammates");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_team);

        b_back = findViewById(R.id.buttonBack);
        b_leave = findViewById(R.id.buttonLeaveTeam);
        f_listView = findViewById(R.id.membersList);
        t_view = findViewById(R.id.teamView);

        //loop through teammate array and add them to list.
        for(int i = 0; i < teammates.length(); i++) {
            try {
                membersList.add(teammates.getJSONObject(i).get("name"));

                t_view.setText(team.getUserTeam().get("name").toString()); //set team name text
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //add list to listview
        adapter = new ArrayAdapter(UserTeamActivity.this, android.R.layout.simple_list_item_1, membersList);
        f_listView.setAdapter(adapter);


        b_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveTeam();
                team.setUserTeam(null);
                team.setTeamID(null);
                team.setName(null);
                Intent i = new Intent(UserTeamActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });


        /**
         * When back button clicked go back to ProfileActivity
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserTeamActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * removes the user from the team
     */
    private void leaveTeam() {
        JSONObject object = new JSONObject();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, Const.URL_JSON_OBJECTTEAM +team.getTeamID() +"/" +user.getID(), object,
                new Response.Listener<JSONObject>() {
                    /**
                     * onResponse prints a log command to show that the volley request completed successfully
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {

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