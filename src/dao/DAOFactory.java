package dao;

import dao.custom.impl.CustomerDaoImpl;
import dao.custom.impl.ItemDaoImpl;
import dao.custom.impl.NewUserDaoImpl;
import dao.custom.impl.OrderDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDaoImpl();
            case ITEM:
                return new ItemDaoImpl();
            case ORDER:
                return new OrderDaoImpl();
            case NEW_USER:
                return new NewUserDaoImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        CUSTOMER, ITEM, ORDER, NEW_USER
    }

}
