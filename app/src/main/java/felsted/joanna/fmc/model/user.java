package felsted.joanna.fmc.model;

public class user {
    private String authToken;
    private String username;
    private String personID;
    private String firstName;
    private String lastName;

    private static user instance = null;

    //THIS IS A SINGLETON
    // there should NEVER be more than one user at a time
    private user(){

    }

    public static user getInstance(){
        if (instance == null)
            instance = new user();
        return instance;
    }

    public void logout(){
        authToken = null;
        username = null;
        personID = null;
        firstName = null;
        lastName = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public static void setInstance(user instance) {
        user.instance = instance;
    }
}
