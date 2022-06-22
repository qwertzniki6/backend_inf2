package hsrt.inf2p.backendapi.model;

import java.util.Collections;
import java.util.List;

public class Recommendations implements Comparable<Recommendations>{
    private String username;
    private int commonFollowers;

    public Recommendations(String username, int commonFollowers){
        this.username = username;
        this.commonFollowers = commonFollowers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCommonFollowers() {
        return commonFollowers;
    }

    public void setCommonFollowers(int commonFollowers) {
        this.commonFollowers = commonFollowers;
    }

    @Override
    public int compareTo(Recommendations o){
        return Integer.compare(commonFollowers, o.getCommonFollowers());
    }

    @Override
    public String toString(){
        return "Benutzername: " + username + " Anzahl gemeinsamer Follower: " + commonFollowers;
    }
}
