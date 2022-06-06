package hsrt.inf2p.backendapi.model;

public class FollowersTransferObject {

    private String username1;
    private String username2;

    public FollowersTransferObject(){
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }
}