package application.dao;

import application.exception.AppException;
import application.model.OrderProductDescription;
import application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static application.dao.Mappers.*;
import static application.dao.SqlConstants.*;
import static application.model.Constants.MESSAGE_INVALID_PRODUCT_ID;

@Repository
public class ProductRepository {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Product addProduct(Product product){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QUERY_PRODUCT_ADD, getProductMap(product), keyHolder);
        product.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return product;
    }

    public int updateProduct(Product product) {
        return namedParameterJdbcTemplate.update(QUERY_PRODUCT_UPDATE, getProductMap(product));
    }

    public void updateProductQuantityForCancelOrder(List<OrderProductDescription> orderProductDescriptions) {
        MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[orderProductDescriptions.size()];
        for(int i = 0; i < orderProductDescriptions.size(); ++i){
            mapSqlParameterSources[i] = getProductMap(orderProductDescriptions.get(i).getProductId(), orderProductDescriptions.get(i).getQuantity());
        }
        namedParameterJdbcTemplate.batchUpdate(QUERY_PRODUCT_UPDATE_QUANTITY, mapSqlParameterSources);
    }

    public int deleteProduct(int id) {
        return namedParameterJdbcTemplate.update(QUERY_PRODUCT_DELETE, getProductMap(id));
    }


    public List<Product> searchAllProducts(String prefix, int limit, int offset) {
        return namedParameterJdbcTemplate.query(QUERY_PRODUCT_SEARCH_ALL, getProductMap(prefix, limit, offset), (resultSet, i) -> getProductFromResultSet(resultSet));
    }

    public List<Product> searchCategoryProducts(String filter, String prefix, int limit, int offset) {
        return namedParameterJdbcTemplate.query(QUERY_PRODUCT_SEARCH_ALL_CATEGORY, getProductMap(filter, prefix, limit, offset), (resultSet, i) -> getProductFromResultSet(resultSet));
    }

    public int getQuantityForProduct(int productId){
        try {
            return namedParameterJdbcTemplate.queryForObject(QUERY_PRODUCT_QUANTITY, getProductMap(productId), Integer.class);
        }catch (EmptyResultDataAccessException ex){
            throw new AppException(HttpStatus.NOT_FOUND, MESSAGE_INVALID_PRODUCT_ID);
        }
    }

}
