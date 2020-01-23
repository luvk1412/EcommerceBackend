package application.service;

import application.dao.CartRepository;
import application.dao.ProductRepository;
import application.exception.AppException;
import application.model.Cart;
import application.model.CartReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.model.Constants.*;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    public void addToCart(Cart cart) {
        validateCartAddition(productRepository.getQuantityForProduct(cart.getProductId()), cartRepository.getQuantityForCart(cart.getUserId(), cart.getProductId()), cart.getQuantity());
        cartRepository.addToCart(cart);
    }

    public List<CartReturnObject> getCartForUser(Integer userId) {
        return cartRepository.getCartForUser(userId);
    }

    public void updateCartQuantity(int userId, int productId, int quantity) {
        int cartQuantity = cartRepository.getQuantityForCart(userId, productId);
        int productQuantity = productRepository.getQuantityForProduct(productId);
        validateCartAddition(productQuantity, cartQuantity, quantity);
        validateCartRemoval(cartQuantity, quantity);
        if(cartQuantity + quantity == 0)
            deleteProductFromCart(userId, productId);
        else
            if(cartRepository.updateCartQuantity(userId, productId, quantity) == 0){
                throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_INVALID_CART_ITEM);
            }
    }

    public void deleteProductFromCart(int userId, int productId) {
        if(cartRepository.deleteCartItem(userId, productId) == 0){
            throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_INVALID_CART_ITEM);
        }
    }

    private void validateCartAddition(int productQuantity, int cartQuantity, int quantity) {
        if(productQuantity < cartQuantity + quantity)
            throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_EXCEEDED_CART_ADD);
    }
    private void validateCartRemoval(int cartQuantity, int quantity) {
        if(cartQuantity + quantity < 0)
            throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_EXCEEDED_CART_DEC);
    }



}
