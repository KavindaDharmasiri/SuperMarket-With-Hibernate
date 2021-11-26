package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBO {
    boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    String generateNewItemID() throws SQLException, ClassNotFoundException;

    Item getItem(String id) throws SQLException, ClassNotFoundException;

    List<String> getAllItemIds() throws SQLException, ClassNotFoundException;
}
