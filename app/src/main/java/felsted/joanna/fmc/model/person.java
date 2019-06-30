package felsted.joanna.fmc.model;

/**
 * This is the model Person class. It's data members include --
 *     Person ID: Unique identifier for this person (non-empty string)
 *     Descendant: User (Username) to which this person belongs
 *     First Name: Person’s first name (non-empty string)
 *     Last Name: Person’s last name (non-empty string)
 *     Gender: Person’s gender (string: “f” or “m”)
 *     Father: ID of person’s father (possibly null)
 *     Mother: ID of person’s mother (possibly null)
 *     Spouse: ID of person’s spouse (possibly null)
 * Persons can only be constructed when given ALL data members. Because some members are optional
 * pass in an empty string if you do not have that data.
 *
 * Data members can only be accessed through getters and setters
 */
public class person {
    /*
    Person ID: Unique identifier for this person (non-empty string)
    Descendant: User (Username) to which this person belongs
    First Name: Person’s first name (non-empty string)
    Last Name: Person’s last name (non-empty string)
    Gender: Person’s gender (string: “f” or “m”)
    Father: ID of person’s father (possibly null)
    Mother: ID of person’s mother (possibly null)
    Spouse: ID of person’s spouse (possibly null)
    */

    /* NOTE
    to make a new person ID, use
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

     */

    private String personID;
    private String descendant; // username of the user this person belongs to
    private String firstName;
    private String lastName;
    private String gender;
    private String father; //possibly null
    private String mother; //possibly null
    private String spouse; //possibly null

    public person(String personID, String descendant, String firstName, String lastName,
                  String gender, String father, String mother, String spouse) {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
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

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

}
