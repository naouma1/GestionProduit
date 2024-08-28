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
        if (user.isEmpty()) {
            throw new Exception("Les informations de l'utilisateur ne peuvent pas être vides");
        }

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
        // Vous pouvez ajouter ici une logique de déconnexion spécifique si nécessaire
        // Par exemple, invalider des tokens ou des sessions si vous utilisez des sessions côté serveur
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
