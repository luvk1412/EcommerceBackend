package application.dao;

import application.model.Cart;
import application.model.Product;
import application.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import static application.dao.SqlConstants.*;

public class Mappers {
    public static MapSqlParameterSource getUserMap(User user){
        return new MapSqlParameterSource(COLUMN_USER_ID, user.getId())
                .addValue(COLUMN_USER_NAME, user.getName())
                .addValue(COLUMN_USER_PASSWORD,user.getPassword())
                .addValue(COLUMN_USER_EMAIL,user.getEmail())
                .addValue(COLUMN_USER_ADDRESS, user.getAddress())
                .addValue(COLUMN_USER_PHONE, user.getPhone());
    }
    public static MapSqlParameterSource getUserMap(int id){
        return new MapSqlParameterSource(COLUMN_USER_ID, id);
    }

    public static MapSqlParameterSource getProductMap(Product product){
        return new MapSqlParameterSource(COLUMN_PRODUCT_ID, product.getId())
                .addValue(COLUMN_PRODUCT_NAME, product.getName())
                .addValue(COLUMN_PRODUCT_CATEGORY,product.getCategory())
                .addValue(COLUMN_PRODUCT_CURRENCY,product.getCurrency())
                .addValue(COLUMN_PRODUCT_DESCRIPTION, product.getDescription())
                .addValue(COLUMN_PRODUCT_PRICE, product.getPrice())
                .addValue(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
    }
    public static MapSqlParameterSource getProductMap(int id){
        return new MapSqlParameterSource(COLUMN_PRODUCT_ID, id);
    }
    public static MapSqlParameterSource getProductMap(int id, int quantity){
        return new MapSqlParameterSource(COLUMN_PRODUCT_ID, id)
                .addValue(COLUMN_PRODUCT_QUANTITY, quantity);
    }
    public static MapSqlParameterSource getProductMap(String filter, String prefix){
        return new MapSqlParameterSource(COLUMN_PRODUCT_CATEGORY, filter)
                .addValue(COLUMN_PRODUCT_NAME, prefix);
    }
    public static MapSqlParameterSource getProductMap(String prefix){
        return new MapSqlParameterSource(COLUMN_PRODUCT_NAME, prefix);
    }

    public static MapSqlParameterSource getCartMap(Cart cart){
        return new MapSqlParameterSource(COLUMN_CART_ID, cart.getId())
                .addValue(COLUMN_CART_USER_ID, cart.getUserId())
                .addValue(COLUMN_CART_PRODUCT_ID, cart.getProductId())
                .addValue(COLUMN_CART_QUANTITY, cart.getQuantity());
    }

    public static MapSqlParameterSource getCartMap(int id){
        return new MapSqlParameterSource(COLUMN_CART_ID, id);
    }
    public static MapSqlParameterSource getCartMapUserId(int userId){
        return new MapSqlParameterSource(COLUMN_CART_USER_ID, userId);
    }


}
