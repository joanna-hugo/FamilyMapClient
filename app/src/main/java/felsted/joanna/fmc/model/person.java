package felsted.joanna.fmc.model;

import java.io.Serializable;

/**
 * This is the model Person class. It's data members include --
 *     Person ID: Unique identifier for this person (non-empty string)
 *     Descendant: User (Username) to which this person belongs
 *     First Name: Person’s first name (non-empty string)
 *     Last Name: Person’s last name (non-empty string)
 *     Gender: Person’s gender (string: “f” or “m”)
 *     Father: ID of person’s fatherID (possibly null)
 *     Mother: ID of person’s motherID (possibly null)
 *     Spouse: ID of person’s spouseID (possibly null)
 * Persons can only be constructed when given ALL data members. Because some members are optional
 * pass in an empty string if you do not have that data.
 *
 * Data members can only be accessed through getters and setters
 */
public class person  implements Serializable {
    /*
    Person ID: Unique identifier for this person (non-empty string)
    Descendant: User (Username) to which this person belongs
    First Name: Person’s first name (non-empty string)
    Last Name: Person’s last name (non-empty string)
    Gender: Person’s gender (string: “f” or “m”)
    Father: ID of person’s fatherID (possibly null)
    Mother: ID of person’s motherID (possibly null)
    Spouse: ID of person’s spouseID (possibly null)
    */

    /* NOTE
    to make a new person ID, use
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

     */

    private String personID;
    private String associatedUsername; // username of the user this person belongs to
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID; //possibly null
    private String motherID; //possibly null
    private String spouseID; //possibly null

    public person(String personID, String associatedUsername, String firstName, String lastName,
                  String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }
    public person(){
    }


    //Getters and Setters----------------------------------------------------------------------------------------------
    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

}
