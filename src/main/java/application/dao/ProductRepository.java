package application.dao;

import application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static application.dao.Mappers.getProductMap;
import static application.dao.SqlConstants.*;

@Repository
public class ProductRepository {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Product> getAllProducts(){
        return namedParameterJdbcTemplate.query(QUERY_PRODUCT_GET_ALL, new RowMapper<Product>() {
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet);
            }
        });
    }

    public Product addProduct(Product product){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QUERY_PRODUCT_ADD, getProductMap(product), keyHolder);
        product.setId(keyHolder.getKey().intValue());
        return product;
    }

    public void updateProduct(Product product) {
        namedParameterJdbcTemplate.update(QUERY_PRODUCT_UPDATE, getProductMap(product));
    }

    public void updateProductQuantity(Integer id, int quantity) {
        namedParameterJdbcTemplate.update(QUERY_PRODUCT_UPDATE_QUANTITY, getProductMap(id, quantity));
    }


    public void deleteProduct(int id) {
        namedParameterJdbcTemplate.update(QUERY_PRODUCT_DELETE, getProductMap(id));
    }


    public List<Product> searchAllProducts(String prefix) {
        return namedParameterJdbcTemplate.query(QUERY_PRODUCT_SEARCH_ALL, getProductMap(prefix), new RowMapper<Product>() {
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet);
            }
        });
    }

    public List<Product> searchCategoryProducts(String filter, String prefix) {
        return namedParameterJdbcTemplate.query(QUERY_PRODUCT_SEARCH_ALL_CATEGORY, getProductMap(filter, prefix), new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet);
            }
        });
    }

}
