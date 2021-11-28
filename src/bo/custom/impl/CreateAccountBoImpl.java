package bo.custom.impl;

import bo.BoFactory;
import bo.custom.CreateAccountBo;
import bo.custom.ItemBo;
import dao.DAOFactory;
import dao.custom.NewUserDAO;
import dto.NewUserDTO;
import entity.NewUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class CreateAccountBoImpl implements CreateAccountBo {

    private final NewUserDAO newUserDAO = (NewUserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.NEW_USER);
    @Override
    public boolean saveNewUser(NewUserDTO user) throws SQLException, ClassNotFoundException {
        NewUser newUser = new NewUser(user.getName(), user.getPassword(), user.getType());
        return newUserDAO.add(newUser);
    }

    @Override
    public List<NewUser> searchUser(String name) throws SQLException, ClassNotFoundException {
        return newUserDAO.searchUser(name);
    }
}
