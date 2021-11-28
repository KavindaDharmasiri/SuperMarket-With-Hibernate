package dao.custom.impl;

import dao.custom.NewUserDAO;
import entity.NewUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class NewUserDaoImpl implements NewUserDAO {
    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(NewUser newUser) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public NewUser search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(NewUser newUser) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(newUser);

        transaction.commit();
        session.close();
        return !save.equals(null);
    }

    @Override
    public List<NewUser> searchUser(String id) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM NewUser WHERE type = :owner_name";
        Query query = session.createQuery(hql);
        query.setParameter("owner_name", id);
        List<NewUser> result = query.list();

        transaction.commit();
        session.close();

        return result;

    }
}
