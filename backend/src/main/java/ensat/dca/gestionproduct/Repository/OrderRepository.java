package ensat.dca.gestionproduct.Repository;

import ensat.dca.gestionproduct.Entity.Orders;
import ensat.dca.gestionproduct.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);
}
