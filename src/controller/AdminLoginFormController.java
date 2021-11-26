package controller;

import bo.BoFactory;
import bo.custom.CreateAccountBo;
import com.jfoenix.controls.JFXTextField;
import entity.NewUser;
import javafx.fxml.FXML;
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

public class AdminLoginFormController {
    private final CreateAccountBo createAccountBo = (CreateAccountBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CREATE_ACCOUNT);
    public AnchorPane adminLoginForm;
    public JFXTextField txtAdminUserName;
    public JFXTextField txtAdminPassword;
    public Button btnLog;
    public Separator sepPass;
    public Separator sepName;
    public PasswordField hidePassword;
    public ImageView openEye;
    public ImageView closeEye;
    private int n = 0;
    private int p = 0;
    private String password;

    public void initialize() {
        txtAdminPassword.setVisible(false);
        openEye.setVisible(false);

        btnLog.setDisable(true);
    }

    public void txtUserName(KeyEvent keyEvent) {
        btnLog.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{5,30}$";
        String typeText = txtAdminUserName.getText();

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
                txtAdminPassword.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {

        btnLog.setDisable(p != 1 || n != 1);
    }

    public void txtShowPassword(KeyEvent keyEvent) {

        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtAdminPassword.getText();

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
        password = txtAdminPassword.getText();
        hidePassword.setText(password);
    }

    @FXML
    private void logIn() throws IOException, SQLException, ClassNotFoundException {
        List<NewUser> temp = createAccountBo.searchUser("Admin");

        for (int i = 0; i < temp.size(); i++) {
            String pWod = DecryptPasswrd(temp.get(i).getPassword());
            if (txtAdminUserName.getText().equals(temp.get(i).getName())) {

                if (txtAdminPassword.getText().equals(pWod)) {
                    LoginFormController.stage.hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AdminDashBoard.fxml"));
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

                    AdminDashBoardController controller = fxmlLoader.getController();
                    controller.txtAdminName.setText(txtAdminUserName.getText());
                    controller.txtName.setText(txtAdminUserName.getText());
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
        new Alert(Alert.AlertType.WARNING, "Can't find your Name.").show();

    }

    private String DecryptPasswrd(String password) {
        return new String(Base64.getMimeDecoder().decode(password));
    }

    public void hidePassword(KeyEvent keyEvent) {
        password = hidePassword.getText();
        txtAdminPassword.setText(password);

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
        txtAdminPassword.setVisible(false);
        openEye.setVisible(false);
        closeEye.setVisible(true);
        hidePassword.setVisible(true);
    }

    public void CloseEyeOnAction(MouseEvent mouseEvent) {
        txtAdminPassword.setVisible(true);
        openEye.setVisible(true);
        closeEye.setVisible(false);
        hidePassword.setVisible(false);
    }
}
