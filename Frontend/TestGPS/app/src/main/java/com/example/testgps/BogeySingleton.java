package com.example.testgps;

public class BogeySingleton {

        private static BogeySingleton bogey_instance = null;

        //user variables (this can be added too if needed
        private String userName, password, id;
        private int teamID;
        //constructor for user_instance singleton
        private BogeySingleton(){
            userName = null;
            password = null;
            id = null;
            //teamID = Integer.parseInt(null);

        }

        //method to get an instance of user_instance in other classes
        public synchronized static com.example.testgps.BogeySingleton getInstance(){
            if(bogey_instance == null){
                bogey_instance = new com.example.testgps.BogeySingleton();
            }
            return bogey_instance;
        }

        //method to get user_instance id in other classes
        public synchronized static String getID() { return bogey_instance.id; }

        //method to get user_instance name in other classes
        public synchronized static String getName(){
            return bogey_instance.userName;
        }
        //method to get user_instance password in other classes
        public synchronized  static String getPass(){
            return bogey_instance.password;
        }

        //method to get user_instance teamID in other classes
        public synchronized static int getTeamID(){return bogey_instance.teamID;}

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


        //method to set user_instance teamID
        public synchronized void setTeamID(int teamID){this.teamID = teamID;}
    }


