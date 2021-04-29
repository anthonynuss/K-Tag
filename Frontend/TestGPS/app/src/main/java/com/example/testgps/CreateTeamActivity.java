package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateTeamActivity extends AppCompatActivity {
    Button b_back, b_createTeam;
    EditText t_name;
    String teamName;
    String code;

    UserSingleton user = UserSingleton.getInstance(); //gets the logged in user info
    UserTeamSingleton team = UserTeamSingleton.getInstance();
    private static final String TAG = "SearchActivity";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        b_back = findViewById(R.id.buttonBack);
        b_createTeam = findViewById(R.id.createTeamBtn);
        t_name = findViewById(R.id.teamNameView);

        //dialog loading animation
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        b_createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamName = t_name.getText().toString();
                createTeam();
                //findTeamID();
                //joinTeam();
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
    private void createTeam() {
        showProgressDialog();
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
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            /**\
             * onErrorResponse handles any unsuccessful volley request
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                // volleyRec.setText("Error getting response");
                hideProgressDialog();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * addFriend takes the found user from code and adds them to the logged in users friends list
     */
    private void joinTeam() {
        showProgressDialog();
        JSONObject object = new JSONObject();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_JSON_OBJECTTEAM +team.getTeamID() +"/" +user.getID() , object,
                new Response.Listener<JSONObject>() {
                    /**
                     * onResponse prints a log command to show that the volley request completed successfully
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            /**\
             * onErrorResponse handles any unsuccessful volley request
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * finds the team info for the user
     */
    private void findTeamID() {
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

                                if(teamObject.get("name").equals(teamName)) {
                                    code = teamObject.get("id").toString();
                                    team.setTeamID(teamObject.get("id").toString());
                                    System.out.println(team.getTeamID());

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
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