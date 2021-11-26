package controller;

import bo.BoFactory;
import bo.custom.CreateAccountBo;
import com.jfoenix.controls.JFXTextField;
import entity.NewUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

public class CashierLoginFormController {

    private final CreateAccountBo createAccountBo = (CreateAccountBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CREATE_ACCOUNT);
    public AnchorPane cashierLoginForm;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public ImageView openEye;
    public Button btnLog;
    public Separator sepPass;
    public Separator sepName;
    public PasswordField hidePassword;
    public ImageView closeEye;
    private int n = 0;
    private int p = 0;
    private String password;

    public void initialize() {
        txtPassword.setVisible(false);
        openEye.setVisible(false);
        btnLog.setDisable(true);
    }

    public void logIn(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        List<NewUser> temp = createAccountBo.searchUser("Cashier");

        for (int i = 0; i < temp.size(); i++) {
            String pWod = DecryptPasswrd(temp.get(i).getPassword());
            if (txtUserName.getText().equals(temp.get(i).getName())) {
                if (txtPassword.getText().equals(pWod)) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CashierDashBoard.fxml"));
                    Parent root1 = null;

                    try {
                        root1 = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    Scene scene = new Scene(root1);
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    scene.setFill(Color.TRANSPARENT);

                    CashierDashBoardController controller = fxmlLoader.getController();
                    controller.txtCashierName.setText(txtUserName.getText());
                    controller.txtName.setText(txtUserName.getText());

                    LoginFormController.setSecontStage(stage);
                    LoginFormController.stage.hide();
                    stage.show();
                    return;

                } else {
                    new Alert(Alert.AlertType.WARNING, "Password Incorrect.").show();
                    return;
                }
            }
        }
        new Alert(Alert.AlertType.WARNING, "No User Found.").show();
    }

    private String DecryptPasswrd(String password) {
        return new String(Base64.getMimeDecoder().decode(password));
    }

    public void txtUserName(KeyEvent keyEvent) {
        btnLog.setDisable(true);
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
                btnLog.setDisable(true);
                txtPassword.requestFocus();
            }
        }

    }

    private void setbtn(boolean b) {
        btnLog.setDisable(p != 1 || n != 1);
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
