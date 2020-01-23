package application.model;

import javax.validation.constraints.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static application.dao.SqlConstants.*;
import static application.model.Constants.REGEX_VALID_NAME;

public class User {
    private Integer id;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    @Pattern(regexp = REGEX_VALID_NAME)
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String address;
    @NotNull
    @Min(1000000000)
    @Max(value = 9999999999L)
    private Long phone;

    public User(){

    }

    public User(int id, String password, String name, String email, String address, Long phone) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
