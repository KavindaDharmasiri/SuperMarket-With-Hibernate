package bo.custom.impl;

import bo.custom.CustomerBo;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getTitle(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getCity(), customerDTO.getProvince(), customerDTO.getPostalCode());
        return customerDAO.add(customer);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return customerDAO.update(customer);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.getId();
    }

    @Override
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllIds();
    }
}
