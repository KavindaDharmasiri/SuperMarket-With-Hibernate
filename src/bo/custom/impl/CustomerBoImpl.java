package bo.custom.impl;

import bo.custom.CustomerBo;
import dto.CustomerDTO;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getTitle(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getCity(), customerDTO.getProvince(), customerDTO.getPostalCode());

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(customer);

        transaction.commit();
        session.close();
        return !save.equals(null);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = getCustomer(id);

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(customer);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Customer SET custName = :cust_name , custTitle = :cust_title , custAddress = :cust_Address , city = :cust_City , province = :cust_Province , postalCode = :cust_PostalCode WHERE custId = :cust_id";
        Query query = session.createQuery(hql);
        query.setParameter("cust_id", customer.getCustId());
        query.setParameter("cust_name", customer.getCustName());
        query.setParameter("cust_title", customer.getCustTitle());
        query.setParameter("cust_Address", customer.getCustAddress());
        query.setParameter("cust_City", customer.getCity());
        query.setParameter("cust_Province", customer.getProvince());
        query.setParameter("cust_PostalCode", customer.getPostalCode());

        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();

        return rowCount > 0;

    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT custId FROM Customer ORDER BY custId DESC";
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

                return "C-00" + tempId;
            } else if (tempId < 99) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }
        }
        return "C-001";

    }

    @Override
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Customer WHERE custId = :cust_Id";
        Query query = session.createQuery(hql);
        query.setParameter("cust_Id", id);
        List<Customer> result = query.list();
        Customer cust = null;
        for (Customer customer : result) {
            cust = customer;
        }

        transaction.commit();
        session.close();
        return cust;
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT custId FROM Customer";
        Query query = session.createQuery(hql);
        List<String> result = query.list();

        transaction.commit();
        session.close();
        return result;
    }
}
