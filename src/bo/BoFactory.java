package bo;

import bo.custom.impl.CreateAccountBoImpl;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderBoImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBoImpl();
            case CUSTOMER:
                return new CustomerBoImpl();
            case ORDER:
                return new OrderBoImpl();
            case CREATE_ACCOUNT:
                return new CreateAccountBoImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, ORDER, CREATE_ACCOUNT
    }
}
