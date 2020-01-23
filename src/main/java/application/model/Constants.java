package application.model;

public class Constants {
    public static final Object NULL = null;

    public static final String HEADER_USER_ID = "user-id";
    public static final String HEADER_PASSWORD = "password";

    public static final int CODE_POST_SUCCESS = 201;
    public static final String MESSAGE_UPDATE_SUCCESS = "Update Successful";
    public static final String MESSAGE_DELETE_SUCCESS = "Delete Successful";
    public static final int CODE_UNAUTHORISED = 401;
    public static final String MESSAGE_UNAUTHORISED = "Unauthorised Access : User invalid or password incorrect or tokens missing";
    public static final int CODE_INVALID = 301;
    public static final String MESSAGE_INVALID_LOGIN = "Email or password incorrect";
    public static final String MESSAGE_INVALID_USER_ID = "Invalid User ID : Id is not present";
    public static final String MESSAGE_INVALID_PRODUCT_ID = "Invalid Product ID";
    public static final String MESSAGE_INVALID_CART_ID = "Invalid Cart ID : Id is null, non-numeric or not present";
    public static final String MESSAGE_INVALID_NAME = "Invalid Name";
    public static final String MESSAGE_INVALID_EMAIL = "Invalid Email";
    public static final String MESSAGE_INVALID_ADDRESS = "Invalid Address : Address cannot be empty";
    public static final String MESSAGE_INVALID_PASSWORD = "Invalid Password";
    public static final String MESSAGE_INVALID_ORDER_ID = "Invalid Order Id : Not found";
    public static final String MESSAGE_INVALID_PHONE = "Invalid Mobile Number : Mobile number can have only numeric digits and should have length 10";
    public static final String MESSAGE_INVALID_DESCRIPTION = "Invalid Description : Cannot be empty";
    public static final String MESSAGE_INVALID_CURRENCY = "Invalid Currency : Cannot be empty ";
    public static final String MESSAGE_INVALID_CATEGORY = "Invalid Category : Cannot be empty";
    public static final String MESSAGE_INVALID_QUANTITY = "Invalid Quantity : Quantity not present or is invalid";
    public static final String MESSAGE_INVALID_PRICE = "Invalid Price : Price not present or is invalid";
    public static final String MESSAGE_INVALID_PAGE = "Invalid Page Number : Either page number is less than equal to zero or it has exceed the page limit";
    public static final String MESSAGE_INVALID_LIMIT = "Invalid Page Limit : Limit needs to be a positive Integer";
    public static final int CODE_DUPLICATE = 300;
    public static final String MESSAGE_DUPLICATE_EMAIL = "Email already present";
    public static final int CODE_EXCEEDED = 302;
    public static final String MESSAGE_EXCEEDED_PRODUCT_DEC = "Not enough items in inventory to remove";
    public static final String MESSAGE_EXCEEDED_CART_ADD = "Item cannot be added to cart as requested quantity is not present in inventory";
    public static final String MESSAGE_EXCEEDED_CART_DEC = "Not enough items in cart to remove";
    public static final String MESSAGE_INVALID_CART_ITEM = "Item not present in cart for given user";
    public static final String MESSAGE_EMPTY_CART = "Cart for the given user is empty";
    public static final String REGEX_VALID_NAME = "(^[A-Za-z]+[A-Za-z .]*[A-Za-z]+$)|(^[A-Za-z]+$)";
    public static final String REGEX_VALID_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static final String REGEX_VALID_PHONE = "[0-9]{10}";
    public static final String REGEX_VALID_NON_EMPTY = ".+";

    public static final String FILTER_SEARCH_PRODUCT_ALL = "all";

    public static final String EMPTY = "";
    public static final String HI_FUN = "-";
    public static final int HEX_BASE = 16;

}
