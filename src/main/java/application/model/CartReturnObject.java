package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import static application.dao.SqlConstants.*;

public class CartReturnObject {
    private Integer cartId;
    private Integer userId;
    Product product;
    public CartReturnObject(){

    }
    public CartReturnObject(ResultSet resultSet) throws SQLException {
        this.cartId = resultSet.getInt(COLUMN_CART_ID);
        this.userId = resultSet.getInt(COLUMN_CART_USER_ID);
        this.product = new Product(resultSet.getInt(COLUMN_PRODUCT_ID)
                ,resultSet.getString(COLUMN_PRODUCT_NAME)
                ,resultSet.getString(COLUMN_PRODUCT_CATEGORY)
                ,resultSet.getString(COLUMN_PRODUCT_DESCRIPTION)
                ,resultSet.getInt(COLUMN_PRODUCT_PRICE)
                ,resultSet.getString(COLUMN_PRODUCT_CURRENCY)
                ,resultSet.getInt(COLUMN_PRODUCT_QUANTITY));
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
