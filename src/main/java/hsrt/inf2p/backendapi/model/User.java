package hsrt.inf2p.backendapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {

    static final String SALT = "dkjfhs68sdh";

    @NotNull
    private String username;

    @NotNull
    private String status;

    @NotNull
    private String password;

    @NotNull
    private String profilePicture; //TODO find or create better class than "Object"


    //private Set<User> followersSet;

    //private Set<User> followingSet;

    private ArrayList<String> followers;
    private ArrayList <String> following;

    public User () {
        //this.followersSet = new HashSet<>();
        //this.followingSet = new HashSet<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String hashPassword() {
        this.password = String.valueOf((SALT + password).hashCode());
        return this.password;
    }

    public @NotNull String getPassword() {
        return this.password;
    }


    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList <String> getFollowers() {
        return followers;
    }

    //public Set<User> getFollowersSet() {
        //return followersSet;
    //}

    public void setFollowers( ArrayList <String> followers) {
        this.followers=followers;
    }

    public ArrayList <String> getFollowing() {
        return following;
    }

    //public Set<User> getFollowingSet() {
        //return followingSet;
    //}


    public void setFollowing(ArrayList <String> following) {
        this.following = following;
    }
    
    public void addFollower (User u) {
        this.followers.add(u.getUsername());
    }

    public void addFollowing(User u) {
        this.following.add(u.getUsername());
    }

    @Override
    public String toString() {
        return "Benutzername: " + this.username + " - Status: " + this.status + " - Hash-Wert Passwort: " + this.password;
    }
}
