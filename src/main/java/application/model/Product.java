package application.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static application.dao.SqlConstants.*;

public class Product {
    private Integer id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String category;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private Integer price;
    @NotNull
    @NotBlank
    private String currency;
    @NotNull
    private Integer quantity;

    public Product(){

    }

    public Product(int id, String name, String category, String description, int price, String currency, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
