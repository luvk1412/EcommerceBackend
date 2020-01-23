package application.controller;

import application.exception.AppException;
import application.model.*;
import application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static application.model.Constants.HEADER_USER_ID;
import static application.model.Constants.MESSAGE_UNAUTHORISED;

@RestController
@Validated
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public void addToCart(@RequestHeader(HEADER_USER_ID) Integer tokenId, @Valid @RequestBody Cart cart){
        if(!tokenId.equals(cart.getUserId()))
            throw new AppException(HttpStatus.UNAUTHORIZED, MESSAGE_UNAUTHORISED);
        cartService.addToCart(cart);
    }

    @PutMapping("/update/userId={userId}&productId={productId}/quantity={quantity}")
    public void updateCartQuantity(@RequestHeader(HEADER_USER_ID) Integer tokenId, @PathVariable @NotNull Integer userId, @PathVariable @NotNull Integer productId, @PathVariable @NotNull Integer quantity){
        if(!tokenId.equals(userId))
            throw new AppException(HttpStatus.UNAUTHORIZED, MESSAGE_UNAUTHORISED);
        cartService.updateCartQuantity(userId, productId, quantity);
    }

    @GetMapping("/get/userId={userId}")
    public List<CartReturnObject> getCartForUser(@RequestHeader(HEADER_USER_ID) Integer tokenId, @PathVariable @NotNull Integer userId){
        if(!tokenId.equals(userId))
            throw new AppException(HttpStatus.UNAUTHORIZED, MESSAGE_UNAUTHORISED);
        return cartService.getCartForUser(userId);
    }

}
