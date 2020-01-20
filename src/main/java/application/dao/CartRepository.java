package application.dao;

import application.model.Cart;
import application.model.CartReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public Cart addToCart(Cart cart) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QUERY_CART_ADD, getCartMap(cart), keyHolder);
        cart.setId(keyHolder.getKey().intValue());
        return cart;
    }

    public List<CartReturnObject> getCartForUser(Integer userId) {
        return namedParameterJdbcTemplate.query(QUERY_CART_GET, getCartMapUserId(userId), new RowMapper<CartReturnObject>() {
            @Override
            public CartReturnObject mapRow(ResultSet resultSet, int i) throws SQLException {
                return new CartReturnObject(resultSet);
            }
        });
    }

    public void deleteCartItem(int id) {
        namedParameterJdbcTemplate.update(QUERY_CART_DELETE, getCartMap(id));
    }

    public int getCountForId(Integer id) {
        return namedParameterJdbcTemplate.queryForObject(QUERY_CART_USER_ID_COUNT, getCartMap(id), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(COLUMN_GENERAL_COUNT);
            }
        });
    }
}
