package application.service;


import application.dao.CartRepository;
import application.dao.OrderRepository;
import application.dao.ProductRepository;
import application.exception.AppException;
import application.exception.InsufficientProduct;
import application.exception.PlaceOrderErrorResponse;
import application.exception.PlaceOrderException;
import application.model.CartForOrder;
import application.model.Order;
import application.model.OrderProductDescription;
import application.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static application.model.Constants.MESSAGE_EMPTY_CART;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    public List<Order> getOrders(Integer userId) {
        return orderRepository.getAllOrdersForUser(userId);
    }

    @Transactional
    public void placeOrder(Integer userId) throws JsonProcessingException {
        List<CartForOrder> cartForOrderList = cartRepository.getCartForOrder(userId);
        validateOrder(cartForOrderList);
        orderRepository.placeOrder(mapToOrderObject(userId, cartForOrderList));
        cartRepository.updateProductAfterOrder(userId);
        cartRepository.deleteCartItemForUser(userId);
    }

    @Transactional
    public void cancelOrder(int orderId) {
        Order order = orderRepository.getOrderById(orderId);
        productRepository.updateProductQuantityForCancelOrder(order.getOrderProductDescriptions());
        orderRepository.deleteOrder(orderId);
    }

    private void validateOrder(List<CartForOrder> cartForOrders){
        if(cartForOrders.size() == 0)
            throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_EMPTY_CART);
        List<InsufficientProduct> insufficientProducts = new ArrayList<>();
        for(CartForOrder cartForOrder : cartForOrders){
            if(cartForOrder.getCartQuantity() > cartForOrder.getProductQuantity()){
                insufficientProducts.add(new InsufficientProduct(cartForOrder.getProductId(), cartForOrder.getName()));
            }
        }
        if(insufficientProducts.size() > 0){
            throw new PlaceOrderException(HttpStatus.BAD_REQUEST, new PlaceOrderErrorResponse(insufficientProducts));
        }
    }

    private Order mapToOrderObject(int userId, List<CartForOrder> cartForOrderList){
        Order order = new Order();
        order.setUserId(userId);
        order.setCurrency(cartForOrderList.get(0).getCurrency());
        int totalAmount = 0;
        List<OrderProductDescription> orderProductDescriptions = new ArrayList<>();
        for(CartForOrder cartForOrder : cartForOrderList){
            totalAmount = totalAmount + cartForOrder.getCartQuantity() * cartForOrder.getPrice();
            orderProductDescriptions.add(new OrderProductDescription(cartForOrder.getProductId(), cartForOrder.getName(), cartForOrder.getCartQuantity()));
        }
        order.setAmount(totalAmount);
        order.setOrderProductDescriptions(orderProductDescriptions);
        return order;
    }

}
