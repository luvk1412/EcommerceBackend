package application.dao;

import application.model.Cart;
import application.model.CartForOrder;
import application.model.CartReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static application.dao.Mappers.*;
import static application.dao.SqlConstants.*;

@Repository
public class CartRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(CartRepository.class);

    public void upsertToCart(Cart cart) {
        try{
            namedParameterJdbcTemplate.update(QUERY_CART_ADD, getCartMap(cart));
        }
        catch (DuplicateKeyException ex){
            LOGGER.info("Product with ID : {} already exists in User Cart, Updating the quantity", cart.getProductId());
            updateCartQuantity(cart.getUserId(), cart.getProductId(), cart.getQuantity());
        }
    }

    public List<CartReturnObject> getCartForUser(Integer userId) {
        return namedParameterJdbcTemplate.query(QUERY_CART_GET_JOIN, getCartMapUserId(userId), new RowMapper<CartReturnObject>() {
            @Override
            public CartReturnObject mapRow(ResultSet resultSet, int i) throws SQLException {
                return getCartReturnObjectFromResultSet(resultSet);
            }
        });
    }

    public List<CartForOrder> getCartForOrder(int userId){
        return namedParameterJdbcTemplate.query(QUERY_CART_GET_JOIN_FOR_ORDER, getCartMapUserId(userId), new BeanPropertyRowMapper<>(CartForOrder.class));
    }


    public int updateCartQuantity(int userId, int productId, int quantity) {
        return namedParameterJdbcTemplate.update(QUERY_CART_UPDATE_QUANTITY, getCartMap(userId, productId, quantity));
    }

    public int updateProductAfterOrder(int userId){
        return namedParameterJdbcTemplate.update(QUERY_PRODUCT_UPDATE_AFTER_ORDER, getCartMapUserId(userId));
    }

    public int deleteCartItem(int userId, int productId) {
        return namedParameterJdbcTemplate.update(QUERY_CART_DELETE, getCartMap(userId, productId));
    }
    public int deleteCartItemForUser(int userId) {
        return namedParameterJdbcTemplate.update(QUERY_CART_DELETE_FOR_USER, getCartMapUserId(userId));
    }


    public List<Cart> getAll() {
        return namedParameterJdbcTemplate.query(QUERY_CART_GET_ALL, new RowMapper<Cart>() {
            @Override
            public Cart mapRow(ResultSet resultSet, int i) throws SQLException {
                return getCartFromResultSet(resultSet);
            }
        });
    }

    public int getQuantityForCart(int userId, int productId){
        try{
            return namedParameterJdbcTemplate.queryForObject(QUERY_CART_QUANTITY, getCartMap(userId, productId), Integer.class);
        }catch (EmptyResultDataAccessException ex){
            LOGGER.info("Product wth ID : {} not found in Cart for UserId : {}, returning 0", productId, userId);
            return 0;
        }
    }
}
