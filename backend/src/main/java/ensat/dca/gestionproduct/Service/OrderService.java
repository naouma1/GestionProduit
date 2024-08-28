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
        if (productId == null || userId == null) {
            throw new IllegalArgumentException("Le productId et le userId doivent être non nuls");
        }

        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Produit non trouvé"));

        if (product.getQuantity() < quantity) {
            throw new Exception("Quantité insuffisante en stock");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        // Vérifiez si l'utilisateur est un client
        if (!user.getType().equals("client")) {
            throw new Exception("Vous devez être un client pour passer une commande");
        }

        Orders order = new Orders();
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setDateCommande(LocalDateTime.now());
        order.setStatus("En cours");
        order.setUser(user); // Lien avec l'utilisateur

        // Mise à jour de la quantité du produit
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        return orderRepository.save(order);
    }


    public List<Orders> getAll() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) throws Exception {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Commande non trouvée"));

        // Mise à jour de la quantité du produit lors de la suppression de la commande
        Products product = order.getProduct();
        product.setQuantity(product.getQuantity() + order.getQuantity());
        productRepository.save(product);

        orderRepository.delete(order);
    }

    public void createOrder(OrderRequest orderRequest) {

    }
}