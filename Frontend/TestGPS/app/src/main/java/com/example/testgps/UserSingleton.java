package com.example.testgps;

/**
 * This class will be the singleton for user data to be accessed by multiple instances across the app
 */
public class UserSingleton {

    private static UserSingleton user_instance = null;

    //user variables (this can be added too if needed
    private String userName, password;

    //constructor for user_instance singleton
    private UserSingleton(){
        userName = null;
        password = null;
    }

    //method to get an instance of user_instance in other classes
    public synchronized static UserSingleton getInstance(){
        if(user_instance == null){
            user_instance = new UserSingleton();
        }
        return user_instance;
    }

    //method to get user_instance name in other classes
    public synchronized static String getName(){
        return user_instance.userName;
    }
    //method to get user_instance password in other classes
    public synchronized  static String getPass(){
        return user_instance.password;
    }

    //method to set user_instance username
    public synchronized void setName(String nameVar){
        this.userName = nameVar;
    }

    //method to set user_instance password
    public synchronized void setPass(String passVar){
        this.password = passVar;
    }

}