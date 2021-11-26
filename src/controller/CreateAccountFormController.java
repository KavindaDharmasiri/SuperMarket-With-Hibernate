package controller;

import bo.BoFactory;
import bo.custom.CreateAccountBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.NewUserDTO;
import entity.NewUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

public class CreateAccountFormController {
    private final CreateAccountBo createAccountBo = (CreateAccountBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CREATE_ACCOUNT);
    public AnchorPane createAccountForm;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public Button btnSave;
    public Separator sepPass;
    public Separator sepName;
    public JFXComboBox cBox;
    public PasswordField hidePassword;
    public ImageView openEye;
    public ImageView closeEye;
    private int p;
    private int n;
    private String value;
    private String password;

    public void initialize() {
        txtPassword.setVisible(false);
        openEye.setVisible(false);
        btnSave.setDisable(true);
        cBox.getItems().addAll("Admin", "Cashier");

        cBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
        });
    }

    public void craeteAccount(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (value != null) {
            String psswd = encryptPassword(txtPassword.getText());
            List<NewUser> temp = createAccountBo.searchUser(value);
            int z = 0;
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getName().equals(txtUserName.getText())) {
                    z = 1;
                    new Alert(Alert.AlertType.INFORMATION, "This User Also In Our System. Please Use Another Name.").show();
                    return;
                }
            }
            if (z != 1) {
                createAccountBo.saveNewUser(new NewUserDTO(txtUserName.getText(), psswd, value));
                new Alert(Alert.AlertType.INFORMATION, "Success.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Login Type.").show();
        }
    }

    private String encryptPassword(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public void txtUserName(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{5,30}$";
        String typeText = txtUserName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            sepName.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            n = 0;
            sepName.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                btnSave.setDisable(true);
                txtPassword.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        btnSave.setDisable(p != 1 || n != 1);
    }

    public void txtShowPassword(KeyEvent keyEvent) {
        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtPassword.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            sepPass.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            p = 0;
            sepPass.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        password = txtPassword.getText();
        hidePassword.setText(password);
    }

    public void hidePassword(KeyEvent keyEvent) {
        password = hidePassword.getText();
        txtPassword.setText(password);

        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = hidePassword.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            sepPass.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            p = 0;
            sepPass.setStyle("-fx-background-color: red");
            setbtn(true);
        }
    }

    public void OpenEyeOnAction(MouseEvent mouseEvent) {
        txtPassword.setVisible(false);
        openEye.setVisible(false);
        closeEye.setVisible(true);
        hidePassword.setVisible(true);
    }

    public void CloseEyeOnAction(MouseEvent mouseEvent) {
        txtPassword.setVisible(true);
        openEye.setVisible(true);
        closeEye.setVisible(false);
        hidePassword.setVisible(false);
    }
}
