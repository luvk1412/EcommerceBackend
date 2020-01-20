package application.service;

import application.dao.UserRepository;
import application.exception.AppException;
import application.model.Encryption;
import application.model.HttpResponse;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

import static application.model.Constants.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean authenticateUser(int userId, String password){
        return userRepository.getAllUsers().stream()
                .anyMatch(user -> user.getId() == userId && Encryption.decrypt(user.getPassword()).equals(password));
    }

    public User getSignInUser(String email, String password){
        User signedInUser = userRepository.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email) && Encryption.decrypt(user.getPassword()).equals(password))
                .findAny()
                .orElse((User) NULL);
        if(signedInUser != NULL){
            setUserPasswordNull(signedInUser);
        }
        return signedInUser;
    }

    public User addUser(User user) {
        validateUser(user);
        user.setPassword(Encryption.encrypt(user.getPassword()));
        user = userRepository.addUser(user);
        setUserPasswordNull(user);
        return user;
    }

    public HttpResponse updateUser(User user) {
        validateUser(user);
        validateUpdate(user);
        user.setPassword(Encryption.encrypt(user.getPassword()));
        userRepository.updateUser(user);
        return new HttpResponse(CODE_POST_SUCCESS, MESSAGE_UPDATE_SUCCESS);
    }

    public HttpResponse deleteUser(Integer id){
        validateUserId(id);
        userRepository.deleteUser(id);
        return new HttpResponse(CODE_POST_SUCCESS, MESSAGE_DELETE_SUCCESS);
    }

    private void validateUser(User user){
        validateName(user.getName());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validateAddress(user.getAddress());
        validatePhone(user.getPhone());
        if(duplicateEmail(user.getId(), user.getEmail(), userRepository.getAllUsers()))
            throw  new AppException(CODE_DUPLICATE, MESSAGE_DUPLICATE_EMAIL);
    }

    public void validateUserId(Integer id){
        if(id == null || !userIdPresent(id, userRepository.getAllUsers())){
            throw  new AppException(CODE_INVALID, MESSAGE_INVALID_USER_ID);
        }
    }

    private void validateName(String name) throws AppException {
        if(name == null || !name.matches(REGEX_VALID_NAME))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_NAME);
    }
    private void validatePassword(String password){
        if(password == null || !password.matches(REGEX_VALID_NON_EMPTY))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_PASSWORD);
    }
    private void validateEmail(String email){
        if(email == null || !email.matches(REGEX_VALID_EMAIL))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_EMAIL);
    }
    private void validateAddress(String address){
        if(address == null || !address.matches(REGEX_VALID_NON_EMPTY))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_ADDRESS);
    }
    private void validatePhone(Long phone){
        if(phone == null || !Long.toString(phone).matches(REGEX_VALID_PHONE))
            throw new AppException(CODE_INVALID, MESSAGE_INVALID_PHONE);
    }

    private void validateUpdate(User user) {
        List<User> users = userRepository.getAllUsers();
        validateUserId(user.getId());
        if(duplicateEmail(user.getId(), user.getEmail(), users))
            throw  new AppException(CODE_DUPLICATE, MESSAGE_DUPLICATE_EMAIL);
    }

    private boolean duplicateEmail(Integer id, String email, List<User> allUsers) {
        return allUsers.stream()
                .anyMatch(user -> user.getId() != id && user.getEmail().equals(email));
    }
    private boolean userIdPresent(int id, List<User> users) {
        return users.stream()
                .anyMatch(curUser -> curUser.getId() == id);
    }

    private void setUserPasswordNull(User user){
        user.setPassword(null);
    }

}
