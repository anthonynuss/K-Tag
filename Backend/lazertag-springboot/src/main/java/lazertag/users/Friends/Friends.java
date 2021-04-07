package lazertag.users.Friends;

import javax.persistence.*;
import lazertag.users.User.User;

@Entity
public class Friends {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
	
    private int person;
    private int friend;
    public Friends(User one, User two) {
    	person = one.getId();
    	friend = two.getId();
    }
    
    public Friends() {}
    
    // =============================== Getters and Setters for each field ================================== //

    /*
     * gets the user's ID
     */
    public int getId(){
        return id;
    }
    
    public int getPerson(){
        return person;
    }
    
    public int getFriend(){
        return friend;
    }
}
