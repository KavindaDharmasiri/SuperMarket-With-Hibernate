package dao.custom.impl;

import bo.BoFactory;
import bo.custom.ItemBo;
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

public class OrderDaoImpl implements OrderDetailDao {
    ItemBo itemBo = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    @Override
    public boolean add(OrderDetail order) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(order);

        transaction.commit();
        session.close();
        return !save.equals(null);

    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        OrderDetail order = search(s);

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(order);

        transaction.commit();
        session.close();

        return true;

    }

    @Override
    public boolean update(OrderDetail order) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE OrderDetail SET discount = :order_discount , customer = :cust_id , item = :item_code , orderQty = :order_qty WHERE orderId = :order_id";
        Query query = session.createQuery(hql);
        query.setParameter("order_discount", order.getDiscount());
        query.setParameter("cust_id", order.getCustomer());
        query.setParameter("item_code", order.getItem());
        query.setParameter("order_qty", order.getOrderQty());
        query.setParameter("order_id", order.getOrderId());
        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();

        return rowCount > 0;
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM OrderDetail WHERE orderId = :cust_Id";
        Query query = session.createQuery(hql);
        query.setParameter("cust_Id", s);
        List<OrderDetail> result = query.list();
        OrderDetail cust = null;
        for (OrderDetail customer : result) {
            cust = customer;
        }

        transaction.commit();
        session.close();
        return cust;

    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT orderId FROM OrderDetail";
        Query query = session.createQuery(hql);
        List<String> result = query.list();


        transaction.commit();
        session.close();
        return result;

    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT orderId FROM OrderDetail ORDER BY orderId DESC";
        Query query = session.createQuery(hql);
        List<String> result = query.list();
        List<String> list = new ArrayList<>();

        transaction.commit();
        session.close();


        for (String name : result) {
            list.add(name);
        }

        if (list.size() > 0) {
            int tempId = Integer.
                    parseInt(list.get(0).split("-")[1]);
            tempId = tempId + 1;
            list.clear();
            if (tempId < 9) {

                return "O-00" + tempId;
            } else if (tempId < 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        }
        return "O-001";
    }

    @Override
    public boolean addOrder(Orders orders) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(orders);

        transaction.commit();
        session.close();
        return !save.equals(null);

    }

    @Override
    public ArrayList<Orders> getOrders() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Orders";
        Query query = session.createQuery(hql);
        ArrayList<Orders> result = (ArrayList<Orders>) query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public ArrayList<OrderDetail> getAllOrderDetail(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM OrderDetail WHERE orderId = :cust_Id";
        Query query = session.createQuery(hql);
        query.setParameter("cust_Id", id);
        List<OrderDetail> result = query.list();

        ArrayList<OrderDetail> cust = new ArrayList<>();

        for (OrderDetail customer : result) {
            cust.add(customer);
        }


        transaction.commit();
        session.close();
        return cust;

    }

    @Override
    public int setMonthIncome(String id) throws SQLException, ClassNotFoundException {
        int monthIncome = 0;
        List<Orders> od = getOrders();
        for (int i = 0; i < od.size(); i++) {
            String monthDay = od.get(i).getOrderDate().substring(0, 7);

            if (monthDay.equals(id)) {
                int totl = 0;
                ArrayList<OrderDetail> order = getAllOrderDetail(od.get(i).getOrderId());
                for (OrderDetail odd : order) {
                    Item item = itemBo.getItem(odd.getItem().getItemCode());
                    totl += odd.getOrderQty() * item.getUnitPrice();
                }
                monthIncome += totl;
            }
        }
        return monthIncome;
    }

    @Override
    public int setYearIncome(String id) throws SQLException, ClassNotFoundException {
        int yearlyincome = 0;
        List<Orders> od = getOrders();
        for (int i = 0; i < od.size(); i++) {
            String yearDay = od.get(i).getOrderDate().substring(0, 4);
            if (yearDay.equals(id)) {

                int totl = 0;
                ArrayList<OrderDetail> order = getAllOrderDetail(od.get(i).getOrderId());
                for (OrderDetail odd : order) {
                    Item item = itemBo.getItem(odd.getItem().getItemCode());
                    totl += odd.getOrderQty() * item.getUnitPrice();
                }

                yearlyincome += totl;
            }
        }
        return yearlyincome;
    }

    @Override
    public int setDayIncome(String id) throws SQLException, ClassNotFoundException {
        int dayIncome = 0;
        List<Orders> od = getOrders();
        for (int i = 0; i < od.size(); i++) {

            if (od.get(i).getOrderDate().equals(id)) {
                int totl = 0;
                ArrayList<OrderDetail> order = getAllOrderDetail(od.get(i).getOrderId());
                for (OrderDetail odd : order) {
                    Item item = itemBo.getItem(odd.getItem().getItemCode());
                    totl += odd.getOrderQty() * item.getUnitPrice();
                }
                dayIncome += totl;
            }
        }
        return dayIncome;

    }

    @Override
    public int getMostMovableItem(int[] arr) throws SQLException, ClassNotFoundException {
        int[] count_arr = new int[100];

        for (int i = 0; i < arr.length; i++) {
            count_arr[arr[i]]++;
        }

        int max_repeated = Integer.MIN_VALUE;
        int max_count = -1;
        for (int i = 0; i < arr.length; i++) {
            if (count_arr[arr[i]] > max_count) {
                max_count = count_arr[arr[i]];
                max_repeated = arr[i];
            }
        }

        return max_repeated;
    }

    @Override
    public int getLeastMovableItem(int[] arr) throws SQLException, ClassNotFoundException {
        int n = arr.length;
        Arrays.sort(arr);

        int min_count = n + 1, res = -1;
        int curr_count = 1;

        for (int i = 1; i < n; i++) {
            if (arr[i] == arr[i - 1])
                curr_count++;
            else {
                if (curr_count < min_count) {
                    min_count = curr_count;
                    res = arr[i - 1];
                }

                curr_count = 1;
            }
        }

        if (curr_count < min_count) {
            min_count = curr_count;
            res = arr[n - 1];
        }

        return res;
    }

    @Override
    public List<String> getOrdersIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT customer FROM Orders";
        Query query = session.createQuery(hql);
        List<String> result = query.list();

        transaction.commit();
        session.close();
        return result;
    }

    @Override
    public Orders getOrdersDetail(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Orders WHERE customer = :cust_Id";
        Query query = session.createQuery(hql);
        query.setParameter("cust_Id", id);
        List<Orders> result = query.list();
        Orders cust = null;
        for (Orders customer : result) {
            cust = customer;
        }

        transaction.commit();
        session.close();
        return cust;

    }

}
