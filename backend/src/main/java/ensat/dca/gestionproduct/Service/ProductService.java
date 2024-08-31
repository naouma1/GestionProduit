package ensat.dca.gestionproduct.Service;

import ensat.dca.gestionproduct.Entity.Products;
import ensat.dca.gestionproduct.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll(); // Assurez-vous que ce retour contient bien les noms de fichiers d'image
    }

    public void updateProductImage(Long productId, String imageUrl) {
        Optional<Products> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            product.setImageUrl(imageUrl);
            productRepository.save(product);
        }
    }

    public Products getProductById(Long id) throws Exception {
        Optional<Products> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new Exception("Product not found");
        }
        return product.get();
    }

    public Products createProduct(Products product) {
        return productRepository.save(product);
    }

    public Products updateProduct(Products product, Long id) throws Exception {
        Optional<Products> existingProduct = productRepository.findById(id);
        if (!existingProduct.isPresent()) {
            throw new Exception("Product not found");
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) throws Exception {
        Optional<Products> existingProduct = productRepository.findById(id);
        if (!existingProduct.isPresent()) {
            throw new Exception("Product not found");
        }
        productRepository.deleteById(id);
    }

    public Optional<Products> findById(Long id) {
        return productRepository.findById(id);
    }
    public Products saveProduct(Products product) {
        return productRepository.save(product);
    }
}

