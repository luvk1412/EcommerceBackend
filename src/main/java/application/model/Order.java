package application.model;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private int amount;
    private String currency;
    private Timestamp dateCreated;
    private List<OrderProductDescription> orderProductDescriptions;

    public Order(){

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderProductDescription> getOrderProductDescriptions() {
        return orderProductDescriptions;
    }

    public void setOrderProductDescriptions(List<OrderProductDescription> orderProductDescriptions) {
        this.orderProductDescriptions = orderProductDescriptions;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}
