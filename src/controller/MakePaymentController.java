package controller;

import bo.BoFactory;
import bo.custom.OrderBo;
import entity.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MakePaymentController {
    public AnchorPane makePayment;
    public TableColumn orderId;
    public TableColumn orderDate;
    public TableColumn customerId;
    public TableView<Orders> paymentTbl;
    OrderBo orderBo = (OrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {
        paymentTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        paymentTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        paymentTbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customer"));

        paymentTbl.getColumns().setAll(orderId, orderDate, customerId);
        paymentTbl.getItems().setAll(loadTableData());
    }

    private ObservableList<Orders> loadTableData() {
        try {
            ArrayList<Orders> orders = orderBo.getOrders();
            ObservableList<Orders> tableData = FXCollections.observableArrayList();
            for (int i = 0; i < orders.size(); i++) {
                tableData.add(orders.get(i));
            }
            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadData() {
        paymentTbl.getItems().setAll(loadTableData());
    }

    public void makePayment(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MakeOrderPayment.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        UpdateItemFormController.setStage(stage);
        MakeOrderPaymentController.setStage(stage);
        stage.showAndWait();
        loadData();
    }
}
