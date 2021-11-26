package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import dto.CustomerDTO;
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

public class AddNewCustomerFormController {
    private static Stage newStage;
    private final CustomerBo customer = (CustomerBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public AnchorPane customerSave;
    public Button btnSave;
    public TextField txtCustName;
    public TextField txtCustTitle;
    public TextField txtCustId;
    public TextField txtCustAddress;
    public TextField txtCustCity;
    public TextField txtCustProvince;
    public TextField txtCustPostalCode;
    private int n = 0;
    private int code = 0;
    private int a = 0;
    private int p = 0;
    private int c = 0;
    private int pc;

    public static void setStage(Stage stage) {
        newStage = stage;
    }

    public void initialize() {
        btnSave.setDisable(true);
        try {
            txtCustId.setText(customer.generateNewCustomerID());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = new CustomerDTO(
                txtCustId.getText(), txtCustTitle.getText(), txtCustName.getText(),
                txtCustAddress.getText(), txtCustCity.getText(), txtCustProvince.getText(), txtCustPostalCode.getText()
        );
        if (customer.addCustomer(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            newStage.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void backToCustomerManage(ActionEvent actionEvent) throws IOException {
        newStage.close();
    }

    public void customerTitle(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = txtCustTitle.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            pc = 1;
            txtCustTitle.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            pc = 0;
            txtCustTitle.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtCustName.requestFocus();
            }
        }
    }

    public void customerProvince(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[a-zA-Z]{4,}$";
        String typeText = txtCustProvince.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            txtCustProvince.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            p = 0;
            txtCustProvince.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtCustPostalCode.requestFocus();
            }
        }
    }

    public void customerCity(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[a-zA-Z]{4,}$";
        String typeText = txtCustCity.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            c = 1;
            txtCustCity.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            c = 0;
            txtCustCity.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtCustProvince.requestFocus();
            }
        }
    }

    public void customerAddress(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtCustAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            txtCustAddress.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            a = 0;
            txtCustAddress.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtCustCity.requestFocus();
            }
        }
    }

    public void customerName(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = txtCustName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            txtCustName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            n = 0;
            txtCustName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtCustAddress.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        btnSave.setDisable(n != 1 || a != 1 || p != 1 || c != 1 || pc != 1 || code != 1);
    }

    public void customerPostalCode(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[0-9][0-9]{5}$";
        String typeText = txtCustPostalCode.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            code = 1;
            txtCustPostalCode.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            code = 0;
            txtCustPostalCode.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
