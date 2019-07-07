package felsted.joanna.fmc.model;

public class loginResponse {
    private String authToken;
    private String username;
    private String personID;

    /*
    {
        "authToken": "cf7a368f", // Non-empty auth token string
        "userName": "susan", // User name passed in with request
        "personID": "39f9fe46" // Non-empty string containing the Person ID of the
        // user’s generated Person object
    }
     */
    public String jsonify(){
        return "{" +
                " \"authToken\" : \"" + authToken + "\", " +
                " \"userName\" : \"" + username + "\","+
                " \"personID\" : \"" + personID + "\"" +
                "}";
    }

    public loginResponse(String auth_token, String username, String personID) {
        this.authToken = auth_token;
        this.username = username;
        this.personID = personID;
    }

    public loginResponse(){

    }
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String auth_token) {
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

}
