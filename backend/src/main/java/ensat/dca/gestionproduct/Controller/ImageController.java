package ensat.dca.gestionproduct.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final Path imageLocation = Paths.get("src/main/resources/static/images");

    @GetMapping("/{filename:.+}")
    public Resource getImage(@PathVariable String filename) {
        try {
            Path file = imageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Image not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Image not found", e);
        }
    }
}

