package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import entity.Item;
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

public class ItemManageFormController {
    private final ItemBo item = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public AnchorPane itemManage;
    public TableView<Item> itemTbl;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colEdit;

    public void addNewItem(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddNewItemForm.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        AddNewItemFormController.setStage(stage);
        stage.showAndWait();
        loadData();
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        itemTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        itemTbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        itemTbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        itemTbl.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        itemTbl.getColumns().get(5).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/view/photo/icons8-edit-30.png");
            ImageView delete = new ImageView("/view/photo/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                Item items = itemTbl.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UpdateItemForm.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UpdateItemFormController controller = fxmlLoader.getController();
                controller.code.setText(items.getItemCode());
                controller.desc.setText(items.getDescription());
                controller.packSize.setText(String.valueOf(items.getPackSize()));
                controller.qtyOnHand.setText(String.valueOf(items.getQtyOnHand()));
                controller.unitPrice.setText(String.valueOf(items.getUnitPrice()));

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.initStyle(StageStyle.TRANSPARENT);
                UpdateItemFormController.setStage(stage);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                Item items = itemTbl.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        item.deleteItem(items.getItemCode());
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

        itemTbl.getColumns().setAll(colCode, colDescription, colPackSize, colUnitPrice, colQtyOnHand, colEdit);
        itemTbl.getItems().setAll(loadTableData());
    }

    private ObservableList<Item> loadTableData() {
        try {
            List<String> items = item.getAllItemIds();
            ObservableList<Item> tableData = FXCollections.observableArrayList();
            for (int i = 0; i < items.size(); i++) {
                tableData.add(item.getItem(items.get(i)));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadData() {
        itemTbl.getItems().setAll(loadTableData());
    }
}
