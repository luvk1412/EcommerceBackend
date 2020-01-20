package application.service;

import application.dao.ProductRepository;
import application.exception.AppException;
import application.model.HttpResponse;
import application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static application.model.Constants.*;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product addProduct(Product product) {
        validateProduct(product);
        product = productRepository.addProduct(product);
        return product;
    }

    public HttpResponse updateProduct(Product product) {
        validateProduct(product);
        validateUpdate(product);
        productRepository.updateProduct(product);
        return new HttpResponse(CODE_POST_SUCCESS, MESSAGE_UPDATE_SUCCESS);
    }

    public HttpResponse updateProductQuantity(Integer id, int quantity) {
        if(id == null || !productIdPresent(id, productRepository.getAllProducts()))
            throw  new AppException(CODE_INVALID, MESSAGE_INVALID_PRODUCT_ID);
        productRepository.updateProductQuantity(id, quantity);
        return new HttpResponse(CODE_POST_SUCCESS, MESSAGE_UPDATE_SUCCESS);
    }
    public List<Product> searchProducts(String filter, String prefix, int pageNumber, int limit) {
        return getProductsByFilter(filter, prefix, pageNumber,  limit);
    }

    public HttpResponse deleteProduct(int id){
        if(!productIdPresent(id, productRepository.getAllProducts())){
            throw  new AppException(CODE_INVALID, MESSAGE_INVALID_PRODUCT_ID);
        }
        productRepository.deleteProduct(id);
        return new HttpResponse(CODE_POST_SUCCESS, MESSAGE_DELETE_SUCCESS);
    }
    public void verifyCartAddition(Integer productId, Integer quantity){
        List<Product> allProducts = productRepository.getAllProducts();
        if(productId == null || !productIdPresent(productId, allProducts)) {
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_PRODUCT_ID);
        }
        if(quantity == null || !productQuantityAvailable(productId, quantity, allProducts)) {
            throw new AppException(CODE_EXCEEDED, MESSAGE_EXCEEDED_CART);
        }

    }
    public boolean productQuantityAvailable(Integer productId, Integer quantity, List<Product> allProducts){
        return allProducts.stream()
                .anyMatch(product -> productId == product.getId() && quantity <= product.getQuantity());
    }

    private void validateProduct(Product product){
        validateName(product.getName());
        validateCategory(product.getCategory());
        validateCurrency(product.getCurrency());
        validateDescription(product.getDescription());
        validatePrice(product.getPrice());
        validateQuantity(product.getQuantity());
        if(duplicateProduct(product.getId(), product.getCategory(), product.getName(), productRepository.getAllProducts()))
            throw  new AppException(CODE_DUPLICATE, MESSAGE_DUPLICATE_PRODUCT);
    }

    private void validateUpdate(Product product) {
        List<Product> allProducts = productRepository.getAllProducts();
        if(product.getId() == null || !productIdPresent(product.getId(), allProducts))
            throw  new AppException(CODE_INVALID, MESSAGE_INVALID_PRODUCT_ID);
        if(duplicateProduct(product.getId(), product.getCategory(), product.getName(), allProducts))
            throw  new AppException(CODE_DUPLICATE, MESSAGE_DUPLICATE_PRODUCT);
    }

    private void validateDescription(String description) {
        if(!description.matches(REGEX_VALID_NON_EMPTY))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_DESCRIPTION);
    }

    private void validateCurrency(String currency) {
        if(!currency.matches(REGEX_VALID_NON_EMPTY))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_CURRENCY);
    }

    private void validateCategory(String category) {
        if(!category.matches(REGEX_VALID_NON_EMPTY))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_CATEGORY);
    }

    private void validateName(String name) {
        if(!name.matches(REGEX_VALID_NON_EMPTY))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_NAME);
    }
    private boolean duplicateProduct(Integer id, String category, String name, List<Product> allProducts) {
        return allProducts.stream()
                .anyMatch(product -> id != product.getId() && product.getCategory().equals(category) && product.getName().equals(name));
    }
    private void validateQuantity(Integer quantity) {
        if(quantity == null){
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_QUANTITY);
        }
    }

    private void validatePrice(Integer price) {
        if(price == null){
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_PRICE);
        }
    }

    private boolean productIdPresent(int id, List<Product> allProducts) {
        return allProducts.stream()
                .anyMatch(product -> id == product.getId());
    }

    private List<Product> getProductsByFilter(String filter, String prefix, int pageNumber, int limit) {
        if(filter.equals(FILTER_SEARCH_PRODUCT_ALL)){
            return getProductsForPage(pageNumber, limit, productRepository.searchAllProducts(prefix));
        }
        else{
            return getProductsForPage(pageNumber, limit, productRepository.searchCategoryProducts(filter, prefix));
        }
    }

    private List<Product> getProductsForPage(int pageNumber, int limit, List<Product> products){
        if(products.size() == 0)
            return new ArrayList<>();
        int low = (pageNumber-1) * limit;
        int high = Math.min(low + limit, products.size());
        if(pageNumber <= 0 || low >= products.size())
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_PAGE);
        if(limit <= 0)
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_LIMIT);
        return products.subList(low, high);

    }

}
