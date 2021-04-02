package lazertag.users.Team;

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

    //private ArrayList<User> friends = new ArrayList<User>();
    
    
    @OneToMany
    private List<User> teammates;
    
    /*
    @ManyToMany
    @JoinTable(name="friends",
     joinColumns=@JoinColumn(name="friend"),
     inverseJoinColumns=@JoinColumn(name="person")
    )
    private List<Team> friendOf;
    */
    

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
}
