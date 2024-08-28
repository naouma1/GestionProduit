package ensat.dca.gestionproduct.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("userId")
    private Long userId;

    // Getters et Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
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
