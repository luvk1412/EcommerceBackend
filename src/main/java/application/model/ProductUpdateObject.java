package application.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static application.dao.SqlConstants.*;
import static application.model.Constants.REGEX_VALID_NON_EMPTY;

public class ProductUpdateObject {
    private Integer id;
    @Pattern(regexp = REGEX_VALID_NON_EMPTY)
    private String name;
    @Pattern(regexp = REGEX_VALID_NON_EMPTY)
    private String category;
    @Pattern(regexp = REGEX_VALID_NON_EMPTY)
    private String description;
    @Min(0)
    private Integer price;
    @Pattern(regexp = REGEX_VALID_NON_EMPTY)
    private String currency;

    private Integer quantity;

    public ProductUpdateObject(){

    }

    public ProductUpdateObject(int id, String name, String category, String description, int price, String currency, int quantity) {
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

    public String getUpdateQuery() throws IllegalAccessException {
        List<Field> fields = Arrays.asList(this.getClass().getDeclaredFields());
        StringBuilder queryPart = new StringBuilder();
        boolean isSingleField = true;
        for(Field field : fields){
            if(field.getName().equals(COLUMN_PRODUCT_ID))
                continue;
            if(field.get(this) != null){
                if(isSingleField) {
                    if(field.getName().equals(COLUMN_PRODUCT_QUANTITY)) {
                        queryPart.append(QUERY_BUILDER_SET_UPDATE_START.replace(QUERY_BUILDER_ATTRIBUTE_FIELD, field.getName()));
                    }
                    else{
                        queryPart.append(QUERY_BUILDER_SET_START.replace(QUERY_BUILDER_ATTRIBUTE_FIELD, field.getName()));
                    }
                    isSingleField = false;
                }else {
                    if(field.getName().equals(COLUMN_PRODUCT_QUANTITY)) {
                        queryPart.append(QUERY_BUILDER_SET_UPDATE.replace(QUERY_BUILDER_ATTRIBUTE_FIELD, field.getName()));
                    }
                    else{
                        queryPart.append(QUERY_BUILDER_SET.replace(QUERY_BUILDER_ATTRIBUTE_FIELD, field.getName()));
                    }
                }
            }
        }
        return QUERY_BUILDER_UPDATE.replace(QUERY_BUILDER_ATTRIBUTE_TABLE, TABLE_PRODUCT).replace(QUERY_BUILDER_ATTRIBUTE_FIELDS, queryPart);
    }
}
