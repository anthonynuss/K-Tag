package com.example.testgps;

/**
 * This class will be the singleton for user data to be accessed by multiple instances across the app
 */
public class UserSingleton {

    private static UserSingleton user_instance = null;

    //user variables (this can be added too if needed
    private String userName, password, id;
    private int teamID, wins, losses, tags, knockouts;
    //constructor for user_instance singleton
    private UserSingleton(){
        userName = null;
        password = null;
        id = null;
        wins = 0;
        losses = 0;
        tags = 0;
        knockouts = 0;
        //teamID = Integer.parseInt(null);

    }

    //method to get an instance of user_instance in other classes
    public synchronized static UserSingleton getInstance(){
        if(user_instance == null){
            user_instance = new UserSingleton();
        }
        return user_instance;
    }

    //method to get user_instance id in other classes
    public synchronized static String getID() { return user_instance.id; }

    //method to get user_instance name in other classes
    public synchronized static String getName(){
        return user_instance.userName;
    }
    //method to get user_instance password in other classes
    public synchronized  static String getPass(){
        return user_instance.password;
    }
    //method to get user_instance stats in other classes
    public synchronized  static int getWins(){
        return user_instance.wins;
    }
    public synchronized  static int getLosses(){
        return user_instance.losses;
    }
    public synchronized  static int getTags(){
        return user_instance.tags;
    }
    public synchronized  static int getKnockouts(){
        return user_instance.knockouts;
    }

    //method to get user_instance teamID in other classes
    public synchronized static int getTeamID(){return user_instance.teamID;}

    //method to set user_instance username
    public synchronized void setName(String nameVar){
        this.userName = nameVar;
    }

    //method to set user_instance password
    public synchronized void setPass(String passVar){
        this.password = passVar;
    }

    //method to set user_instance id
    public synchronized void setID(String idVar){
        this.id = idVar;
    }

    public synchronized void setWins(int winsVar){
        this.wins = winsVar;
    }
    public synchronized void setLosses(int lossesVar){
        this.losses = lossesVar;
    }
    public synchronized void setTags(int tagsVar){ this.tags = tagsVar;
    }
    public synchronized void setKnockouts(int knockoutsVar){
        this.knockouts = knockoutsVar;
    }

    //method to set user_instance teamID
    public synchronized void setTeamID(int teamID){this.teamID = teamID;}
}
