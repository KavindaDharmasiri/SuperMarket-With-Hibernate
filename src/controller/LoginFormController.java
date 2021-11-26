package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    static Stage stage;
    static Stage secondStage;
    public AnchorPane mainAnchrPan;
    public AnchorPane movableAnchrPane;
    public AnchorPane slider;
    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void setSecontStage(Stage stage) {
        secondStage = stage;
    }

    public void initialize(URL location, ResourceBundle resources) {
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        mainAnchrPan.setOnMouseDragged(event -> event.setDragDetect(true));

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
    }

    public void min(javafx.scene.input.MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    public void logAsAdministrator(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }

    public void logAsCashier(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }

    public void createAccount(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CreateAccountForm.fxml");
        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }

    public void aboutMarket(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/HomeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }
}
