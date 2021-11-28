package dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T, ID> extends SuperDAO {

    boolean add(T t) throws SQLException, ClassNotFoundException;

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException, ClassNotFoundException;

    T search(ID id) throws SQLException, ClassNotFoundException;

    List<String> getAllIds() throws SQLException, ClassNotFoundException;

    String getId() throws SQLException, ClassNotFoundException;

}
