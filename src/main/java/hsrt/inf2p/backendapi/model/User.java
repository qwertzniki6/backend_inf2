package hsrt.inf2p.backendapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
    private Object profilePicture; //TODO find or create better class than "Object"

    private Set<User> followers;

    private Set<User> following;

    public User() {
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }

}
