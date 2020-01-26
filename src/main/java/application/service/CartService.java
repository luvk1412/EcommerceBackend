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

    public void upsertToCart(Cart cart) {
        int cartQuantity = cartRepository.getQuantityForCart(cart.getUserId(), cart.getProductId());
        int productQuantity = productRepository.getQuantityForProduct(cart.getProductId());
        validateCartAddition(productQuantity, cartQuantity, cart.getQuantity());
        validateCartRemoval(cartQuantity, cart.getQuantity());
        if(cartQuantity + cart.getQuantity() == 0)
            deleteProductFromCart(cart.getUserId(), cart.getProductId());
        else
            cartRepository.upsertToCart(cart);
    }

    public List<CartReturnObject> getCartForUser(Integer userId) {
        return cartRepository.getCartForUser(userId);
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
