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

    private int organizerId;

    private String gameDescription;

    private int team1Id;

    private int team2Id;

    /**
     * creates a new event
     * @param name - name of the event
     * @param location - location of the event
     * @param time - time the event will take place
     * @param organizerId - user who create the evetn
     */
    public Event(String name, String location, String time, int organizerId){
        this.name = name;
        this.location = location;
        this.time = time;
        this.organizerId = organizerId;
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
     * gets the event organizerId
     * @return organizerId - user who created the event
     */
    public int getOrganizer() {return organizerId;}

    /**
     * changes the event organizer
     * @param organizer
     */
    public void changeOrganizer(int organizerId) {this.organizerId = organizerId;}

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
    public int getTeam1Id() {return team1Id;}

    /**
     * changes team1 of the event
     * @param team1Id
     */
    public void changeTeam1(int team1Id) {this.team1Id = team1Id;}

    /**
     * gets team2 of the event
     * @return
     */
    public int getTeam2Id() {return team2Id;}

    /**
     * changes team2 of the event
     * @param team2Id
     */
    public void changeTeam2(int team2Id) {this.team2Id = team2Id;}

}
