package application.service;

import application.dao.UserRepository;
import application.exception.AppException;
import application.model.Encryption;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.model.Constants.*;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticateUser(int userId, String receivedPassword){
        String actualPassword = userRepository.getPasswordForId(userId);
        return Encryption.decrypt(actualPassword).equals(receivedPassword);
    }

    public User getSignInUser(String email, String password){
        User signedInUser = userRepository.getUserForEmail(email);
        if(Encryption.decrypt(signedInUser.getPassword()).equals(password))
            return signedInUser;
        throw new AppException(HttpStatus.BAD_REQUEST, MESSAGE_INVALID_PASSWORD);
    }

    public User addUser(User user) {
        user.setPassword(Encryption.encrypt(user.getPassword()));
        user = userRepository.addUser(user);
        setUserPasswordNull(user);
        return user;
    }

    public void updateUser(User user) {
        user.setPassword(Encryption.encrypt(user.getPassword()));
        userRepository.updateUser(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteUser(id);
    }


    private void setUserPasswordNull(User user){
        user.setPassword(null);
    }

}
