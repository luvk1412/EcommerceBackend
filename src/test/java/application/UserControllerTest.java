package application;


import application.controller.UserController;
import application.dao.UserRepository;
import application.model.Encryption;
import application.model.HttpResponse;
import application.model.User;
import application.model.UserLoginObject;
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


@RunWith(SpringRunner.class)
@WebMvcTest(value = {UserController.class, UserService.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private User user1;

    private ObjectMapper objectMapper;

    @Before
    public void init(){
        objectMapper = new ObjectMapper();
        user1 = new User(1, Encryption.encrypt("test"), "testName", "testName@test.com", "testAddress", 9999999999L);
        Mockito.when(userRepository.getPasswordForId(user1.getId())).thenReturn(user1.getPassword());
    }

    @Test
    public void loginSuccess() throws Exception {
        Mockito.when(userRepository.getUserForEmail(Mockito.any())).thenReturn(user1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserLoginObject(user1.getEmail(), Encryption.decrypt(user1.getPassword()))))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        User returnUser = objectMapper.readValue(json, User.class);
        Assert.assertEquals("logged in user has same id ",user1.getId(), returnUser.getId());
        Assert.assertEquals("logged in user has same email",user1.getEmail(), returnUser.getEmail());
    }

    @Test
    public void loginFail() throws Exception {
        Mockito.when(userRepository.getUserForEmail(Mockito.any())).thenReturn(user1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserLoginObject(user1.getEmail(), "wrongpassword")))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        HttpResponse httpResponse = objectMapper.readValue(json, HttpResponse.class);
        Assert.assertEquals("Unauthorised status code", 400, httpResponse.getStatus());
    }

    @Test
    public void addUser() throws Exception {
        Mockito.when(userRepository.addUser(Mockito.any())).thenReturn(user1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        User user = objectMapper.readValue(json, User.class);
        Assert.assertEquals("logged in user has same id ", user1.getId(), user.getId());
        Assert.assertEquals("logged in user has same email", user1.getEmail(), user.getEmail());
    }

    @Test
    public void updateUser() throws Exception{
        Mockito.when(userRepository.addUser(Mockito.any())).thenReturn(user1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/update")
                .header("user-id", user1.getId())
                .header("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
        Assert.assertEquals("", json);
    }


}
