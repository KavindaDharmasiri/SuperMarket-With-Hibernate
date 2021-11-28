package dao.custom;

import dao.CrudDAO;
import entity.NewUser;

import java.sql.SQLException;
import java.util.List;

public interface NewUserDAO extends CrudDAO<NewUser, String> {
    boolean add(NewUser newUser) throws SQLException, ClassNotFoundException;

    List<NewUser> searchUser(String id) throws SQLException, ClassNotFoundException;
}
