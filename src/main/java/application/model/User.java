package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import static application.dao.SqlConstants.*;

public class User {
    private Integer id;
    private String password;
    private String name;
    private String email;
    private String address;
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
    public User(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(COLUMN_USER_ID);
        this.password = resultSet.getString(COLUMN_USER_PASSWORD);
        this.name = resultSet.getString(COLUMN_USER_NAME);
        this.email = resultSet.getString(COLUMN_USER_EMAIL);
        this.address = resultSet.getString(COLUMN_USER_ADDRESS);
        this.phone = resultSet.getLong(COLUMN_USER_PHONE);
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
