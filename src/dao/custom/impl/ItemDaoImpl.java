package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDAO {

    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(item);

        transaction.commit();
        session.close();
        return !save.equals(null);
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        Item item = search(s);

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(item);

        transaction.commit();
        session.close();

        return true;

    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Item SET qtyOnHand = :item_qty , description = :item_description , packSize = :item_size , unitPrice = :item_price WHERE itemCode = :item_id";
        Query query = session.createQuery(hql);
        query.setParameter("item_qty", item.getQtyOnHand());
        query.setParameter("item_description", item.getDescription());
        query.setParameter("item_size", item.getPackSize());
        query.setParameter("item_price", item.getUnitPrice());
        query.setParameter("item_id", item.getItemCode());
        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();

        return rowCount > 0;

    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Item WHERE itemCode = :cust_Id";
        Query query = session.createQuery(hql);
        query.setParameter("cust_Id", s);
        List<Item> result = query.list();
        Item cust = null;
        for (Item customer : result) {
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

        String hql = "SELECT itemCode FROM Item";
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

        String hql = "SELECT itemCode FROM Item ORDER BY itemCode DESC";
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

                return "I-00" + tempId;
            } else if (tempId < 99) {
                return "I-0" + tempId;
            } else {
                return "I-" + tempId;
            }
        }
        return "I-001";
    }

}
