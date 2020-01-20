package application.service;


import application.dao.CartRepository;
import application.exception.AppException;
import application.model.Cart;
import application.model.CartReturnObject;
import application.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.model.Constants.*;
import static application.model.Constants.MESSAGE_DELETE_SUCCESS;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    public Cart addToCart(Cart cart) {
        validateCartAddition(cart);
        return cartRepository.addToCart(cart);
    }

    private void validateCartAddition(Cart cart) {
        userService.validateUserId(cart.getUserId());
        productService.verifyCartAddition(cart.getProductId(), cart.getQuantity());
    }

    public List<CartReturnObject> getCartForUser(Integer userId) {
        userService.validateUserId(userId);
        return cartRepository.getCartForUser(userId);
    }

    public HttpResponse deleteProduct(Integer id) {
        validateCartId(id);
        cartRepository.deleteCartItem(id);
        return new HttpResponse(CODE_POST_SUCCESS, MESSAGE_DELETE_SUCCESS);
    }

    private void validateCartId(Integer id) {
        if(id == null || cartRepository.getCountForId(id) == 0){
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_CART_ID);
        }
    }
}
