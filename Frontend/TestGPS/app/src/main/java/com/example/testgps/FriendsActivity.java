package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    Button b_back, b_search;
    //variable used to display a list
    ListView f_listView;
    List idList = new ArrayList(); //order of friend added
    List idFriendsList = new ArrayList(); //order in the list view
    List friendsList = new ArrayList();
    ArrayAdapter adapter;


    UserSingleton user = UserSingleton.getInstance(); //gets the logged in user info

    private ProgressDialog pDialog;
    private static final String TAG = "FriendsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);



        b_back = findViewById(R.id.buttonBack);
        b_search = findViewById(R.id.buttonSearch);
        f_listView = findViewById(R.id.fiendsList);

        //dialog loading animation
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);



        getIDList();
        getFriendsList();

        //when a name is clicked send their id to the DeleteFriend activity
        f_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent (FriendsActivity.this, DeleteFriend.class);
                i.putExtra("USER_IN_LIST", idFriendsList.get(position).toString());
                System.out.println(idFriendsList.get(position).toString());
                startActivity(i);
            }
        });

        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FriendsActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        /**
         * When back button clicked go back to ProfileActivity
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FriendsActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

//    /**
//     * showProgressDialog is used to show the volley request progress graphically
//     */
//
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
     * getFriendsList gets all of the friends that a user has in the form of their id.
     */
    private void getIDList() {
        showProgressDialog();

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_OBJECTFRIENDS +user.getID(), null,
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

                        //add each name in the users friends server to a list
                        for(int i = 0; i < serverArray.length(); i++) {
                            try {
                                userObject = serverArray.getJSONObject(i);
                                Log.v(TAG, "Found friend");
                                idList.add(userObject.get("friend").toString());

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
     * getFriendsList gets all of the friends that a user has in the form of their id.
     */
    private void getFriendsList() {
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

                        //add each name in the users friends server to a list
                        for(int i = 0; i < serverArray.length(); i++) {
                            try {
                                userObject = serverArray.getJSONObject(i);
                                for(int j = 0; j < idList.size(); j++) {
                                    if(userObject.get("id").toString() == idList.get(j)) {
                                        Log.v(TAG, "add friend");
                                        friendsList.add(userObject.get("name").toString());
                                        idFriendsList.add(userObject.get("id").toString());
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hideProgressDialog();
                        }
                        adapter = new ArrayAdapter(FriendsActivity.this, android.R.layout.simple_list_item_1, friendsList);
                        f_listView.setAdapter(adapter);
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