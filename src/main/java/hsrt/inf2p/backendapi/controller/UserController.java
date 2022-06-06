package hsrt.inf2p.backendapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import hsrt.inf2p.backendapi.model.StatusTransferObject;
import hsrt.inf2p.backendapi.model.User;

import hsrt.inf2p.backendapi.model.followersTransferObject;
import hsrt.inf2p.backendapi.model.profilePictureTransferObject;
import org.jetbrains.annotations.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import java.io.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    // userListe: key= String "HarryPotter" value= Objekt User HarryPotter
    private HashMap<String, User> userMap = new HashMap<>();


    public UserController userController;

        // wieder in Json reinschreiben
    public void updateJson () {
        User [] userArray = new User[userMap.size()];
        int i=0;
        for (Map.Entry <String,User> eintrag : userMap.entrySet()) {
            userArray[i] = eintrag.getValue();
            i++;
        }
        ObjectMapper objectMapper = new ObjectMapper();



        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter("savefiles/users.json"), userArray);
        } catch(IOException e){
            System.out.println(e);
        }
    }

    public void printAllUsers () {
        for (Map.Entry<String, User> eintrag : userMap.entrySet()) {
            System.out.println(eintrag.getValue().toString());
        }
    }

    public UserController () throws IOException {
        initUsersFromJson(); // initialiseren von Benutzerdaten
        printAllUsers(); // Benutzerdaten in der Konsole ausgeben
    }

    /**
     * Post Construct function to initialize the UserController
     */

    public void initUsersFromJson() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        // wir erzeugen ein User Array mit User-Objekten aus der users.json datei
        User[] allUsers = objectMapper.readValue(new FileReader("savefiles/users.json"), User[].class);

        //wir erstellen eine HashMap aus unserer Benutzerliste
        //Key= "Benutzername" Value= User-Objekt
        for(User user : allUsers) {
           userMap.put(user.getUsername(), user);
        }
    }

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
        HashSet <User> collection = new HashSet<User>(userMap.values());
        return collection;
    }

    /**
     * 2 Punkte
     * Registers a new user
     *
     * @param user a new user object, including username (unique identifier), statusMessage, and profilePicture; excluding followers and followings
     * @return the created user object for verification
     */

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@Valid @RequestBody @NotNull User user) throws IOException {
        // prüft ob Benutzer bereits vorhanden
        if (userMap.containsKey(user.getUsername())) {
            System.out.println("Benutzer bereits vorhanden");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }

        // wir fügen den User zur UserListe (HashMap) hinzu
        userMap.put(user.getUsername(), user);

        // hasht das Passwort und überschreibt das Klartext Passwort
        user.hashPassword();

        System.out.println("User registriert..." + userMap.get(user.getUsername()).toString() );

        updateJson();

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
        return userMap.get(username);
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
    public User updateProfilePicture(@PathVariable String username, @Valid @RequestBody @NotNull profilePictureTransferObject profilePicture) {
        //TODO find appropriate class for profilePicture and replace 'Object'
        userMap.get(username).setProfilePicture(profilePicture.getprofilePicture());
        updateJson();
        return userMap.get(username);
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
    public User updateStatus(@PathVariable String username, @Valid @RequestBody @NotNull StatusTransferObject status) {
        userMap.get(username).setStatus(status.getStatus());
        updateJson();
        return userMap.get(username);
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
    public List<User> updateFollow(@Valid @RequestBody @NotNull followersTransferObject dataObject) {
        //TODO find appropriate class for follow and replace 'Object'

        // follower user = dataObject.getFollowing()

        //harry folgt Hermoine
        //User A = HarryPotter - follower (er folgt) = username1
        // User B = HermoineGranger - following (ihr wird gefolgt) = username2
        // Adde HermoineGranegr in Harrys Following Liste
        // Adde harry zu Hermoines Follower
        String follower = dataObject.getUsername1();
        String following = dataObject.getUsername2();

        userMap.get(follower).addFollowing(following);
        userMap.get(following).addFollower(follower);

        updateJson();

        List <User> returningUsers = new ArrayList<>();
        returningUsers.add(userMap.get(follower));
        returningUsers.add(userMap.get(following));

        return returningUsers;
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
    public List<User> deleteFollow(@Valid @RequestBody @NotNull followersTransferObject data) {
        //TODO find appropriate class for follow and replace 'Object'
        String follower = data.getUsername1();
        String following = data.getUsername2();
        if (userMap.get(follower).removeFollowing(following)==false) {
            System.out.println("entfernen des gefolgten " + following + "aus following von" + follower + "hat nicht geklappt!");
        }
        if(userMap.get(following).removeFollower(follower)==false){
            System.out.println("");
        }

        updateJson();

        List <User> returningUsers = new ArrayList<>();
        returningUsers.add(userMap.get(follower));
        returningUsers.add(userMap.get(following));

        return returningUsers;
    }

}
