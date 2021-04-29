package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JoinTeamActivity extends AppCompatActivity {
    Button b_back, b_search;
    EditText t_code;
    String code;


    ListView t_listView;
    List teamList = new ArrayList();
    ArrayAdapter adapter;


    private static final String TAG = "JoinTeamActivity";
    private ProgressDialog pDialog;

    UserSingleton user = UserSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);

        b_back = findViewById(R.id.buttonBack);
        b_search = findViewById(R.id.buttonSearch);
        t_code = findViewById(R.id.codeText);
        t_listView = findViewById(R.id.list_view);


        //dialog loading animation
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        findAllTeams();

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
     * showProgressDialog is used to show the volley request progress graphically
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * hideProgressDialog is used to stop the graphical progress representation
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
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



    /**
     * finds all teams
     */
    private void findAllTeams() {
        showProgressDialog();
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_OBJECTTEAM, null,
                new Response.Listener<JSONArray>() {

                    /**
                     * onResponse loops through the received JSON array to find each user name
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject teamObject;
                        JSONArray serverArray;
                        JSONArray teamMembers;
                        JSONObject thisTeam;
                        String userPass;

                        Log.v(TAG, response.toString());
                        Log.v(TAG, "the server is here");

                        serverArray = response;
                        //iterate whole team server page
                        for(int i = 0; i < serverArray.length(); i++){

                            try {
                                teamObject = serverArray.getJSONObject(i);
                                Log.v(TAG, "teamObject: " + teamObject);

                                teamList.add(teamObject.get("name").toString() +" ID: " +teamObject.get("id").toString());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        adapter = new ArrayAdapter(JoinTeamActivity.this, android.R.layout.simple_list_item_1, teamList);
                        t_listView.setAdapter(adapter);
                        hideProgressDialog();

                    }
                }, new Response.ErrorListener() {

            /**
             * onErrorResponse responds to errors if there is an unsuccessful volley request
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrReq, "jobj_req");
    }
}