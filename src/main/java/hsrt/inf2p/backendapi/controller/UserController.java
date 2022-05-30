package hsrt.inf2p.backendapi.controller;

import hsrt.inf2p.backendapi.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    /**
     * Post Construct function to initialize the UserController
     */
    @PostConstruct
    private void init() {

    }


    /**
     * 1 Punkt
     * Gets all users
     *
     * @return a collection containing all users, or an empty collection if no users exist
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    public Collection<User> getAllUsers() {
        return null;
    }

    /**
     * 2 Punkte
     * Registers a new user
     *
     * @param user a new user object, including username (unique identifier), statusMessage, and profilePicture; excluding followers and followings
     * @return the created user object for verification
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    public User addUser(@Valid @RequestBody @NotNull User user) {
        return null;
    }

    /**
     * 1 Punkt
     * Gets a specific user. May also be used for in place of a proper log-in function
     *
     * @param username the user's username (unique identifier)
     * @return the user, including usernames (and nothing more!) of followers and followings
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{username}")
    public User getUser(@PathVariable(name = "username") String username) {
        return null;
    }

    /**
     * 2 Punkte
     * Changes the user's profile picture
     *
     * @param username       the user's username (unique identifier)
     * @param profilePicture the new profile picture
     * @return the updated user object for verification
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/user/{username}/profilePicture")
    public User updateProfilePicture(@PathVariable String username, @Valid @RequestBody @NotNull Object profilePicture) {
        //TODO find appropriate class for profilePicture and replace 'Object'
        return null;
    }

    /**
     * 2 Punkte
     * Changes the user's status message
     *
     * @param username the user's username (unique identifier)
     * @param status   the new status message
     * @return the updated user object for verification
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/user/{username}/status")
    public User updateStatus(@PathVariable String username, @Valid @RequestBody @NotNull Object status) {
        //TODO find appropriate class for status and replace 'Object'
        return null;
    }

    /**
     * 2 Punkte
     * Creates a new following. Followings created are one-directional only!
     * Say User A follows User B:
     * - adds User B to User A's followings
     * - adds User A to User B's followers
     * - nothing more!
     *
     * @param follow two users, one following an other
     * @return The two users including updated followers/followings (as sets of usernames only)
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/follow")
    public List<User> updateFollow(@Valid @RequestBody @NotNull Object follow) {
        //TODO find appropriate class for follow and replace 'Object'
        return null;
    }

    /**
     * 2 Punkte
     * Deletes an existing following. Followings are deleted one-directional only!
     * Say User A unfollows User B:
     * - removes User B from User A's followings
     * - removes User A from User B's followers
     * - nothing more!
     *
     * @param follow two users, one unfollowing an other
     * @return The two users including updated followers/followings (as sets of usernames only)
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/follow")
    public List<User> deleteFollow(@Valid @RequestBody @NotNull Object follow) {
        //TODO find appropriate class for follow and replace 'Object'
        return null;
    }

}
