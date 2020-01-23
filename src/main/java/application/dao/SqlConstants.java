package application.dao;

public interface SqlConstants {
    String QUERY_USER_GET_ALL = "SELECT * FROM user";
    String QUERY_USER_GET_PASSWORD_BY_ID = "SELECT password FROM user WHERE id = :id";
    String QUERY_USER_GET_BY_EMAIL = "SELECT * FROM user WHERE email = :email";
    String QUERY_USER_ADD = "INSERT INTO user (name, password, email, address, phone) VALUES (:name, :password, :email, :address, :phone)";
    String QUERY_USER_UPDATE = "UPDATE user SET name = :name , password = :password , email = :email , address = :address , phone = :phone WHERE (id = :id)";
    String QUERY_USER_DELETE = "DELETE FROM user WHERE (id = :id)";

    String QUERY_PRODUCT_GET_ALL = "SELECT * FROM product";
    String QUERY_PRODUCT_ADD = "INSERT INTO product (name, category, description, price, currency, quantity) VALUES (:name, :category, :description, :price, :currency, :quantity)";
    String QUERY_PRODUCT_UPDATE = "UPDATE product SET name = :name , category = :category, description = :description, price = :price, currency = :currency, quantity = quantity + :quantity WHERE (id = :id)";
    String QUERY_PRODUCT_UPDATE_QUANTITY = "UPDATE product SET quantity = quantity + :quantity WHERE (id = :id)";
    String QUERY_PRODUCT_DELETE = "DELETE FROM product WHERE (id = :id)";
    String QUERY_PRODUCT_SEARCH_ALL = "SELECT * FROM product WHERE name LIKE CONCAT(:name , '%') ORDER BY id LIMIT :limit OFFSET :offset";
    String QUERY_PRODUCT_SEARCH_ALL_CATEGORY = "SELECT * FROM product WHERE category = :category AND name LIKE CONCAT(:name , '%') ORDER BY id LIMIT :limit OFFSET :offset";
    String QUERY_PRODUCT_QUANTITY = "SELECT quantity FROM product WHERE id = :id";
    String QUERY_PRODUCT_UPDATE_AFTER_ORDER = "UPDATE cart JOIN product ON cart.productId = product.id SET product.quantity = product.quantity - cart.quantity WHERE cart.userId = :userId";

    String QUERY_CART_ADD = "INSERT INTO cart (userId, productId, quantity) VALUES (:userId , :productId , :quantity)";
    String QUERY_CART_DELETE = "DELETE FROM cart WHERE userId = :userId AND productId = :productId";
    String QUERY_CART_DELETE_FOR_USER = "DELETE FROM cart WHERE userId = :userId";
    String QUERY_CART_GET_JOIN = "SELECT cart.userId, cart.productId, cart.quantity, product.name, product.category, product.description, product.price, product.currency FROM cart JOIN product ON cart.productId = product.id WHERE cart.userId = :userId";
    String QUERY_CART_GET_JOIN_FOR_ORDER = "SELECT cart.userId, cart.productId, cart.quantity as cartQuantity, product.name, product.price, product.currency, product.quantity as productQuantity FROM cart JOIN product ON cart.productId = product.id WHERE cart.userId = :userId FOR UPDATE";
    String QUERY_CART_GET_ALL = "SELECT * FROM cart";
    String QUERY_CART_UPDATE_QUANTITY = "UPDATE cart SET quantity = quantity + :quantity WHERE userId = :userId AND productId = :productId";
    String QUERY_CART_QUANTITY = "SELECT quantity FROM cart WHERE userId = :userId AND productId = :productId";

    String QUERY_ORDER_GET_BY_USER_ID = "SELECT * FROM orderItem WHERE userId = :userId";
    String QUERY_ORDER_GET_BY_ORDER_ID = "SELECT * FROM orderItem WHERE orderId = :orderId";
    String QUERY_ORDER_ADD = "INSERT INTO orderItem (userId, orderDescription, amount, currency) VALUES (:userId , :orderDescription, :amount, :currency)";
    String QUERY_ORDER_DELETE = "DELETE FROM orderItem WHERE (orderId = :orderId)";

    String COLUMN_USER_ID = "id";
    String COLUMN_USER_NAME = "name";
    String COLUMN_USER_PASSWORD = "password";
    String COLUMN_USER_EMAIL = "email";
    String COLUMN_USER_ADDRESS = "address";
    String COLUMN_USER_PHONE = "phone";

    String COLUMN_PRODUCT_ID = "id";
    String COLUMN_PRODUCT_NAME = "name";
    String COLUMN_PRODUCT_CATEGORY = "category";
    String COLUMN_PRODUCT_DESCRIPTION = "description";
    String COLUMN_PRODUCT_PRICE = "price";
    String COLUMN_PRODUCT_CURRENCY = "currency";
    String COLUMN_PRODUCT_QUANTITY = "quantity";

    String COLUMN_CART_USER_ID = "userId";
    String COLUMN_CART_PRODUCT_ID = "productId";
    String COLUMN_CART_QUANTITY = "quantity";

    String COLUMN_ORDER_ID = "orderId";
    String COLUMN_ORDER_USER_ID = "userId";
    String COLUMN_ORDER_DATE_CREATED = "dateCreated";
    String COLUMN_ORDER_DESCRIPTION = "orderDescription";
    String COLUMN_ORDER_AMOUNT = "amount";
    String COLUMN_ORDER_CURRENCY = "currency";

    String SQL_PAGE_OFFSET = "offset";
    String SQL_PAGE_LIMIT = "limit";


}
