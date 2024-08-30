package ensat.dca.gestionproduct.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ensat.dca.gestionproduct.Entity.Users;
import jakarta.persistence.Id;

public class OrderRequest {
    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("userId")
    private Long userId;

    // Getters et Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
