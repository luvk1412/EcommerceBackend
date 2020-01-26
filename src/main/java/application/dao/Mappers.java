package application.dao;

import application.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public static MapSqlParameterSource getUserMap(UserUpdateObject userUpdateObject){
        return new MapSqlParameterSource(COLUMN_USER_ID, userUpdateObject.getId())
                .addValue(COLUMN_USER_NAME, userUpdateObject.getName())
                .addValue(COLUMN_USER_PASSWORD,userUpdateObject.getPassword())
                .addValue(COLUMN_USER_EMAIL,userUpdateObject.getEmail())
                .addValue(COLUMN_USER_ADDRESS, userUpdateObject.getAddress())
                .addValue(COLUMN_USER_PHONE, userUpdateObject.getPhone());
    }
    public static MapSqlParameterSource getUserMap(int id){
        return new MapSqlParameterSource(COLUMN_USER_ID, id);
    }
    public static MapSqlParameterSource getUserMap(String email){
        return new MapSqlParameterSource(COLUMN_USER_EMAIL, email);
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
    public static MapSqlParameterSource getProductMap(ProductUpdateObject product){
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
    public static MapSqlParameterSource getProductMap(String filter, String prefix, int limit, int offset){
        return new MapSqlParameterSource(COLUMN_PRODUCT_CATEGORY, filter)
                .addValue(COLUMN_PRODUCT_NAME, prefix)
                .addValue(SQL_PAGE_LIMIT, limit)
                .addValue(SQL_PAGE_OFFSET, offset);
    }
    public static MapSqlParameterSource getProductMap(String prefix, int limit, int offset){
        return new MapSqlParameterSource(COLUMN_PRODUCT_NAME, prefix)
                .addValue(SQL_PAGE_LIMIT, limit)
                .addValue(SQL_PAGE_OFFSET, offset);
    }

    public static MapSqlParameterSource getCartMap(Cart cart){
        return new MapSqlParameterSource(COLUMN_CART_USER_ID, cart.getUserId())
                .addValue(COLUMN_CART_PRODUCT_ID, cart.getProductId())
                .addValue(COLUMN_CART_QUANTITY, cart.getQuantity());
    }

    public static MapSqlParameterSource getCartMap(int userId, int productId, int quantity){
        return new MapSqlParameterSource(COLUMN_CART_USER_ID, userId)
                .addValue(COLUMN_CART_PRODUCT_ID, productId)
                .addValue(COLUMN_CART_QUANTITY, quantity);
    }

    public static MapSqlParameterSource getCartMap(int userId, int productId){
        return new MapSqlParameterSource(COLUMN_CART_USER_ID, userId)
                .addValue(COLUMN_CART_PRODUCT_ID, productId);
    }

    public static MapSqlParameterSource getCartMapUserId(int userId){
        return new MapSqlParameterSource(COLUMN_CART_USER_ID, userId);
    }

    public static MapSqlParameterSource getOrderMap(Order order) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order.getOrderProductDescriptions());
        return new MapSqlParameterSource(COLUMN_ORDER_USER_ID, order.getUserId())
                .addValue(COLUMN_ORDER_AMOUNT, order.getAmount())
                .addValue(COLUMN_ORDER_CURRENCY, order.getCurrency())
                .addValue(COLUMN_ORDER_DESCRIPTION, json);
    }

    public static MapSqlParameterSource getOrderMapUserId(int userId){
        return new MapSqlParameterSource(COLUMN_ORDER_USER_ID, userId);
    }

    public static MapSqlParameterSource getOrderMapOrderId(int orderId){
        return new MapSqlParameterSource(COLUMN_ORDER_ID, orderId);
    }


    public static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(COLUMN_USER_ID));
        user.setPassword(resultSet.getString(COLUMN_USER_PASSWORD));
        user.setName(resultSet.getString(COLUMN_USER_NAME));
        user.setEmail(resultSet.getString(COLUMN_USER_EMAIL));
        user.setAddress(resultSet.getString(COLUMN_USER_ADDRESS));
        user.setPhone(resultSet.getLong(COLUMN_USER_PHONE));
        return user;
    }

    public static Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt(COLUMN_PRODUCT_ID));
        product.setName(resultSet.getString(COLUMN_PRODUCT_NAME));
        product.setCategory(resultSet.getString(COLUMN_PRODUCT_CATEGORY));
        product.setDescription(resultSet.getString(COLUMN_PRODUCT_DESCRIPTION));
        product.setPrice(resultSet.getInt(COLUMN_PRODUCT_PRICE));
        product.setCurrency(resultSet.getString(COLUMN_PRODUCT_CURRENCY));
        product.setQuantity(resultSet.getInt(COLUMN_PRODUCT_QUANTITY));
        return product;
    }

    public static Cart getCartFromResultSet(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setUserId(resultSet.getInt(COLUMN_CART_USER_ID));
        cart.setProductId(resultSet.getInt(COLUMN_CART_PRODUCT_ID));
        cart.setQuantity(resultSet.getInt(COLUMN_CART_QUANTITY));
        return cart;
    }

    public static CartReturnObject getCartReturnObjectFromResultSet(ResultSet resultSet) throws SQLException{
        CartReturnObject cartReturnObject = new CartReturnObject();
        cartReturnObject.setUserId(resultSet.getInt(COLUMN_CART_USER_ID));
        cartReturnObject.setProduct(new Product(resultSet.getInt(COLUMN_CART_PRODUCT_ID)
                ,resultSet.getString(COLUMN_PRODUCT_NAME)
                ,resultSet.getString(COLUMN_PRODUCT_CATEGORY)
                ,resultSet.getString(COLUMN_PRODUCT_DESCRIPTION)
                ,resultSet.getInt(COLUMN_PRODUCT_PRICE)
                ,resultSet.getString(COLUMN_PRODUCT_CURRENCY)
                ,resultSet.getInt(COLUMN_PRODUCT_QUANTITY)));
        return cartReturnObject;
    }

    public static Order getOrderFromResultSet(ResultSet resultSet) throws SQLException, JsonProcessingException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt(COLUMN_ORDER_ID));
        order.setUserId(resultSet.getInt(COLUMN_ORDER_USER_ID));
        order.setAmount(resultSet.getInt(COLUMN_ORDER_AMOUNT));
        order.setCurrency(resultSet.getString(COLUMN_ORDER_CURRENCY));
        order.setDateCreated(resultSet.getTimestamp(COLUMN_ORDER_DATE_CREATED));
        ObjectMapper objectMapper = new ObjectMapper();

        List<OrderProductDescription> orderProductDescriptions = objectMapper.readValue(resultSet.getString(COLUMN_ORDER_DESCRIPTION), new TypeReference<List<OrderProductDescription>>() {});
        order.setOrderProductDescriptions(orderProductDescriptions);
        return order;
    }

}
