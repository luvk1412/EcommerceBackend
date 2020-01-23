package application.service;

import application.dao.ProductRepository;
import application.exception.AppException;
import application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

import static application.model.Constants.*;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product addProduct(Product product) {
        product = productRepository.addProduct(product);
        return product;
    }

    public void updateProduct(Product product) {
        if(productRepository.getQuantityForProduct(product.getId()) + product.getQuantity() < 0){
            throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_EXCEEDED_PRODUCT_DEC);
        }
        if(productRepository.updateProduct(product) == 0){
            throw new AppException(HttpStatus.BAD_REQUEST.BAD_REQUEST, MESSAGE_INVALID_PRODUCT_ID);
        }
    }

    public List<Product> searchProducts(String filter, String prefix, int pageNumber, int limit) {
        return getProductsByFilter(filter, prefix, pageNumber,  limit);
    }

    public void deleteProduct(int id){
        if(productRepository.deleteProduct(id) == 0){
            throw new AppException(HttpStatus.BAD_REQUEST.BAD_REQUEST, MESSAGE_INVALID_PRODUCT_ID);
        }
    }

    private List<Product> getProductsByFilter(String filter, String prefix, int pageNumber, int limit) {
        if(filter.equals(FILTER_SEARCH_PRODUCT_ALL)){
            return productRepository.searchAllProducts(prefix, limit, limit * (pageNumber - 1));
        }
        else{
            return productRepository.searchCategoryProducts(filter, prefix, limit, limit * (pageNumber - 1));
        }
    }

}
