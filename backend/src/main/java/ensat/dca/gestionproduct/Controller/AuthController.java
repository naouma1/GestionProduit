package ensat.dca.gestionproduct.Controller;

import ensat.dca.gestionproduct.Entity.Users;
import ensat.dca.gestionproduct.Repository.UserRepository;
import ensat.dca.gestionproduct.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        try {
            // Vérifier si l'utilisateur a rempli tous les champs requis
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le nom d'utilisateur ne peut pas être vide");
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("L'email ne peut pas être vide");
            }
            if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le numéro de téléphone ne peut pas être vide");
            }

            Users newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users user) {
        // Check if the user can be authenticated
        Optional<Users> loggedInUser = userService.login(user.getUsername(), user.getPassword());

        // Prepare the response body
        Map<String, Object> responseBody = new HashMap<>();
        if (loggedInUser.isPresent()) {
            responseBody.put("status", "success");
            responseBody.put("user", loggedInUser.get());
            return ResponseEntity.ok(responseBody); // HTTP 200 OK
        } else {
            responseBody.put("status", "error");
            responseBody.put("message", "Nom d'utilisateur ou mot de passe incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody); // HTTP 401 Unauthorized
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            userService.logout(); // Appeler la méthode de déconnexion dans le service si nécessaire
            return ResponseEntity.ok("Déconnexion réussie !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la déconnexion.");
        }
    }

}
