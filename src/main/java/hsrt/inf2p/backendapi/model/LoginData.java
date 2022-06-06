package hsrt.inf2p.backendapi.model;

public class LoginData {
    private String password, username;

    public LoginData () {}

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
