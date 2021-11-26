package bo.custom;

import bo.SuperBO;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderBo extends SuperBO {
    boolean addOrder(OrderDetail order) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrderDetail order) throws SQLException, ClassNotFoundException;

    String generateNewOrderID() throws SQLException, ClassNotFoundException;

    OrderDetail getOrder(String id) throws SQLException, ClassNotFoundException;

    List<String> getAllOrderIds() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetail> getAllOrderDetail(String id) throws SQLException, ClassNotFoundException;

    boolean addOrders(Orders orders) throws SQLException, ClassNotFoundException;

    ArrayList<Orders> getOrders() throws SQLException, ClassNotFoundException;

    int getYearIncome(String date) throws SQLException, ClassNotFoundException;

    int getMonthIncome(String date) throws SQLException, ClassNotFoundException;

    int getDayIncome(String date) throws SQLException, ClassNotFoundException;

    int getMostMovableItem(int[] arr) throws SQLException, ClassNotFoundException;

    int getLeastMovableItem(int[] arr) throws SQLException, ClassNotFoundException;

    List<String> getOrdersIds() throws SQLException, ClassNotFoundException;

    Orders getOrdersDetail(String id) throws SQLException, ClassNotFoundException;

}
