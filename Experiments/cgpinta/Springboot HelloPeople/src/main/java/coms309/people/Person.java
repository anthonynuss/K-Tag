package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String major;

    private String favfruit;

    public Person(){
        
    }

    public Person(String firstName, String lastName, String major, String favfruit){
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.favfruit = favfruit;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String address) {
        this.major = major;
    }

    public String getFavfruit() {
        return this.favfruit;
    }

    public void setFavfruit(String favfruit) {
        this.favfruit = favfruit;
    }

    @Override
    public String toString() {
        return firstName + " " 
               + lastName + " "
               + major + " "
               + favfruit;
    }
}
