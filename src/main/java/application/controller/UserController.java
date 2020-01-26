package application.controller;

import application.exception.AppException;
import application.model.User;
import application.model.UserLoginObject;
import application.model.UserUpdateObject;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static application.model.Constants.*;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public User loginUser(@RequestBody UserLoginObject user) throws AppException {
        return userService.getSignInUser(user.getEmail(), user.getPassword());
    }

    @PostMapping("/add")
    public User addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping("/update")
    public void updateUser(@RequestHeader(HEADER_USER_ID) Integer userId, @Valid @RequestBody UserUpdateObject userUpdateObject) throws AppException, IllegalAccessException {
        userUpdateObject.setId(userId);
        userService.updateUser(userUpdateObject);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestHeader(HEADER_USER_ID) Integer tokenId){
        userService.deleteUser(tokenId);
    }

}
