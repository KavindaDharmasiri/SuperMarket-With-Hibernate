package bo.custom.impl;

import bo.custom.ItemBo;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getPackSize(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand());
        return itemDAO.add(item);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getPackSize(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand());
        return itemDAO.update(item);
    }

    @Override
    public String generateNewItemID() throws SQLException, ClassNotFoundException {
        return itemDAO.getId();
    }


    @Override
    public Item getItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.search(id);
    }

    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllIds();
    }
}
