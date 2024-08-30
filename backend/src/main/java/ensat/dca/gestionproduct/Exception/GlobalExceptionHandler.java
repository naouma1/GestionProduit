package ensat.dca.gestionproduct.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        ex.printStackTrace(); // Affichez l'exception dans la console pour débogage
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur : " + ex.getMessage());
    }
}

