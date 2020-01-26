package application;

import application.controller.ProductController;
import application.dao.ProductRepository;
import application.dao.UserRepository;
import application.model.*;
import application.service.ProductService;
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
@WebMvcTest(value = {ProductController.class, UserService.class, ProductService.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    private Product product1;

    private ObjectMapper objectMapper;

    @Before
    public void init(){
        objectMapper = new ObjectMapper();
        product1 = new Product(1, "testName", "testCategory", "testDesc", 1000, "INR", 3);
        Mockito.when(userRepository.getPasswordForId(1)).thenReturn(Encryption.encrypt("test"));
    }

    @Test
    public void addProduct() throws Exception {
        Mockito.when(productRepository.addProduct(Mockito.any())).thenReturn(product1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/add")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Product product = objectMapper.readValue(json, Product.class);
        Assert.assertEquals("product has same id ", product.getId(), product1.getId());
    }

    @Test
    public void updateProductSuccess() throws Exception{
        Mockito.when(productRepository.updateProduct(Mockito.any())).thenReturn(1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/update/id=1")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("update success", "", json);
    }
    @Test
    public void updateProductFailOnId() throws Exception{
        Mockito.when(productRepository.updateProduct(Mockito.any())).thenReturn(0);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/update/id=1")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        HttpResponse httpResponse = objectMapper.readValue(json, HttpResponse.class);
        Assert.assertEquals("No update done", "Invalid Product ID", httpResponse.getMessage());
    }
    @Test
    public void updateProductFailOnQuantity() throws Exception {
        Mockito.when(productRepository.updateProduct(Mockito.any())).thenReturn(0);
        Mockito.when(productRepository.getQuantityForProduct(1)).thenReturn(3);
        product1.setQuantity(-10);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/update/id=1")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1))
                .contentType(MediaType.APPLICATION_JSON);
        product1.setQuantity(3);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        HttpResponse httpResponse = objectMapper.readValue(json, HttpResponse.class);
        Assert.assertEquals("quantity cannot be updated to less than 0", "Not enough items in inventory to remove", httpResponse.getMessage());
    }


    @Test
    public void deleteProductSuccess() throws Exception{
        Mockito.when(productRepository.deleteProduct(1)).thenReturn(1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/delete/id=1")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("delete success", "", json);
    }

    @Test
    public void deleteProductFailOnId() throws Exception{
        Mockito.when(productRepository.deleteProduct(1)).thenReturn(0);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/delete/id=1")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        HttpResponse httpResponse = objectMapper.readValue(json, HttpResponse.class);
        Assert.assertEquals("delete fail due to id not found", "Invalid Product ID", httpResponse.getMessage());
    }

    @Test
    public void searchProducts() throws Exception{
        Mockito.when(productRepository.searchAllProducts("", 2,2)).thenReturn(Arrays.asList(product1, product1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/search/all/prefix=/page=2&limit=2")
                .header("user-id", 1)
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readValue(json, List.class);
        Assert.assertEquals("number of items equal", 2, products.size());
    }


}
