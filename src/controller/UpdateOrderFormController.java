package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import entity.Item;
import entity.OrderDetail;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateOrderFormController {
    private static Stage newStage;
    private final OrderBo order = (OrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    private final ItemBo itemBo = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public AnchorPane updateOrder;
    public Label orderId;
    public TextField orderQty;
    public ComboBox itemCodecmb;
    public TextField discount;
    public Button btnUpdate;
    private String value;
    private int Qty;
    private int q;
    private double dis;

    public static void setStage(Stage stage) {
        newStage = stage;
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        OrderDetail orders = this.order.getOrder(orderId.getText());
        Item item = itemBo.getItem(value);
        OrderDetail c1 = new OrderDetail(
                orderId.getText(), orders.getCustomer(), item, Integer.parseInt(orderQty.getText()), dis);

        if (this.order.updateOrder(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            newStage.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        newStage.close();
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        btnUpdate.setDisable(true);
        loadItemIds();

        itemCodecmb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            try {
                Item item = itemBo.getItem(value);
                Qty = item.getQtyOnHand();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = itemBo.getAllItemIds();
        itemCodecmb.getItems().addAll(itemIds);
    }

    public void qtyOnHand(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = orderQty.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            q = 1;
            orderQty.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            q = 0;
            orderQty.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    private void setbtn(boolean b) {
        btnUpdate.setDisable(q != 1);
    }

    public void setOrder(OrderDetail od) {
        orderId.setText(od.getOrderId());
        orderQty.setText(String.valueOf(od.getOrderQty()));
        dis = od.getDiscount();
    }
}
