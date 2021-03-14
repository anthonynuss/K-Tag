package lazertag.users.User;

//import java.util.Date;

import javax.persistence.*;
import io.swagger.annotations.*;

//import lazertag.users.Team.Team;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //
    @ApiModelProperty(notes = "Name of the User",name="name",required=true,value="name")
    private String name;
    @ApiModelProperty(notes = "Password of the User",name="password",required=true,value="password")
    private String password;
    //private Date joiningDate;
    @ApiModelProperty(notes = "Latitude of the User",name="latitude",required=false,value="lat")
    private double lat;
    @ApiModelProperty(notes = "Longitude of the User",name="longitude",required=false,value="long")
    private double lng;

    /*
     * Team object creation and constructors that use Teams and join dates are not working correctly as the moment.
     * These are features that will work later.
     * 
    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "team_name")
    private Team team;*/

     // =============================== Constructors ================================== //
    /* team things
    public User(String name, String password, Date joiningDate, Team team) {
        this.name = name;
        this.password = password;
        this.joiningDate = joiningDate;
        this.team = team;
    }

    public User(String name, String password, Date joiningDate) {
        this.name = name;
        this.password = password;
        this.joiningDate = joiningDate;
    }*/
    
    /*
     * Creates a user object with the given username and password
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    
    // =============================== Getters and Setters for each field ================================== //


    /*
     * gets the user's ID
     */
    public int getId(){
        return id;
    }

    /*
     * sets the user's ID
     */
    public void setId(int id){
        this.id = id;
    }

    /*
     * gets the user's name
     */
    public String getName(){
        return name;
    }

    /*
     * sets the user's name
     */
    public void setName(String name){
        this.name = name;
    }

    /*
     * gets the user's password
     */
    public String getPassword(){
        return password;
    }

    /*
     * sets the user's password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /*
     * gets and sets the user's date they joined. Does not work yet. will be implemented later
     */
    /*
    public Date getJoiningDate(){
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate){
        this.joiningDate = joiningDate;
    }*/
    
    
    /*
     * gets the user's latitude
     */
    public double getLatitude(){
        return lat;
    }

    /*
     * sets the user's latitude
     */
    public void setLatitude(double lat){
        this.lat = lat;
    }
    
    /*
     * gets the user's longitude
     */
    public double getLongitude(){
        return lng;
    }

    /*
     * sets the user's longitude
     */
    public void setLongitude(double lng){
        this.lng = lng;
    }


    /*
     * gets the user's team. Teams do not work yet, will be implemented later
     */
    /* team things
    public Team getTeam(){
        return team;
    }

	/*
     * sets the user's team. Teams do not work yet, will be implemented later
     */
    /*
    public void setTeam(Team team){
        this.team = team;
    }*/
}
