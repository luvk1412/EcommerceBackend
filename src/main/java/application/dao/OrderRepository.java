package application.dao;

import application.exception.AppException;
import application.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static application.dao.Mappers.*;
import static application.dao.SqlConstants.*;
import static application.model.Constants.MESSAGE_INVALID_ORDER_ID;

@Repository
public class OrderRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepository.class);

    public List<Order> getAllOrdersForUser(int userId){
        return namedParameterJdbcTemplate.query(QUERY_ORDER_GET_BY_USER_ID, getOrderMapUserId(userId), (resultSet, i) -> {
            try {
                return getOrderFromResultSet(resultSet);
            } catch (JsonProcessingException e) {
                LOGGER.info("Json Parsing error for request for used ID : {}", userId);
                throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString());
            }
        });
    }

    public Order getOrderById(int orderId){
        try{
            return namedParameterJdbcTemplate.queryForObject(QUERY_ORDER_GET_BY_ORDER_ID, getOrderMapOrderId(orderId), (resultSet, i) -> {
                try {
                    return getOrderFromResultSet(resultSet);
                } catch (JsonProcessingException e) {
                    LOGGER.info("Json Parsing error for request for order ID : {}", orderId);
                    throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString());
                }
            });
        }catch (EmptyResultDataAccessException ex){
            throw new AppException(HttpStatus.NOT_FOUND, MESSAGE_INVALID_ORDER_ID);
        }
    }

    public void placeOrder(Order order) throws JsonProcessingException {
        namedParameterJdbcTemplate.update(QUERY_ORDER_ADD, getOrderMap(order));
    }

    public void deleteOrder(int orderId){
        namedParameterJdbcTemplate.update(QUERY_ORDER_DELETE, getOrderMapOrderId(orderId));
    }
}
