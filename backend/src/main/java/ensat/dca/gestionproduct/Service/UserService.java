package ensat.dca.gestionproduct.Service;


import ensat.dca.gestionproduct.Entity.Users;
import ensat.dca.gestionproduct.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    public Users createUser(Users user) throws Exception {
        // Vérifier si l'utilisateur a rempli tous les champs requis
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new Exception("Le nom d'utilisateur ne peut pas être vide");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new Exception("L'email ne peut pas être vide");
        }
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            throw new Exception("Le numéro de téléphone ne peut pas être vide");
        }

        // Vérification des doublons
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Nom d'utilisateur déjà utilisé");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email déjà utilisé");
        }
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new Exception("Numéro de téléphone déjà utilisé");
        }

        return userRepository.save(user);
    }



    public Optional<Users> login(String username, String password) {
        Optional<Users> userExist = userRepository.findByUsername(username);

        if (userExist.isPresent()) {
            Users user = userExist.get();
            if (password.equals(user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    public void logout() {

    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
