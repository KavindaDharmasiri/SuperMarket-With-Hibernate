package controller;

import bo.BoFactory;
import bo.custom.OrderBo;
import entity.OrderDetail;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageOrderFormController {
    private final OrderBo order = (OrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    public AnchorPane manageOrder;
    public TableView<OrderDetail> custTbl;
    public TableColumn colId;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public Label lblTotal;
    public TableColumn colEdit;

    public void addNewOrder(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MakeOrderForm.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        MakeOrderFormController.setStage(stage);
        stage.showAndWait();
        loadData();
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        custTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        custTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("item"));
        custTbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        custTbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("discount"));

        custTbl.getColumns().get(4).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/view/photo/icons8-edit-30.png");
            ImageView delete = new ImageView("/view/photo/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {

                OrderDetail od = custTbl.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UpdateOrderForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateOrderFormController controller = fxmlLoader.getController();
                controller.setOrder(od);

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                UpdateOrderFormController.setStage(stage);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();

            });

            delete.setOnMouseClicked(event -> {
                OrderDetail od = custTbl.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        order.deleteOrder(od.getOrderId());
                        loadData();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            return new ReadOnlyObjectWrapper(new HBox(10, edit, delete));
        });

        custTbl.getColumns().setAll(colId, colItemCode, colQty, colDiscount, colEdit);
        custTbl.getItems().setAll(loadTableData());
    }

    private ObservableList<OrderDetail> loadTableData() {
        try {
            List<String> oId = order.getAllOrderIds();

            ObservableList<OrderDetail> tableData = FXCollections.observableArrayList();
            for (int i = 0; i < oId.size(); i++) {
                ArrayList<OrderDetail> allOrderDetail = order.getAllOrderDetail(oId.get(i));
                for (int j = 0; j < allOrderDetail.size(); j++) {
                    tableData.add(allOrderDetail.get(j));
                }
            }
            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadData() {
        custTbl.getItems().setAll(loadTableData());
    }

}
