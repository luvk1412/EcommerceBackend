package application.controller;

import application.exception.AppException;
import application.model.HttpResponse;
import application.model.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static application.model.Constants.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/login")
    public User loginUser(@RequestBody User user) throws AppException {
        User signedInUser = userService.getSignInUser(user.getEmail(), user.getPassword());
        if(signedInUser != null){
            return signedInUser;
        }
        throw new AppException(CODE_INVALID, MESSAGE_INVALID_LOGIN);
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @RequestMapping(value="/update/id={id}",method = RequestMethod.PUT)
    public HttpResponse updateUser(@PathVariable Integer id, @RequestBody User user){
        user.setId(id);
        return userService.updateUser(user);
    }

    @RequestMapping(value="/delete/id={id}",method = RequestMethod.DELETE)
    public HttpResponse deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }

}
