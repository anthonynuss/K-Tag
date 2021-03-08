package lazertag.users.User;

import java.util.Date;

import javax.persistence.*;

//import lazertag.users.Team.Team;

@Entity
public class User {

    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private Date joiningDate;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User), the cascade option tells springboot
     * to create the child entity if not present already (in this case it is laptop)
     * @JoinColumn specifies the ownership of the key i.e. The User table will contain a foreign key from the laptop table and the column name will be laptop_id
     */
    /*team things
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
    }*/

    public User(String name, String password, Date joiningDate) {
        this.name = name;
        this.password = password;
        this.joiningDate = joiningDate;
    }

    public User() {

    }

    
    // =============================== Getters and Setters for each field ================================== //


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    //get-set user name
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    //get-set user password
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    //get-set user join date
    public Date getJoiningDate(){
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate){
        this.joiningDate = joiningDate;
    }

    //get-set user's team
    /* team things
    public Team getTeam(){
        return team;
    }

    public void setTeam(Team team){
        this.team = team;
    }*/
}
