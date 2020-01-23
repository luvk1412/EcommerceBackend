package application.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static application.dao.SqlConstants.*;

public class Cart {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    public Cart(){

    }

    public Cart(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
