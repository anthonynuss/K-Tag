package lazertag.users.Event;

import javax.persistence.*;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //generate id for event to be identified with

    //name of the event
    private String name;

    //physical location of lazertag game
    private String location;

    //date and time of event, could be split into date and time data types
    private String time;    //TODO change to date datatype later

    //points to userId
    private int organizerId;

    //general description of the event, set by organizer
    private String gameDescription;

    //name of first team in event
    private String team1;

    //name of second team in event
    private String team2;

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
     * @param organizerId
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
    public String getTeam1() {return team1;}

    /**
     * changes team1 of the event
     * @param team1
     */
    public void changeTeam1(String team1) {this.team1 = team1;}

    /**
     * gets team2 of the event
     * @return
     */
    public String getTeam2() {return team2;}

    /**
     * changes team2 of the event
     * @param team2
     */
    public void changeTeam2(String team2) {this.team2 = team2;}

}
