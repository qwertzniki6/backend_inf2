package hsrt.inf2p.backendapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void hashPassword() {
        this.password = String.valueOf((SALT + password).hashCode());
    }

    public @NotNull String getPassword() {
        return this.password;
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

    public Set<User> getFollowersSet() {
        return followersSet;
    }

    public void setFollowers(String[] followers) {
        this.followers=followers;
    }

    public String[] getFollowing() {
        return following;
    }

    public Set<User> getFollowingSet() {
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
