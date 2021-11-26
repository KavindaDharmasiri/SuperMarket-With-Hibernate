package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import entity.Customer;
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
import java.util.List;
import java.util.Optional;

public class ManageCustomerFormController {
    private final CustomerBo customer = (CustomerBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public AnchorPane addnewCustomer;
    public TableView<Customer> custTbl;
    public TableColumn colId;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public TableColumn colEdit;

    public void addNewCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddNewCustomerForm.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        AddNewCustomerFormController.setStage(stage);
        stage.showAndWait();
        loadData();
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        custTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("custId"));
        custTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("custTitle"));
        custTbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("custName"));
        custTbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        custTbl.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("city"));
        custTbl.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("province"));
        custTbl.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        custTbl.getColumns().get(7).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/view/photo/icons8-edit-30.png");
            ImageView delete = new ImageView("/view/photo/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {

                Customer cust = custTbl.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UpdateCustomerForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateCustomerFormController controller = fxmlLoader.getController();
                controller.setCust(cust.getCustId());

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                UpdateCustomerFormController.setStage(stage);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Customer cust = custTbl.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        customer.deleteCustomer(cust.getCustId());
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

        custTbl.getColumns().setAll(colId, colTitle, colName, colAddress, colCity, colProvince, colPostalCode, colEdit);
        custTbl.getItems().setAll(loadTableData());
    }

    private ObservableList<Customer> loadTableData() {
        try {

            List<String> customers = customer.getAllCustomerIds();
            ObservableList<Customer> tableData = FXCollections.observableArrayList();
            for (int i = 0; i < customers.size(); i++) {
                tableData.add(customer.getCustomer(customers.get(i)));
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