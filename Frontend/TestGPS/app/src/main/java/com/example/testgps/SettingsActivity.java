package com.example.testgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SettingsActivity extends AppCompatActivity {
    Button b_back, b_changeName, b_changePass;
    Button b_winsMinus, b_lossesMinus, b_tagsMinus, b_knockoutsMinus, b_winsPlus, b_lossesPlus, b_tagsPlus, b_knockoutsPlus;
    EditText e_name, e_pass1, e_pass2;
    TextView t_name, t_passError, t_wins, t_losses, t_tags, t_knockouts;

    private ProgressDialog pDialog;
    private static final String TAG = "ProfileActivity";

    UserSingleton user = UserSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        b_back = findViewById(R.id.buttonBack);
        b_changeName = findViewById(R.id.buttonNewName);
        b_changePass = findViewById(R.id.buttonNewPassword);
        b_winsMinus = findViewById(R.id.buttonWinsMinus);
        b_lossesMinus = findViewById(R.id.buttonLossesMinus);
        b_tagsMinus = findViewById(R.id.buttonTagsMinus);
        b_knockoutsMinus = findViewById(R.id.buttonKnockoutsMinus);
        b_winsPlus = findViewById(R.id.buttonWinsPlus);
        b_lossesPlus = findViewById(R.id.buttonLossesPlus);
        b_tagsPlus = findViewById(R.id.buttonTagsPlus);
        b_knockoutsPlus = findViewById(R.id.buttonKnockoutsPlus);

        e_name = findViewById(R.id.editTextName);
        e_pass1 = findViewById(R.id.editTextPassword);
        e_pass2 = findViewById(R.id.editTextPassword2);

        t_name = findViewById(R.id.editTextName);
        t_passError = findViewById(R.id.textViewPassError);
        t_wins = findViewById(R.id.winsView);
        t_losses = findViewById(R.id.lossesView);
        t_tags = findViewById(R.id.tagView);
        t_knockouts = findViewById(R.id.knockoutView);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        t_wins.setText("Wins: " +user.getWins());
        t_losses.setText("Losses: " +user.getLosses());
        t_tags.setText("Tags: " +user.getTags());
        t_knockouts.setText("Knockouts: " +user.getKnockouts());

        /**
         * change users name
         */
        b_changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_name.getText().length() != 0) {
                    user.setName(e_name.getText().toString());
                    updateUser();
                    Intent i = new Intent(SettingsActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
            }
        });

        /**
         * change users password
         */
        b_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_pass1.getText().length() != 0 && e_pass1.getText().toString().equals(e_pass2.getText().toString())) {
                    user.setPass(e_pass1.getText().toString());
                    updateUser();
                    Intent i = new Intent(SettingsActivity.this, ProfileActivity.class);
                    startActivity(i);
                }else {
                    t_passError.setText("Passwords do not match");
                }
            }
        });

        /**
         * minus stats on click
         */
        b_winsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setWins(Math.max(0,user.getWins()-1));
                t_wins.setText("Wins: " +user.getWins());
            }
        });
        b_lossesMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLosses(Math.max(0,user.getLosses()-1));
                t_losses.setText("Losses: " +user.getLosses());
            }
        });
        b_tagsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setTags(Math.max(0,user.getTags()-1));
                t_tags.setText("Tags: " +user.getTags());
            }
        });
        b_knockoutsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setKnockouts(Math.max(0,user.getKnockouts()-1));
                t_knockouts.setText("Knockouts: " +user.getKnockouts());
            }
        });

        /**
         * add stats on click
         */
        b_winsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setWins(user.getWins()+1);
                t_wins.setText("Wins: " +user.getWins());
            }
        });
        b_lossesPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLosses(user.getLosses()+1);
                t_losses.setText("Losses: " +user.getLosses());
            }
        });
        b_tagsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setTags(user.getTags()+1);
                t_tags.setText("Tags: " +user.getTags());
            }
        });
        b_knockoutsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setKnockouts(user.getKnockouts()+1);
                t_knockouts.setText("Knockouts: " +user.getKnockouts());
            }
        });



        /**
         * When back button clicked update settings. go back to MainActivity
         */
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
                Intent i = new Intent(SettingsActivity.this, ProfileActivity.class);
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
     * updates the users info
     */
    private void updateUser() {
        showProgressDialog();
        JSONObject object = new JSONObject();
        try {
            object.put("name", user.getName());
            object.put("password", user.getPass());
            object.put("wins", user.getWins());
            object.put("losses", user.getLosses());
            object.put("tags", user.getTags());
            object.put("knockouts", user.getKnockouts());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_JSON_OBJECT +user.getID(), object,
                new Response.Listener<JSONObject>() {
                    /**
                     * onResponse prints a log command to show that the volley request completed successfully
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
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
     * Closes keyboard when user presses outside an input
     * @param view
     */
    public void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}