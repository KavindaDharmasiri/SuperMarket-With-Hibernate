package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import dto.ItemDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateItemFormController {
    private static Stage newStage;
    private final ItemBo item = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public AnchorPane itemUpdate;
    public TextField qtyOnHand;
    public TextField unitPrice;
    public TextField packSize;
    public TextField desc;
    public Label code;
    public Button btnUpdate;
    private int q;
    private int d;
    private int p;
    private int a;

    public static void setStage(Stage stage) {
        newStage = stage;
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO c1 = new ItemDTO(
                code.getText(), desc.getText(), packSize.getText(),
                Double.parseDouble(unitPrice.getText()), Integer.parseInt(qtyOnHand.getText())
        );

        if (item.updateItem(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            newStage.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        newStage.close();
    }

    public void packSize(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = packSize.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            packSize.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            a = 0;
            packSize.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                btnUpdate.setDisable(true);
                qtyOnHand.requestFocus();
            }
        }
    }

    public void description(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = desc.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            d = 1;
            desc.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            d = 0;
            desc.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                btnUpdate.setDisable(true);
                packSize.requestFocus();
            }
        }
    }

    public void unitPrice(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[1-9][0-9]{1,3}([.][0-9]{2})?$";
        String typeText = unitPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            unitPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            p = 0;
            unitPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }

    }

    public void qtyOnHand(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = qtyOnHand.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            q = 1;
            qtyOnHand.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            q = 0;
            qtyOnHand.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                btnUpdate.setDisable(true);
                unitPrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        btnUpdate.setDisable(d != 1 || p != 1 || q != 1 || a != 1);
    }
}
