package lazertag.users.User;

import java.util.List;
import javax.persistence.*;
import io.swagger.annotations.*;

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
    @JoinTable(name="friends",
     joinColumns=@JoinColumn(name="person"),
     inverseJoinColumns=@JoinColumn(name="friend")
    )
    private List<User> friends;

    @ManyToMany
    @JoinTable(name="friends",
     joinColumns=@JoinColumn(name="friend"),
     inverseJoinColumns=@JoinColumn(name="person")
    )
    private List<User> friendOf;
    
    //TODO:Implement these variables
    private int wins;
    private int loses;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public User() {
        this.name = null;
        this.password = null;
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
    public List<User> getFriends() {
    	return friends;
    }
    
    public void addFriend(User friend) {
    	if(friends==null) {
    		friends.add(friend);
    	}
    	for(int i =0;i<friends.size();i++) {
    		if(friends.get(i).getId()>friend.getId()) {
    			friends.add(i, friend);
    			return;
    		}
    	}
    	
    }
    
    public void removeFriend(User friend) {
    	if(friends.size()>0) {
    		for(int i =0;i<friends.size();i++) {
        		if(friends.get(i).getId()==friend.getId()) {
        			friends.remove(i);
        			return;
        		}
        	}
    	}
    }*/
    
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
