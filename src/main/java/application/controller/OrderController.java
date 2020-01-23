package application.controller;

import application.exception.AppException;
import application.model.Order;
import application.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.List;

import static application.model.Constants.HEADER_USER_ID;
import static application.model.Constants.MESSAGE_UNAUTHORISED;

@RestController
@Validated
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value="/all/userId={userId}")
    public List<Order> getOrdersForUser(@RequestHeader(HEADER_USER_ID) Integer tokenId, @PathVariable @NotNull Integer userId){
        if(!tokenId.equals(userId))
            throw new AppException(HttpStatus.UNAUTHORIZED, MESSAGE_UNAUTHORISED);
        return orderService.getOrders(userId);
    }

    @PostMapping(value="/checkout/userId={userId}")
    public void placeOrder(@RequestHeader(HEADER_USER_ID) Integer tokenId, @PathVariable @NotNull Integer userId) throws JsonProcessingException {
        if(!tokenId.equals(userId))
            throw new AppException(HttpStatus.UNAUTHORIZED, MESSAGE_UNAUTHORISED);
        orderService.placeOrder(userId);
    }



    @DeleteMapping(value="/cancel/userId={userId}&orderId={orderId}")
    public void cancelOrder(@RequestHeader(HEADER_USER_ID) Integer tokenId, @PathVariable @NotNull Integer userId, @PathVariable @NotNull Integer orderId){
        if(!tokenId.equals(userId))
            throw new AppException(HttpStatus.UNAUTHORIZED, MESSAGE_UNAUTHORISED);
        orderService.cancelOrder(orderId);
    }
}
