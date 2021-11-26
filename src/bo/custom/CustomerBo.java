package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBO {
    boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException;

    String generateNewCustomerID() throws SQLException, ClassNotFoundException;

    Customer getCustomer(String id) throws SQLException, ClassNotFoundException;

    List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
}
