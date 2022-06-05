package hsrt.inf2p.backendapi.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import hsrt.inf2p.backendapi.model.User;

import org.jetbrains.annotations.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import java.io.*;

import java.nio.file.Paths;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    // userListe: key= String "HarryPotter" value= Objekt User HarryPotter
    private HashMap<String, User> userMap = new HashMap<>();


    public UserController userController;

    public UserController () throws IOException {
        initUsersFromJson(); // initialiseren von Benutzerdaten

//        ObjectMapper objectMapper = new ObjectMapper();
//        User test = userMap.get("HarryPotter");
        //JsonNode jsonNode = objectMapper.readTree(Paths.get("savefiles/users.json/").toFile()).get(0);
        //JsonNode harrypotter = jsonNode.get(9).get("username");
        //ObjectNode einnode = ( ((ObjectNode)jsonNode).putObject("adress"));
                //einnode.put("abc","abc");
        //objectMapper.writeValue(, test);

//        JsonFactory factory = new JsonFactory();
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Paths.get("savefiles/users.json/").toFile() , true)));
//        objectMapper.writeValue(out, test);

//        JsonGenerator generator = factory.createGenerator()
//        generator.writeStartArray();
//        generator.writeStartObject();
//        generator.writeObjectField("id", 101);
//        generator.writeObjectField("name", "Praj");
//        generator.writeEndObject();
//        generator.writeEndArray();
//        generator.close();
    }

    /**
     * Post Construct function to initialize the UserController
     */

    public void initUsersFromJson() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        // wir erzeugen ein User Array mit User-Objekten aus der users.json datei
        User[] allUsers = objectMapper.readValue(new FileReader("savefiles/users.json"), User[].class);

        // wir erstellen eine HashMap aus unserer Benutzerliste
        // Key= "Benutzername" Value= User-Objekt
        for(User user : allUsers) {
            userMap.put(user.getUsername(), user);
        }

        // Wir iterieren durch unsere HashMap mit den Benutzern
        for (Map.Entry<String, User> eintrag : userMap.entrySet()) {


            //wir iterieren durch die String Liste mit den Followern eines Benutzers
            for(String follower : eintrag.getValue().getFollowers()) {

                // wir wählen den Benutzer aus der userListe und fügen ihn zum follower-Set hinzu
                eintrag.getValue().addFollower(userMap.get(follower));
                // gleichzeitig fügen wir in das set für following des ausgewählten Benutzers den Gefolgten ein
                userMap.get(follower).addFollowing(eintrag.getValue());
            }
        }

        //sout Ausgabe ---
        System.out.println("Hallo");
        System.out.println(userMap.get("HarryPotter").getUsername());
        System.out.println("folgt folgenden Leuten:");
        for (User u: userMap.get("HarryPotter").getFollowersSet()) {
            System.out.println(u.getUsername());
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

        System.out.println("User registriert... username:" + user.getUsername() + " - status:" + user.getStatus() + " - Passwort-Hash: " + user.getPassword());

        //objectMapper um Benutzerdaten in json file zu speichern
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        JsonNode jsonNode = objectMapper.readTree(Paths.get("savefiles/users.json/").toFile());
//        System.out.println(jsonNode.get(0));

        //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Paths.get("savefiles/users.json/").toFile(), true)));
        // konvertiere Benutzer in json
        //objectMapper.writeValue(out, user);

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
