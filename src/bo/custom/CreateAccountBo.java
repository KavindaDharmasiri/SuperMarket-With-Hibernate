package bo.custom;

import bo.SuperBO;
import dto.NewUserDTO;
import entity.NewUser;

import java.sql.SQLException;
import java.util.List;

public interface CreateAccountBo extends SuperBO {
    boolean saveNewUser(NewUserDTO user) throws SQLException, ClassNotFoundException;

    List<NewUser> searchUser(String name) throws SQLException, ClassNotFoundException;
}
