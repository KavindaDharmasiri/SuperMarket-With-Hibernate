package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeOrderPaymentController {
    private static Stage newStage;
    public AnchorPane placeMoney;
    public JFXComboBox cmbOrder;
    public Label lblPrice;
    public JFXButton makeBtn;
    OrderBo orderBo = (OrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    ItemBo itemBo = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    int totl;
    private String value;
    private String tempDate;

    public static void setStage(Stage stage) {
        newStage = stage;
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        makeBtn.setDisable(true);
        loadOrderIds();
        loadDate();

        cmbOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            int total = 0;
            try {
                total = getTotal(value);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            lblPrice.setText(String.valueOf(total));
            makeBtn.setDisable(false);
        });
    }

    private int getTotal(String value) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> order = orderBo.getAllOrderDetail(value);
        for (OrderDetail od : order) {
            Item item = itemBo.getItem(od.getItem().getItemCode());
            totl += od.getOrderQty() * item.getUnitPrice();
        }
        return totl;
    }

    private void loadOrderIds() throws SQLException, ClassNotFoundException {
        List<String> allOrderIds = orderBo.getAllOrderIds();
        ArrayList<Orders> orders = orderBo.getOrders();

        ArrayList<String> newIds = new ArrayList<>();
        for (int i = 0; i < allOrderIds.size(); i++) {
            int tmp = 0;
            for (int j = 0; j < orders.size(); j++) {
                if (allOrderIds.get(i).equals(orders.get(j).getOrderId())) {
                    tmp = 1;
                }
            }
            if (tmp != 1) {
                newIds.add(allOrderIds.get(i));
            }
        }
        cmbOrder.getItems().addAll(newIds);
    }

    public void makePayment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        OrderDetail order = orderBo.getOrder(value);

        boolean b = orderBo.addOrders(new Orders(value, tempDate, order.getCustomer(), totl));

        if (b)
            new Alert(Alert.AlertType.CONFIRMATION, "Aded...").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        tempDate = f.format(date);
    }

    public void ExitBtn(ActionEvent actionEvent) {
        newStage.close();
    }
}
