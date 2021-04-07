package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.testgps.app.AppController;
import com.example.testgps.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class LeaderboardActivity extends AppCompatActivity {
    Button b_back;

    ListView listView; //listview of players stats
    //ArrayList<JSONObject> infoList = new ArrayList(); //the list of player info
    SimpleAdapter adapter; //used by list
    ArrayList<HashMap<String, String>> infoList = new ArrayList<HashMap<String, String>>();

    private ProgressDialog pDialog;
    private static final String TAG = "LeaderboardActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        b_back = findViewById(R.id.buttonBack);
        listView = findViewById(R.id.list_view);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        getAllUserInfo(); //get all user info

        //users info
        UserSingleton user = UserSingleton.getInstance();

        //adapter for the listview layout.
        adapter = new SimpleAdapter(this, infoList, R.layout.adapter_view_layout,
                new String[] {"name", "wins", "losses", "tags", "knockouts"}, new int[] {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5});

        /**
         * onclick go back to profile
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeaderboardActivity.this, MainActivity.class);
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
     * findLoginCredentials gets all users from server and puts their info into the leaderBoard
     */
    private void getAllUserInfo() {
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
                        String findUser;

                        Log.v(TAG, response.toString());
                        Log.v(TAG, "the server is here");

                        serverArray = response;
                        for(int i = 0; i < serverArray.length(); i++){
                            try {
                                userObject = serverArray.getJSONObject(i);

                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("name", userObject.get("name").toString());
                                map.put("wins", userObject.get("wins").toString());
                                map.put("losses", userObject.get("losses").toString());
                                map.put("tags", userObject.get("tags").toString());
                                map.put("knockouts", userObject.get("knockouts").toString());
                                infoList.add(map);

                                listView.setAdapter(adapter);
                                
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