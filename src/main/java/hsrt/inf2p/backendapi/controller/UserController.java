package hsrt.inf2p.backendapi.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hsrt.inf2p.backendapi.BackendApplication;
import hsrt.inf2p.backendapi.model.User;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.converter.json.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    // userListe: key= String "HarryPotter" value= Objekt User HarryPotter
    private HashMap<String, User> userListe = new HashMap<>();

//    @Autowired
    public UserController userController;

    public UserController () throws IOException {
        initUsersFromJson(); // initialiseren von Benutzerdaten
    }

    /**
     * Post Construct function to initialize the UserController
     */

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/printJsonUsers")
    public void printJsonUsers() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();


        // wir erzeugen User-Objekte aus der users.json datei
        User[] allUsers = objectMapper.readValue(new FileReader("savefiles/users.json"), User[].class);

        // wir erstellen eine HashMap aus unserer Benutzerliste
        // Key= "Benutzername" Value= User-Objekt
        for(User user : allUsers) {
            userListe.put(user.getUsername(), user);
        }

        // Wir iterieren durch unsere HashMap mit den Benutzern
        for (Map.Entry<String, User> eintrag : userListe.entrySet()) {
            //wir iterieren durch die String Liste mit den Followern eines Benutzers
            for(String follower : eintrag.getValue().getFollowers()) {
                // wir wählen den Benutzer aus der userListe und fügen ihn zum follower-Set hinzu
                eintrag.getValue().addFollower(userListe.get(follower));
                // gleichzeitig fügen wir in das set für following des ausgewählten Benutzers den Gefolgten ein
                userListe.get(follower).addFollowing(eintrag.getValue());
            }
        }

        System.out.println("HarryPotter wird gefolgt von:");
        for (User user : userListe.get("HarryPotter").getFollowersSet()) {
            System.out.println(user.getUsername());
        }
    }


    @PostConstruct
    private void init() {
        // System.out.println("InitSequenceBean: postConstruct");
        // <bean class="InitSequenceBean" init-method="initMethod"></bean>

//        User[] test;
//        ArrayList<User> userList;
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, String> user = new HashMap<>();
//        user.put("username", "RonWeasley");
//        user.put("status", "EAT SLUGS");
//        user.put("profilePicture", "www.picture.com");
//
//        try {
//            // {"username": "RonWeasley", "status": "EAT SLUGS!", "profilePicture": "www.picture.com", "password": "Passwort123"}
//            // "/savefiles/users.json"
//            System.out.println(objectMapper.readValue("{\"username\": \"RonWeasley\", \"status\": \"EAT SLUGS!\" , \"profilePicture\" : \"www.picture.com\", \"password\" : \"Passwort123\"}", User.class));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

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
    public ResponseEntity<?> addUser(@Valid @RequestBody @NotNull User user) {
        // wenn Benutzer bereits vorhanden
        if (userListe.containsKey(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
        System.out.println(user.getUsername() + user.getUsername() + " " + user.getPassword());

        // wir fügen den User zur UserListe (HashMap) hinzu
        userListe.put(user.getUsername(), user);

        // hasht das Passwort und löscht das Klartext Passwort
        user.hashPassword();

        return ResponseEntity.status(HttpStatus.OK).body(user);
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
