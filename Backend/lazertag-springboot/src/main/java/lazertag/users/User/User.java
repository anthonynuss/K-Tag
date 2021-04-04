package lazertag.users.User;

import java.util.List;
import javax.persistence.*;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.*;
import lazertag.users.Team.Team;

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
    @ApiModelProperty(notes = "User's friends list",name="friends",required=false,value="friends")
    //private ArrayList<User> friends = new ArrayList<User>();
    
    
    @ManyToMany
    @JoinTable(name="friends", joinColumns=@JoinColumn(name="person"), inverseJoinColumns=@JoinColumn(name="friend"))
    private List<User> friends;

    @ManyToMany
    @JoinTable(name="friends", joinColumns=@JoinColumn(name="friend"), inverseJoinColumns=@JoinColumn(name="person"))
    private List<User> friendOf;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")
    @JsonIgnore
    private Team team;
    
    //TODO:Implement these variables
    @NonNull
    private int wins;
    @NonNull
    private int losses;
    @NonNull
    private int tags;
    @NonNull
    private int knockouts;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        wins = 0;
    	losses = 0;
    	tags = 0;
    	knockouts = 0;
    }
    
    public User(String name, String password, int wins, int losses) {
        this.name = name;
        this.password = password;
        this.wins = wins;
        this.losses = losses;
    }
    
    public User() {
    	wins = 0;
    	losses = 0;
    	tags = 0;
    	knockouts = 0;
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
     * gets the user's wins
     */
    
   //GETTERS AND SETTERS FOR WINS
    public int getWins(){
        return wins;
    }

    public void setWins(int wins){
        this.wins = wins;
    }
    
    public void addWin() {
    	this.wins++;
    }
    
    //GETTERS AND SETTERS FOR LOSSES
    public int getLosses(){
        return losses;
    }

    public void setLosses(int losses){
        this.losses = losses;
    }
    
    public void addLosses() {
    	this.losses++;
    }
    
    //GETTERS AND SETTERS FOR TAGS
    public int getTags(){
        return tags;
    }

    public void setTags(int tags){
        this.tags = tags;
    }
    
    public void addTag() {
    	this.tags++;
    }
    
    //GETTERS AND SETTERS FOR KNOCKOUTS
    public int getKnockouts(){
        return knockouts;
    }

    public void setKnockouts(int knockouts){
        this.knockouts = knockouts;
    }
    
    public void addKnockouts() {
    	this.knockouts++;
    }
    
    public Team getTeam() {
    	return this.team;
    }
    
    public void setTeam(Team team) {
    	this.team = team;
    }
    
    
}
