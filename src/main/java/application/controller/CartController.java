package application.controller;

import application.model.*;
import application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static application.model.Constants.HEADER_USER_ID;

@RestController
@Validated
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public void upsertToCart(@RequestHeader(HEADER_USER_ID) Integer userId, @Valid @RequestBody Cart cart){
        cart.setUserId(userId);
        cartService.upsertToCart(cart);
    }

    @GetMapping("/get")
    public List<CartReturnObject> getCartForUser(@RequestHeader(HEADER_USER_ID) Integer userId){
        return cartService.getCartForUser(userId);
    }

}
