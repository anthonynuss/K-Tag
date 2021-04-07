package lazertag.users.Team;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import io.swagger.annotations.*;
import lazertag.users.User.User;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //

    private String name;

    private int captainId;
    
    private int wins;
    
    private int losses;
    
    
    @OneToMany(mappedBy = "team")
    private List<User> teammates = new ArrayList<User>();


    public Team(String name, int captainId) {
        this.name = name;
        this.captainId = captainId;
        wins = 0;
        losses = 0;
    }
    
    public Team() {}
    
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
     * gets the team's name
     */
    public String getName(){
        return name;
    }

    /*
     * sets the team's name
     */
    public void setName(String name){
        this.name = name;
    }

    /*
     * gets the team's captain
     */
    public int getCaptain(){
        return captainId;
    }

    /*
     * change the team's captain
     */
    public void setCaptain(int captainId){
        this.captainId = captainId;
    }
    
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
    
    public void addTeammate(User user) {
    	this.teammates.add(user);
    }
    
    public void removeTeammate(User user) {
    	for(int i = 0;i<teammates.size();i++) {
    		if(teammates.get(i) == user) {
    			teammates.remove(i);
    			break;
    		}
    	}
    }
    
    public List<User> getTeammates(){
		return teammates;
    }
}
