package ensat.dca.gestionproduct.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "commandes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "date_commande", nullable = false)
    private LocalDateTime dateCommande;

    @Column(name = "status", nullable = false)
    private String status;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // Assurez-vous que l'entité User est définie
}
