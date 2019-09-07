package felsted.joanna.fmc.model;

import java.io.Serializable;

public class loginRequest implements Serializable {
    private String serverHost;
    private String serverPort;
    private String username;
    private String password;

    public loginRequest(String serverHost, String serverPort, String username, String password) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.username = username;
        this.password = password;
    }

    public loginRequest(){

    }

    public boolean allInfo(){
        return(serverHost != null && serverPort!= null && username != null && password != null);
    }
    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
