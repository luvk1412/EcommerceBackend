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

    @GetMapping(value="/all")
    public List<Order> getOrdersForUser(@RequestHeader(HEADER_USER_ID) Integer userId){
        return orderService.getOrders(userId);
    }

    @PostMapping(value="/checkout")
    public void placeOrder(@RequestHeader(HEADER_USER_ID) Integer userId) throws JsonProcessingException {
        orderService.placeOrder(userId);
    }



    @DeleteMapping(value="/cancel/orderId={orderId}")
    public void cancelOrder(@PathVariable @NotNull Integer orderId){
        orderService.cancelOrder(orderId);
    }
}
