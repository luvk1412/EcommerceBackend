package application.controller;

import application.model.*;
import application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Cart addToCart(@RequestBody Cart cart){
        return cartService.addToCart(cart);
    }

    @RequestMapping(value="/get/userId={userId}",method = RequestMethod.GET)
    public List<CartReturnObject> addCartForUser(@PathVariable Integer userId){
        return cartService.getCartForUser(userId);
    }
    @RequestMapping(value="/delete/id={id}", method = RequestMethod.DELETE)
    public HttpResponse deleteProduct(@PathVariable Integer id){
        return cartService.deleteProduct(id);
    }
}
