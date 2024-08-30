
package ensat.dca.gestionproduct.Controller;

import ensat.dca.gestionproduct.Entity.Orders;
import ensat.dca.gestionproduct.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        if (orderRequest.getProductId() == null || orderRequest.getUserId() == null) {
            return ResponseEntity.badRequest().body("Le productId et le userId doivent être non nuls");
        }
        try {
            Orders order = orderService.createOrder(orderRequest.getProductId(), orderRequest.getQuantity(), orderRequest.getUserId());
            return ResponseEntity.ok("Commande créée avec succès");
        } catch (Exception e) {
            e.printStackTrace(); // Affichez l'exception dans la console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de la commande : " + e.getMessage());
        }
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable Long userId) {
        List<Orders> orders = orderService.getOrdersByUserId(userId);

        return ResponseEntity.ok(orders);}




    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
