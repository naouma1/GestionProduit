package ensat.dca.gestionproduct.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType type; // Ajout du type d'utilisateur



    public enum UserType {
        CLIENT,
        VENDEUR;
    }

    public boolean isEmpty() {
        return (username == null || username.isEmpty()) &&
                (password == null || password.isEmpty()) &&
                (email == null || email.isEmpty()) &&
                (phone == null || phone.isEmpty()) &&
                type == null;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Username cannot be empty") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username cannot be empty") String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Password cannot be empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password cannot be empty") String password) {
        this.password = password;
    }

    public @NotEmpty(message = "Email cannot be empty") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email cannot be empty") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}



