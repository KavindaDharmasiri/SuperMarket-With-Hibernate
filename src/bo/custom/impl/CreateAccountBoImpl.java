package bo.custom.impl;

import bo.custom.CreateAccountBo;
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

    @Override
    public boolean saveNewUser(NewUserDTO user) throws SQLException, ClassNotFoundException {

        NewUser newUser = new NewUser(user.getName(), user.getPassword(), user.getType());

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(newUser);

        transaction.commit();
        session.close();
        return !save.equals(null);
    }

    @Override
    public List<NewUser> searchUser(String name) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM NewUser WHERE type = :owner_name";
        Query query = session.createQuery(hql);
        query.setParameter("owner_name", name);
        List<NewUser> result = query.list();

        transaction.commit();
        session.close();

        return result;

    }
}
