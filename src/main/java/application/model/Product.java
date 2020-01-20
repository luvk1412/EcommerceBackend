package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import static application.dao.SqlConstants.*;

public class Product {
    private Integer id;
    private String name;
    private String category;
    private String description;
    private Integer price;
    private String currency;
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

    public Product(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(COLUMN_PRODUCT_ID);
        this.name = resultSet.getString(COLUMN_PRODUCT_NAME);
        this.category = resultSet.getString(COLUMN_PRODUCT_CATEGORY);
        this.description = resultSet.getString(COLUMN_PRODUCT_DESCRIPTION);
        this.price = resultSet.getInt(COLUMN_PRODUCT_PRICE);
        this.currency = resultSet.getString(COLUMN_PRODUCT_CURRENCY);
        this.quantity = resultSet.getInt(COLUMN_PRODUCT_QUANTITY);
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
