package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Orders {
    @Id
    private String orderId;
    private String orderDate;
    @ManyToOne
    private Customer customer;
    private int total;

    public Orders() {
    }

    public Orders(String orderId, String orderDate, Customer customer, int total) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
