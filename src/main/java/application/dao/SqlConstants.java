package application.dao;

public class SqlConstants {
    public static final String QUERY_USER_GET_ALL = "SELECT * FROM user";
    public static final String QUERY_USER_ADD = "INSERT INTO user (name, password, email, address, phone) VALUES (:name, :password, :email, :address, :phone)";
    public static final String QUERY_USER_UPDATE = "UPDATE user SET name = :name , password = :password , email = :email , address = :address , phone = :phone WHERE (id = :id)";
    public static final String QUERY_USER_DELETE = "DELETE FROM user WHERE (id = :id)";

    public static final String QUERY_PRODUCT_GET_ALL = "SELECT * FROM product";
    public static final String QUERY_PRODUCT_ADD = "INSERT INTO product (name, category, description, price, currency, quantity) VALUES (:name, :category, :description, :price, :currency, :quantity)";
    public static final String QUERY_PRODUCT_UPDATE = "UPDATE product SET name = :name , category = :category, description = :description, price = :price, currency = :currency WHERE (id = :id)";
    public static final String QUERY_PRODUCT_UPDATE_QUANTITY = "UPDATE product SET quantity = quantity + :quantity WHERE (id = :id)";
    public static final String QUERY_PRODUCT_DELETE = "DELETE FROM product WHERE (id = :id)";
    public static final String QUERY_PRODUCT_SEARCH_ALL = "SELECT * FROM product WHERE name LIKE CONCAT(:name , '%') ORDER BY name";
    public static final String QUERY_PRODUCT_SEARCH_ALL_CATEGORY = "SELECT * FROM product WHERE category = :category AND name LIKE CONCAT(:name , '%') ORDER BY name";

    public static final String QUERY_CART_ADD = "INSERT INTO cart (userId, productId, quantity) VALUES (:userId , :productId , :quantity)";
    public static final String QUERY_CART_USER_ID_COUNT = "SELECT count(*) as count FROM cart where id = :id";
    public static final String QUERY_CART_DELETE = "DELETE FROM cart WHERE (id = :id)";
    public static final String QUERY_CART_GET = "SELECT cart.id, cart.userId, cart.productId, cart.quantity, product.name, product.category, product.description, product.price, product.currency FROM cart JOIN product ON cart.productId = product.id WHERE cart.userId = :userId";

    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_ADDRESS = "address";
    public static final String COLUMN_USER_PHONE = "phone";

    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_CATEGORY = "category";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_CURRENCY = "currency";
    public static final String COLUMN_PRODUCT_QUANTITY = "quantity";

    public static final String COLUMN_CART_ID = "id";
    public static final String COLUMN_CART_USER_ID = "userId";
    public static final String COLUMN_CART_PRODUCT_ID = "productId";
    public static final String COLUMN_CART_QUANTITY = "quantity";

    public static final String COLUMN_GENERAL_COUNT = "count";

}
