package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;
import entity.OrderDetail;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tm.OrderTm;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MakeOrderFormController {

    private static Stage newStage;
    private final CustomerBo customer = (CustomerBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    private final ItemBo item = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    private final OrderBo order = (OrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    private final ObservableList<OrderDetail> obList = FXCollections.observableArrayList();
    private final ObservableList<OrderTm> obbList = FXCollections.observableArrayList();
    public TableColumn colTotal;
    public TableColumn colDiscount;
    public TableColumn colQTY;
    public TableColumn colCode;
    public TableColumn colId;
    public TableView tblOrder;
    public ComboBox<String> cmbItemIds;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public ComboBox<String> cmbCustomerIds;
    public AnchorPane placeOrder;
    public Label txtTitle;
    public Label txtName;
    public Label txtAddress;
    public Label txtCity;
    public Label txtProvince;
    public Label txtPostalCode;
    public Label txtDescription;
    public Label txtPackSize;
    public Label txtUnitPrice;
    public Label txtQtyOnHand;
    public Label txtTotal;
    public TextField txtQty;
    public Button btnAdd;
    OrderBo orderBo = (OrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    private int cartSelectedRowForRemove = -1;
    private String tem;
    private String value;
    private double dis;
    private int q;

    public static void setStage(Stage stage) {
        newStage = stage;
    }

    public void initialize() {
        btnAdd.setDisable(true);
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        setOrderId();

        try {
            loadCustomerIds();
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                tem = newValue;
                setCustomerData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                value = newValue;
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setOrderId() {
        try {
            lblOrderId.setText(order.generateNewOrderID());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = customer.getAllCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer c1 = customer.getCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtTitle.setText(c1.getCustTitle());
            txtName.setText(c1.getCustName());
            txtAddress.setText(c1.getCustAddress());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtPostalCode.setText(c1.getPostalCode());
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = item.getAllItemIds();
        cmbItemIds.getItems().addAll(itemIds);
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i1 = item.getItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i1.getDescription());
            txtPackSize.setText(String.valueOf(i1.getPackSize()));
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String orderId = lblOrderId.getText();
        String itemCode = value;
        int orderQty = Integer.parseInt(txtQty.getText());
        int QtyOhHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = orderQty * unitPrice;

        if (orderQty == 0) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        if (QtyOhHand < orderQty) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        OrderTm tm = new OrderTm(
                orderId,
                itemCode,
                orderQty,
                10,
                total
        );

        txtQtyOnHand.setText(String.valueOf(orderQty));

        Customer customer = this.customer.getCustomer(tem);
        int rowNumber = isExists(tm);
        if (rowNumber == -1) {
            Item item1 = item.getItem(tm.getItemCode());
            obList.add(new OrderDetail(tm.getOrderId(), customer, item1, tm.getOrderQty(), tm.getDiscount()));
            obbList.add(tm);
        } else {
            OrderDetail temp = obList.get(rowNumber);
            OrderTm orderTm = obbList.get(rowNumber);
            dis = orderTm.getOrderQty() * Double.parseDouble(txtUnitPrice.getText());
            OrderDetail newTm = new OrderDetail(
                    temp.getOrderId(),
                    customer,
                    temp.getItem(),
                    temp.getOrderQty() + orderQty,
                    dis
            );

            OrderTm newT = new OrderTm(temp.getOrderId(),
                    temp.getItem().getItemCode(),
                    temp.getOrderQty() + orderQty,
                    dis,
                    total
            );

            obList.remove(rowNumber);
            obbList.remove(rowNumber);
            obbList.add(newT);
            obList.add(newTm);
        }
        tblOrder.setItems(obbList);
        calculateCost();
    }

    private void calculateCost() {
        double ttl = 0;
        for (OrderTm tm : obbList
        ) {
            ttl += tm.getTotal();
        }
        txtTotal.setText(ttl + " /=");
    }


    private int isExists(OrderTm tm) {
        for (int i = 0; i < obbList.size(); i++) {
            if (tm.getItemCode().equals(obbList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obbList.remove(cartSelectedRowForRemove);
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblOrder.refresh();
        }
    }


    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean b = false;
        ArrayList<OrderDetail> items = new ArrayList<>();
        for (OrderDetail tempTm : obList) {
            Customer customer1 = this.customer.getCustomer(tem);
            b = order.addOrder(new OrderDetail(tempTm.getOrderId(), customer1 , tempTm.getItem(), tempTm.getOrderQty(), tempTm.getDiscount()));
            Item med = item.getItem(tempTm.getItem().getItemCode());
            int newQty = 0;
            if (med != null) {
                newQty = med.getQtyOnHand() - tempTm.getOrderQty();
            }
            item.updateItem(new ItemDTO(med.getItemCode(), med.getDescription(), med.getPackSize(), med.getUnitPrice(), newQty));
            items.add(new OrderDetail(tempTm.getOrderId(), customer1, tempTm.getItem(), tempTm.getOrderQty(), tempTm.getDiscount()));
        }

        if (b) {
            tblOrder.getItems().clear();
            tblOrder.refresh();
            calculateCost();
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setOrderId();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        newStage.close();
    }

    public void qtyOnHand(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = txtQtyOnHand.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            q = 1;
            txtQtyOnHand.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            q = 0;
            txtQtyOnHand.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    private void setbtn(boolean b) {
        btnAdd.setDisable(q != 1);
    }
}
