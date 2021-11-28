package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDetailDao extends CrudDAO<OrderDetail, String> {

    boolean addOrder(Orders orders) throws SQLException, ClassNotFoundException;

    ArrayList<Orders> getOrders() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetail> getAllOrderDetail(String id) throws SQLException, ClassNotFoundException;

    int setMonthIncome(String id) throws SQLException, ClassNotFoundException;

    int setYearIncome(String id) throws SQLException, ClassNotFoundException;

    int setDayIncome(String id) throws SQLException, ClassNotFoundException;

    int getMostMovableItem(int[] arr) throws SQLException, ClassNotFoundException;

    int getLeastMovableItem(int[] arr) throws SQLException, ClassNotFoundException;

    List<String> getOrdersIds() throws SQLException, ClassNotFoundException;

    Orders getOrdersDetail(String id) throws SQLException, ClassNotFoundException;

}
