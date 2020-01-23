package application.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserLoginObject {
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;

    public UserLoginObject(){

    }

    public UserLoginObject(@NotNull @NotBlank String email, @NotNull @NotBlank String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
