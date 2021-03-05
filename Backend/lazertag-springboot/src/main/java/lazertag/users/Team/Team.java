package lazertag.users.Team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lazertag.users.User.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Team {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date creationDate;
    private int wins;
    private int loses;

    @OneToOne
    private User captain;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @ManyToOne
    @JsonIgnore
    private User[] users = new User[32];


    // =============================== Constructors ================================== //


    public Team(String name, User captain, User[] users, Date creationDate) {
        this.name = name;
        this.captain = captain;
        this.users = users;
        this.creationDate = creationDate;
    }

    public Team(String name, User captain, Date creationDate) {
        this.name = name;
        this.captain = captain;
        this.addUser(captain);
        this.creationDate = creationDate;
    }

    public Team(String name, Date creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public Team() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //get-set Team name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //get-set Team captain
    public User getCaptain() {
        return captain;
    }

    public void setCaptain(User captain) {
        this.captain = captain;
    }

    //get-set and add-remove Team members
    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
    //adds a user to the team users list
    public void addUser(User user) {
        User[] nUsers = new User[users.length + 1];
        for (int i = 0; i < users.length; i++) {
            nUsers[i] = users[i];
        }
        nUsers[users.length] = user;
        users = nUsers;
    }
    public void removeUser() {
        User[] nUsers = new User[users.length - 1];
        for (int i = 0; i < nUsers.length; i++) {
            nUsers[i] = users[i];
        }
        users = nUsers;
    }

    //get-set Team creation date
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    //get-set and add-remove Team wins
    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void addWin() {
        this.wins++;
    }

    public void removeWin() {
        this.wins--;
    }

    //get-set and add-remove Team losses
    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public void addLoss() {
        this.loses++;
    }

    public void removeLoss() {
        this.loses--;
    }
}
