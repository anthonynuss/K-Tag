package com.example.testgps;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * This class will be the singleton for user data to be accessed by multiple instances across the app
 */
public class UserTeamSingleton {

    private static UserTeamSingleton team_instance = null;

    //Teammates in team (this can be added too if needed
    private JSONArray teamList, opponentList;

    //constructor for user_instance singleton
    private UserTeamSingleton(){
       teamList = null;
       opponentList = null;
    }

    //method to get an instance of user_instance in other classes
    public synchronized static UserTeamSingleton getInstance(){
        if(team_instance == null){
            team_instance = new UserTeamSingleton();
        }
        return team_instance;
    }
    //method to use the array values pulled to set a team
    public synchronized static void setTeam(JSONArray userTeam){
        team_instance.teamList = userTeam;
    }


    //getter methods for returning teamList and  opponentList
    public synchronized static  JSONArray getTeam(){return team_instance.teamList;}
    public synchronized static JSONArray getOpponent(){return team_instance.opponentList;}


    //method to get a specific team mate from team_instance
    public synchronized static JSONObject getTeamMate(int teamMateID) throws JSONException {
        return team_instance.teamList.getJSONObject(teamMateID);
    }

    //method to get a specific opponents from team_instance
    public synchronized static JSONObject getOpponent(int opponentID) throws JSONException {
        return team_instance.opponentList.getJSONObject(opponentID);
    }



    //small helper method to return the length of our teamArray
    public int teamLength() {
        return team_instance.teamList.length();
    }

    //small helper method to return the length of our opponentArray
    public int opponentLength(){
        return team_instance.opponentList.length();
    }
}
