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

public class SearchActivity extends AppCompatActivity {
    Button b_back,b_search;
    EditText f_code;
    String code;

    UserSingleton user = UserSingleton.getInstance(); //gets the logged in user info

    private ProgressDialog pDialog;
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        b_back = findViewById(R.id.buttonBack);
        b_search = findViewById(R.id.buttonSearch);
        f_code = findViewById(R.id.codeText);

        //dialog loading animation
//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.setCancelable(false);

        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = f_code.getText().toString();
                //findUser();
                addFriend();
            }
        });

        /**
         * When back button clicked go back to ProfileActivity
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, FriendsActivity.class);
                startActivity(i);
            }
        });
    }


    /**
     * showProgressDialog is used to show the volley request progress graphically
     */

//    private void showProgressDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    /**
//     * hideProgressDialog is used to stop the graphical progress representation
//     */
//    private void hideProgressDialog() {
//        if (pDialog.isShowing())
//            pDialog.hide();
//    }

    /**
     * findUser finds the friends id that the user entered. If found, add them as a friend
     */
//    private void findUser() {
//        showProgressDialog();
//
//        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
//                Const.URL_JSON_OBJECTServer, null,
//                new Response.Listener<JSONArray>() {
//
//                    /**
//                     * onResponse loops through the received JSON array to find each user name
//                     * @param response
//                     */
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        JSONObject userObject;
//                        JSONArray serverArray;
//
//                        Log.v(TAG, response.toString());
//                        Log.v(TAG, "the server is here");
//
//                        serverArray = response;
//
//                        //add each name in the users friends server to a list
//                        for(int i = 0; i < serverArray.length(); i++) {
//                            try {
//                                userObject = serverArray.getJSONObject(i);
//
//                                if(userObject.get("id").toString() == code) {
//                                    addFriend();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            hideProgressDialog();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//
//            /**
//             * onErrorResponse responds to errors if there is an unsuccessful volley request
//             * @param error
//             */
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
//            }
//        });
//
//        AppController.getInstance().addToRequestQueue(jsonArrReq, "jobj_req");
//    }

    /**
     * addFriend takes the found user from code and adds them to the logged in users friends list
     */
    private void addFriend() {
        JSONObject object = new JSONObject();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.URL_JSON_OBJECTFRIENDS +user.getID()+"/"+code  , object,
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
                // volleyRec.setText("Error getting response");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}