package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.testgps.InfoActivity;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    Button b_back, b_friends, b_jointeam, b_createteam, b_myteam, b_settings;
    TextView user_name, id_view, team_view, wins_view, losses_view, tags_view, knockouts_view, noTeam_view;
    UserSingleton user;

    UserTeamSingleton team = UserTeamSingleton.getInstance();

    private ProgressDialog pDialog;
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        b_back = findViewById(R.id.buttonBack);
        b_friends = findViewById(R.id.buttonFriends);
        b_jointeam = findViewById(R.id.buttonJoinTeam);
        b_createteam = findViewById(R.id.buttonCreateTeam);
        b_myteam = findViewById(R.id.buttonTeam);
        b_settings = findViewById(R.id.buttonSettings);
        id_view = findViewById(R.id.textViewID);
        team_view = findViewById(R.id.teamView);
        user_name = findViewById(R.id.myName);
        wins_view = findViewById(R.id.winsView);
        losses_view = findViewById(R.id.lossesView);
        tags_view = findViewById(R.id.tagView);
        knockouts_view = findViewById(R.id.knockoutView);
        noTeam_view = findViewById(R.id.textViewNoTeam);

        user = UserSingleton.getInstance();

        user_name.setText(user.getName()); //sets users name
        id_view.setText("ID: " +user.getID());


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        getPlayerInfo();
        findUserTeam();

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
         * Goes to my team on click
         */
        b_myteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(team.getUserTeam() != null) {
                    Intent i = new Intent(ProfileActivity.this, UserTeamActivity.class);
                    startActivity(i);
                }else {
                    noTeam_view.setText("Create or Join a team to view My Team.");
                }
            }
        });

        /**
         * When settings button clicked go back to SettingsActivity
         */
        b_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, SettingsActivity.class);
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
     * findLoginCredentials gets all users from server and finds the user that the person entered by comparing username and password.
     */
    private void getPlayerInfo() {
        showProgressDialog();
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_OBJECTServer, null,
                new Response.Listener<JSONArray>() {

                    /**
                     * onResponse loops through the received JSON array to find each user name
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject userObject;
                        JSONArray serverArray;


                        Log.v(TAG, response.toString());
                        Log.v(TAG, "the server is here");

                        serverArray = response;
                        for(int i = 0; i < serverArray.length(); i++){
                            try {
                                userObject = serverArray.getJSONObject(i);
                                String findUser = userObject.get("name").toString();
                                String userPass = userObject.get("password").toString();
                                Log.v(TAG, "User: " + findUser);
                                //if we find a person with same name and password it is a valid user.
                                if(findUser.equals(user.getName()) && userPass.equals(user.getPass())){
                                    wins_view.setText("Wins: " +userObject.get("wins").toString());
                                    user.setWins(Integer.parseInt(userObject.get("wins").toString()));
                                    losses_view.setText("Losses: " +userObject.get("losses").toString());
                                    user.setLosses(Integer.parseInt(userObject.get("losses").toString()));
                                    tags_view.setText("Tags: " +userObject.get("tags").toString());
                                    user.setTags(Integer.parseInt(userObject.get("tags").toString()));
                                    knockouts_view.setText("Knockouts: " +userObject.get("knockouts").toString());
                                    user.setKnockouts(Integer.parseInt(userObject.get("knockouts").toString()));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hideProgressDialog();
                        }

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

    /**
     * finds the team info for the user
     */
    private void findUserTeam() {
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
                                teamMembers = teamObject.getJSONArray("teammates");

                                //iterate through the members of a team.
                                for(int j = 0; j < teamMembers.length(); j++) {
                                    if (user.getName().equals(teamMembers.getJSONObject(j).get("name"))) {
                                        UserTeamSingleton team = UserTeamSingleton.getInstance();
                                        team.setName(teamObject.get("name").toString());
                                        team.setUserTeam(teamObject);
                                        team.setTeamID(teamObject.get("id").toString());
                                        team_view.setText("Team " + team.getName());
                                        Log.v(TAG, "We found the user: " + teamMembers.getJSONObject(j).get("name"));

                                    }

                                }
//                                if(user.getID().equals(teamObject.get("captain").toString())) {
//                                            team.setName(teamObject.get("name").toString());
//                                            team.setUserTeam(teamObject);
//                                            team.setTeamID(teamObject.get("id").toString());
//                                    team_view.setText("Team Captain of " + team.getName());
//                                }


                                Log.v(TAG, "User: " + teamMembers);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hideProgressDialog();

                        }

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