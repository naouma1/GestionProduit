package ensat.dca.gestionproduct.Controller;

import ensat.dca.gestionproduct.Entity.Products;
import ensat.dca.gestionproduct.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    private final String uploadDir = "src/main/resources/static/images/"; // Répertoire de stockage des images
    private final String imageUrlPrefix = "/images/"; // URL pour accéder aux images

    @PostMapping("/add")
    public ResponseEntity<Products> createProduct(@RequestBody Products product) throws Exception {
        Products savedProduct = productService.createProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productService.getAllProducts();

        // Vérifiez et ajoutez l'URL de l'image pour chaque produit
        products.forEach(product -> {
            if (product.getImageFileName() != null && !product.getImageFileName().isEmpty()) {
                product.setImageUrl(imageUrlPrefix + product.getImageFileName());
            } else {
                product.setImageUrl(null); // ou ajoutez une URL d'image par défaut
            }
        });

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        try {
            Products product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/images", fileName);

        try {
            Files.write(filePath, file.getBytes());
            String imageUrl = "/images/" + fileName;
            productService.updateProductImage(id, imageUrl);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Could not save image file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Products> updateProduct(@RequestBody Products product, @PathVariable Long id) throws Exception {
        Products updatedProduct = productService.updateProduct(product, id);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws Exception {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws Exception {
        Products product = productService.getProductById(id);

        // Vérifiez et créez le répertoire de stockage s'il n'existe pas
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Sauvegarde du fichier
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new Exception("Could not save image file");
        }

        // Mettre à jour l'URL de l'image
        String imageUrl = "/uploads/" + fileName;
        product.setImageUrl(imageUrl);
        productService.updateProduct(product, id);

        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }

}
