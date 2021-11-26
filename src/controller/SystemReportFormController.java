package controller;

import bo.BoFactory;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXComboBox;
import entity.OrderDetail;
import entity.Orders;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemReportFormController {
    private final OrderBo orderBo = (OrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    private final ArrayList<Integer> list = new ArrayList<Integer>();
    public AnchorPane systemReport;
    public Label yearIncome;
    public Label monthIncome;
    public Label todayIncome;
    public Label leastMovableItem;
    public Label mostMovableItem;
    public JFXComboBox cmbcustomerId;
    public Label lblTotal;
    private int[] arr;
    private ArrayList<Orders> orders;
    private String temp;
    private String value;

    {
        try {
            orders = orderBo.getOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        initArray();
        int mostMovableIte = orderBo.getMostMovableItem(arr);

        int leastMovableIte = orderBo.getLeastMovableItem(arr);

        leastMovableItem.setText(getItemCode(leastMovableIte));
        mostMovableItem.setText(getItemCode(mostMovableIte));

        loadCustomerIds();
        date();

        String day = temp.substring(0, 10);
        String month = temp.substring(0, 7);
        String year = temp.substring(0, 4);

        cmbcustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            try {
                Orders ordersDetail = orderBo.getOrdersDetail(value);
                lblTotal.setText(String.valueOf(ordersDetail.getTotal()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        todayIncome.setText(String.valueOf(orderBo.getDayIncome(day)));
        monthIncome.setText(String.valueOf(orderBo.getMonthIncome(month)));
        yearIncome.setText(String.valueOf(orderBo.getYearIncome(year)));
    }

    private void initArray() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < orders.size(); i++) {
            OrderDetail order = orderBo.getOrder(orders.get(i).getOrderId());
            list.add(Integer.parseInt((order.getItem().getItemCode().substring(3, 5))));
        }
        arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
    }

    private String getItemCode(int mostMovableItem) {
        if (mostMovableItem < 9) {
            return "I-00" + mostMovableItem;
        } else if (mostMovableItem < 99) {
            return "I-0" + mostMovableItem;
        } else {
            return "I-" + mostMovableItem;
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> cusIds = orderBo.getOrdersIds();
        cmbcustomerId.getItems().addAll(cusIds);
    }

    private void date() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        temp = f.format(date);
    }
}
