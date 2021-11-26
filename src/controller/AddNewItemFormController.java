package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import dto.ItemDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddNewItemFormController {
    private static Stage newStage;
    private final ItemBo item = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public AnchorPane itemSave;
    public Button btnSave;
    public TextField txtItemPackSize;
    public TextField txtItemDes;
    public TextField txtItemCode;
    public TextField txtItemUnitPrice;
    public TextField txtQtyOnHand;
    private int q;
    private int p;
    private int d;
    private int a;

    public static void setStage(Stage stage) {
        newStage = stage;
    }

    public void initialize() {
        btnSave.setDisable(true);
        try {
            txtItemCode.setText(item.generateNewItemID());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO c1 = new ItemDTO(
                txtItemCode.getText(), txtItemDes.getText(), txtItemPackSize.getText(),
                Double.parseDouble(txtItemUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())
        );
        if (item.addItem(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            newStage.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void backToItemManage(ActionEvent actionEvent) throws IOException {
        newStage.close();
    }

    public void packSize(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = txtItemPackSize.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            txtItemPackSize.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            a = 0;
            txtItemPackSize.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                btnSave.setDisable(true);
                txtQtyOnHand.requestFocus();
            }
        }
    }

    public void description(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtItemDes.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            d = 1;
            txtItemDes.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            d = 0;
            txtItemDes.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                btnSave.setDisable(true);
                txtItemPackSize.requestFocus();
            }
        }
    }

    public void unitPrice(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[1-9][0-9]{1,3}([.][0-9]{2})?$";
        String typeText = txtItemUnitPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            txtItemUnitPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            p = 0;
            txtItemUnitPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }

    }

    public void qtyOnHand(KeyEvent keyEvent) {
        btnSave.setDisable(true);
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
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                btnSave.setDisable(true);
                txtItemUnitPrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        btnSave.setDisable(d != 1 || p != 1 || q != 1 || a != 1);
    }
}
