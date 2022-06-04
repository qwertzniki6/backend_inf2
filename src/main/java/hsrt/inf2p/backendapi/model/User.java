package hsrt.inf2p.backendapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User {

    @NotNull
    private String username;

    @NotNull
    private String statusMessage;

    @NotNull
    private String password;

    @NotNull
    private Object profilePicture; //TODO find or create better class than "Object"

    private String[] followersString;
    private String[] followingString;

    private Set<User> followersSet;

    private Set<User> followingSet;

    public User() {
        this.followersSet = new HashSet<>();
        this.followingSet = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public int getPasswordHash() {
        System.out.println("geht in pwhash rein");
        System.out.println( ("45678" + "dkjfhs68sdh").hashCode() );
        System.out.println( ("45678dkjfhs68sdh").hashCode() );
        return this.passwordHash;
    }


    public Object getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Object profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String[] getFollowers() {
        return followersString;
    }

    public Set<User> getFollowersAsSet() {
        return followersSet;
    }

    public void setFollowers(String[] followers) {
        followersString = followers;
    }

    public String[] getFollowing() {
        return followingString;
    }

    public Set<User> getFollowingAsSet() {
        return followingSet;
    }


    public void setFollowing(String[] following) {
        followingString = following;
    }
    
    public void addFollower(User u) {
        this.followersSet.add(u);
    }

    public void addFollowing(User u) {
        this.followingSet.add(u);
    }

    public String toString() {
        return this.username + Arrays.toString(this.followersString) + Arrays.toString(this.followingString);
    }


}
