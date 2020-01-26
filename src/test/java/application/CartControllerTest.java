package application;


import application.controller.CartController;
import application.dao.CartRepository;
import application.dao.ProductRepository;
import application.dao.UserRepository;
import application.model.*;
import application.service.CartService;
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
@WebMvcTest(value = {CartController.class, UserService.class, CartService.class})
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    private CartReturnObject cartReturnObject;

    private Product product1;

    private ObjectMapper objectMapper;

    private Cart cart;

    @Before
    public void init(){
        objectMapper = new ObjectMapper();
        product1 = new Product(1, "testName", "testCategory", "testDesc", 1000, "INR", 4);
        cartReturnObject = new CartReturnObject();
        cartReturnObject.setUserId(1);
        cartReturnObject.setProduct(product1);
        cart = new Cart(1,1,5);
        Mockito.when(userRepository.getPasswordForId(1)).thenReturn(Encryption.encrypt("test"));

    }
    @Test
    public void addToCartSuccess() throws Exception{
        Mockito.doNothing().when(cartRepository).upsertToCart(Mockito.any());
        Mockito.when(productRepository.getQuantityForProduct(1)).thenReturn(8);
        Mockito.when(cartRepository.getQuantityForCart(1, 1)).thenReturn(1);
        cart.setQuantity(5);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("add to cart success", "", json);
    }

    @Test
    public void removeFromCartSuccess() throws Exception{
        Mockito.doNothing().when(cartRepository).upsertToCart(Mockito.any());
        Mockito.when(productRepository.getQuantityForProduct(1)).thenReturn(4);
        Mockito.when(cartRepository.getQuantityForCart(1, 1)).thenReturn(7);
        cart.setQuantity(-5);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("Remove from cart", "", json);
    }
    @Test
    public void addToCartFailure() throws Exception{
        Mockito.doNothing().when(cartRepository).upsertToCart(Mockito.any());
        Mockito.when(productRepository.getQuantityForProduct(1)).thenReturn(4);
        Mockito.when(cartRepository.getQuantityForCart(1, 1)).thenReturn(0);
        cart.setQuantity(5);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        HttpResponse httpResponse = objectMapper.readValue(json, HttpResponse.class);
        Assert.assertEquals("add to cart failure", "Item cannot be added to cart as requested quantity is not present in inventory", httpResponse.getMessage());
    }
    @Test
    public void removeFromCartFailure() throws Exception{
        Mockito.doNothing().when(cartRepository).upsertToCart(Mockito.any());
        Mockito.when(productRepository.getQuantityForProduct(1)).thenReturn(4);
        Mockito.when(cartRepository.getQuantityForCart(1, 1)).thenReturn(2);
        cart.setQuantity(-5);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        HttpResponse httpResponse = objectMapper.readValue(json, HttpResponse.class);
        Assert.assertEquals("remove fro cart failure", "Not enough items in cart to remove", httpResponse.getMessage());
    }

    @Test
    public void deleteFromCartSuccess() throws Exception{
        Mockito.doNothing().when(cartRepository).upsertToCart(Mockito.any());
        Mockito.when(productRepository.getQuantityForProduct(1)).thenReturn(4);
        Mockito.when(cartRepository.getQuantityForCart(1, 1)).thenReturn(5);
        Mockito.when(cartRepository.deleteCartItem(1,1)).thenReturn(1);
        cart.setQuantity(-5);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("add to cart For addition", "", json);
    }

    @Test
    public void deleteFromCartFailure() throws Exception{
        Mockito.doNothing().when(cartRepository).upsertToCart(Mockito.any());
        Mockito.when(productRepository.getQuantityForProduct(1)).thenReturn(4);
        Mockito.when(cartRepository.getQuantityForCart(1, 1)).thenReturn(5);
        Mockito.when(cartRepository.deleteCartItem(1,1)).thenReturn(0);
        cart.setQuantity(-5);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        HttpResponse httpResponse = objectMapper.readValue(json, HttpResponse.class);
        Assert.assertEquals("delete from cart failure", "Item not present in cart for given user", httpResponse.getMessage());
    }

    @Test
    public void getCartForUser() throws Exception{
        Mockito.when(cartRepository.getCartForUser(1)).thenReturn(Arrays.asList(cartReturnObject, cartReturnObject));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart/get")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readValue(json, List.class);
        Assert.assertEquals("number of items equal", 2, products.size());
    }
}
