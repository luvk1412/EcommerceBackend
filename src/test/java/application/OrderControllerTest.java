package application;

import application.controller.OrderController;
import application.dao.CartRepository;
import application.dao.OrderRepository;
import application.dao.ProductRepository;
import application.dao.UserRepository;
import application.exception.PlaceOrderErrorResponse;
import application.model.*;
import application.service.OrderService;
import application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {OrderController.class, UserService.class, OrderService.class})
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderRepository orderRepository;

    private Product product1;

    private ObjectMapper objectMapper;

    private Cart cart;
    private List<OrderProductDescription> orderProductDescriptions;

    Order order;

    @Before
    public void init(){
        objectMapper = new ObjectMapper();
        product1 = new Product(1, "testName", "testCategory", "testDesc", 1000, "INR", 4);
        cart = new Cart(1,1,5);
        orderProductDescriptions = Arrays.asList(new OrderProductDescription(1, "testName", 2));
        order = new Order(1, 1, 2000, "INR", Mockito.any(), orderProductDescriptions);
        Mockito.when(userRepository.getPasswordForId(1)).thenReturn(Encryption.encrypt("test"));
    }

    @Test
    public void getOrdersForUser() throws Exception{
        Mockito.when(orderRepository.getAllOrdersForUser(1)).thenReturn(Arrays.asList(new Order(), new Order()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/all")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        List<Order> orders = objectMapper.readValue(json, List.class);
        Assert.assertEquals("number of orders equal", 2, orders.size());
    }

    @Test
    public void placeOrdersForUserSuccess() throws Exception{
        Mockito.doNothing().when(orderRepository).placeOrder(order);
        Mockito.when(cartRepository.deleteCartItemForUser(1)).thenReturn(1);
        Mockito.when(cartRepository.updateProductAfterOrder(1)).thenReturn(1);
        Mockito.when(cartRepository.getCartForOrder(1)).thenReturn(Arrays.asList(new CartForOrder(1, "testName", 2, 4, 1000, "INR")));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/checkout")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("order placed success", "", json);
    }

    @Test
    public void placeOrdersForUserFailure() throws Exception{
        Mockito.doNothing().when(orderRepository).placeOrder(order);
        Mockito.when(cartRepository.deleteCartItemForUser(1)).thenReturn(1);
        Mockito.when(cartRepository.updateProductAfterOrder(1)).thenReturn(1);
        Mockito.when(cartRepository.getCartForOrder(1)).thenReturn(Arrays.asList(new CartForOrder(1, "testName", 2, 1, 1000, "INR")));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/checkout")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        PlaceOrderErrorResponse placeOrderErrorResponse = objectMapper.readValue(json, PlaceOrderErrorResponse.class);
        Assert.assertEquals("order placed failure with1 error", 1, placeOrderErrorResponse.getInsufficientProducts().size());
        Assert.assertEquals("order placed failure with id 1", 1, placeOrderErrorResponse.getInsufficientProducts().get(0).getProductId());
    }

    @Test
    public void cancelOrderForUser() throws Exception{
        Mockito.doNothing().when(productRepository).updateProductQuantityForCancelOrder(orderProductDescriptions);
        Mockito.when(orderRepository.getOrderById(1)).thenReturn(order);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/order/cancel/orderId=1")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("order delete success", "", json);
    }
}
