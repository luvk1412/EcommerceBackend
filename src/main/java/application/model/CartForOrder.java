package application.model;

public class CartForOrder {
    private int productId;
    private String name;
    private int cartQuantity;
    private int productQuantity;
    private int price;
    private String currency;


    public CartForOrder(){

    }

    public CartForOrder(int productId, String name, int cartQuantity, int productQuantity, int price, String currency) {
        this.productId = productId;
        this.name = name;
        this.cartQuantity = cartQuantity;
        this.productQuantity = productQuantity;
        this.price = price;
        this.currency = currency;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
