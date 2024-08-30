package ensat.dca.gestionproduct.Service;

import ensat.dca.gestionproduct.Controller.OrderRequest;
import ensat.dca.gestionproduct.Entity.Orders;
import ensat.dca.gestionproduct.Entity.Products;
import ensat.dca.gestionproduct.Entity.Users; // Assurez-vous que l'entité User est importée
import ensat.dca.gestionproduct.Repository.OrderRepository;
import ensat.dca.gestionproduct.Repository.ProductRepository;
import ensat.dca.gestionproduct.Repository.UserRepository; // Assurez-vous que le repository User est importé
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;



    public Orders createOrder(Long productId, Integer quantity, Long userId) throws Exception {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    return new Exception("Utilisateur non trouvé");
                });

        System.out.println("Recherche du produit avec ID: " + productId);
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    System.out.println("Produit non trouvé pour l'ID: " + productId);//Debug
                    return new Exception("Produit non trouvé");
                });

        System.out.println("Produit trouvé: " + product);

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0");
        }

        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("La quantité demandée dépasse la quantité en stock");
        }

        Orders order = new Orders();
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setDateCommande(LocalDateTime.now());
        order.setUser(user);

        int updatedQuantity = product.getQuantity() - quantity;
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("La quantité demandée dépasse la quantité en stock");
        }
        product.setQuantity(updatedQuantity);
        productRepository.save(product);

        // Sauvegarder la commande
        Orders savedOrder = orderRepository.save(order);

        System.out.println("Commande créée avec succès: " + savedOrder);

        return savedOrder;
    }


    public List<Orders> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void deleteOrder(Long id) throws Exception {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Commande introuvable"));

        Products product = order.getProduct();
        product.setQuantity(product.getQuantity() + order.getQuantity());
        productRepository.save(product);

        orderRepository.delete(order);
    }

    public void createOrder(OrderRequest orderRequest) {

    }
}