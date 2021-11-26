package dto;

public class OrderDTO {
    private String orderId;
    private String date;
    private String custId;

    public OrderDTO(String orderId, String date, String custId) {
        this.orderId = orderId;
        this.date = date;
        this.custId = custId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", custId='" + custId + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
