package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import entity.Customer;
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

public class UpdateCustomerFormController {
    private static Stage Sta;
    private final CustomerBo customerBo = (CustomerBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public AnchorPane updateForm;
    public TextField postalCode;
    public TextField province;
    public TextField city;
    public TextField address;
    public TextField name;
    public Label title;
    public Label id;
    public Button btnUpdate;
    private int code;
    private int p;
    private int c;
    private int a;
    private int n;
    private String cuid;


    public static void setStage(Stage stage) {
        Sta = stage;
    }

    public void initialize() {

        btnUpdate.setDisable(true);
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Customer c1 = new Customer(
                id.getText(), title.getText(), name.getText(),
                address.getText(), city.getText(), province.getText(), postalCode.getText()
        );

        if (customerBo.updateCustomer(c1))
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Sta.close();
    }

    public void customerProvince(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[a-zA-Z]{4,}$";
        String typeText = province.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            province.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            p = 0;
            province.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                postalCode.requestFocus();
            }
        }
    }

    public void customerCity(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[a-zA-Z]{4,}$";
        String typeText = city.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            c = 1;
            city.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            c = 0;
            city.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                province.requestFocus();
            }
        }
    }

    public void customerAddress(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = address.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            address.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            a = 0;
            address.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                city.requestFocus();
            }
        }
    }

    public void customerName(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = name.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            name.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            n = 0;
            name.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                address.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        btnUpdate.setDisable(n != 1 || a != 1 || p != 1 || c != 1 || code != 1);
    }

    public void customerPostalCode(KeyEvent keyEvent) {
        btnUpdate.setDisable(true);
        String regEx = "^[0-9][0-9]{5}$";
        String typeText = postalCode.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            code = 1;
            postalCode.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            code = 0;
            postalCode.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    public void setCust(String custId) {
        cuid = custId;
        try {
            Customer customer = customerBo.getCustomer(custId);

            name.setText(customer.getCustName());
            id.setText(custId);
            title.setText(customer.getCustTitle());
            postalCode.setText(customer.getPostalCode());
            province.setText(customer.getProvince());
            city.setText(customer.getCity());
            address.setText(customer.getCustAddress());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
