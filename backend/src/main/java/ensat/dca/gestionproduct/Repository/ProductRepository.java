package ensat.dca.gestionproduct.Repository;


import ensat.dca.gestionproduct.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
