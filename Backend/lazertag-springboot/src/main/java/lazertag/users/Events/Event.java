package lazertag.users.Events;

import javax.persistence.*;

import lazertag.users.Team.Team;
import lazertag.users.User.User;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String location;

    private String time;    //TODO change to date datatype later

    @OneToOne(mappedBy = "organizer")
    private User organizer;

    private String gameDescription;

    @OneToOne(mappedBy = "team1")
    private Team team1;

    @OneToOne(mappedBy = "team2")
    private Team team2;

    /**
     * creates a new event
     * @param name - name of the event
     * @param location - location of the event
     * @param time - time the event will take place
     * @param organizer - user who create the evetn
     */
    public Event(String name, String location, String time, User organizer){
        this.name = name;
        this.location = location;
        this.time = time;
        this.organizer = organizer;
    }

    /**
     * gets the id number of the event
     * @return id
     */
    public int getId() {return id;}

    /**
     * changes the events id - try not to use, it could mess with other data in the database
     * @param id - new event id
     */
    public void setId(int id) {this.id = id;}

    /**
     * returns the event name
     * @return name - name of the event
     */
    public String getName() {return name;}

    /**
     * changes the event name
     * @param name - new name of the event
     */
    public void changeName(String name) {this.name = name;}

    /**
     * gets the location of the event
     * @return location - location of the event
     */
    public String getLocation() {return location;}

    /**
     * changes the location of the event
     * @param location - new location of the event
     */
    public void changeLocation(String location) {this.location = location;}

    /**
     * gets the set time for the event
     * @return time - time of the event
     */
    public String getTime() {return time;}

    /**
     * changes the time the event will take place
     * @param time - new time of the event
     */
    public void changeTime(String time) {this.time = time;}

    /**
     * gets the event organizer
     * @return organizer - user who created the event
     */
    public User getOrganizer() {return organizer;}

    /**
     * changes the event organizer
     * @param organizer
     */
    public void changeOrganizer(User organizer) {this.organizer = organizer;}

    /**
     * gets the game description
     * @return game description
     */
    public String getGameDescription() {return gameDescription;}

    /**
     * changes game description
     * @param gameDescription
     */
    public void changeGameDescription(String gameDescription) {this.gameDescription = gameDescription;}

    /**
     * gets team1 of the event
     * @return
     */
    public Team getTeam1() {return team1;}

    /**
     * changes team1 of the event
     * @param team1
     */
    public void changeTeam1(Team team1) {this.team1 = team1;}

    /**
     * gets team2 of the event
     * @return
     */
    public Team getTeam2() {return team2;}

    /**
     * changes team2 of the event
     * @param team2
     */
    public void changeTeam2(Team team2) {this.team2 = team2;}

}
