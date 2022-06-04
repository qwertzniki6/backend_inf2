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


    private Set<User> followersSet;

    private Set<User> followingSet;

    private String[] followers;
    private String[] following;


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
        return this.passwordHash;
    }


    public Object getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Object profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String[] getFollowers() {
        return followers;
    }

    public Set<User> getFollowersAsSet() {
        return followersSet;
    }

    public void setFollowers(String[] followers) {
        this.followers=followers;
    }

    public String[] getFollowing() {
        return following;
    }

    public Set<User> getFollowingAsSet() {
        return followingSet;
    }


    public void setFollowing(String[] following) {
        this.following = following;
    }
    
    public void addFollower(User u) {
        this.followersSet.add(u);
    }

    public void addFollowing(User u) {
        this.followingSet.add(u);
    }

    public String toString() {
        return "string";
    }


}
