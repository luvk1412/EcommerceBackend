package application.model;

import javax.validation.constraints.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static application.dao.SqlConstants.*;
import static application.model.Constants.REGEX_VALID_NAME;
import static application.model.Constants.REGEX_VALID_NON_EMPTY;

public class UserUpdateObject {
    private Integer id;
    @Pattern(regexp = REGEX_VALID_NON_EMPTY)
    private String password;
    @Pattern(regexp = REGEX_VALID_NAME)
    private String name;
    @Email
    private String email;
    @Pattern(regexp = REGEX_VALID_NON_EMPTY)
    private String address;
    @Min(1000000000)
    @Max(value = 9999999999L)
    private Long phone;

    public UserUpdateObject(){

    }

    public UserUpdateObject(int id, String password, String name, String email, String address, Long phone) {
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

    public String getUpdateQuery() throws IllegalAccessException {
        List<Field> fields = Arrays.asList(this.getClass().getDeclaredFields());
        StringBuilder queryPart = new StringBuilder();
        boolean isSingleField = true;
        for(Field field : fields){
            if(field.getName().equals(COLUMN_USER_ID))
                continue;
            if(field.get(this) != null){
                if(isSingleField) {
                    queryPart.append(QUERY_BUILDER_SET_START.replace(QUERY_BUILDER_ATTRIBUTE_FIELD, field.getName()));
                    isSingleField = false;
                }else {
                    queryPart.append(QUERY_BUILDER_SET.replace(QUERY_BUILDER_ATTRIBUTE_FIELD, field.getName()));
                }
            }
        }
        return QUERY_BUILDER_UPDATE.replace(QUERY_BUILDER_ATTRIBUTE_TABLE, TABLE_USER).replace(QUERY_BUILDER_ATTRIBUTE_FIELDS, queryPart);
    }
}
