package bo.custom.impl;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailDao;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    private final OrderDetailDao orderDetailDao = (OrderDetailDao) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public boolean addOrder(OrderDetail order) throws SQLException, ClassNotFoundException {
        OrderDetail orderDetail = new OrderDetail(order.getOrderId(), order.getCustomer(), order.getItem(), order.getOrderQty(), order.getDiscount());
        return orderDetailDao.add(orderDetail);

    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDetailDao.delete(id);
    }

    @Override
    public boolean updateOrder(OrderDetail order) throws SQLException, ClassNotFoundException {
        return orderDetailDao.update(order);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDetailDao.getId();
    }

    @Override
    public OrderDetail getOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDetailDao.search(id);
    }

    @Override
    public List<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        return orderDetailDao.getAllIds();
    }

    @Override
    public ArrayList<OrderDetail> getAllOrderDetail(String id) throws SQLException, ClassNotFoundException {
        return orderDetailDao.getAllOrderDetail(id);
    }

    @Override
    public boolean addOrders(Orders orders) throws SQLException, ClassNotFoundException {
        return orderDetailDao.addOrder(orders);
    }

    @Override
    public ArrayList<Orders> getOrders() throws SQLException, ClassNotFoundException {
        return orderDetailDao.getOrders();
    }

    @Override
    public int getYearIncome(String date) throws SQLException, ClassNotFoundException {
        return orderDetailDao.setYearIncome(date);
    }

    @Override
    public int getMonthIncome(String date) throws SQLException, ClassNotFoundException {
        return orderDetailDao.setMonthIncome(date);
    }

    @Override
    public int getDayIncome(String date) throws SQLException, ClassNotFoundException {
        return orderDetailDao.setDayIncome(date);
    }

    @Override
    public int getMostMovableItem(int[] arr) throws SQLException, ClassNotFoundException {
        return orderDetailDao.getMostMovableItem(arr);
    }

    @Override
    public int getLeastMovableItem(int[] arr) throws SQLException, ClassNotFoundException {
        return orderDetailDao.getLeastMovableItem(arr);
    }

    @Override
    public List<String> getOrdersIds() throws SQLException, ClassNotFoundException {
        return orderDetailDao.getOrdersIds();
    }

    @Override
    public Orders getOrdersDetail(String id) throws SQLException, ClassNotFoundException {
        return orderDetailDao.getOrdersDetail(id);
    }

}
