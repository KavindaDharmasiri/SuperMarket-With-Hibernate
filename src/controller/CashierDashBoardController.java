package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierDashBoardController implements Initializable {
    public AnchorPane cashierDashBoard;
    public AnchorPane cashierAnchrPan;
    public Label txtCashierName;
    public Circle circle;
    public Label txtName;
    public ImageView Exit;
    public Label Menu;
    public Label MenuClose;
    public AnchorPane slid1;
    public AnchorPane slider;

    public void min(javafx.scene.input.MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("/view/photo/download (1).jpeg");
        circle.setFill(new ImagePattern(img));

        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        cashierDashBoard.setOnMouseDragged(event -> event.setDragDetect(true));

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

    public void logOut(ActionEvent actionEvent) throws IOException {
        LoginFormController.secondStage.close();
        LoginFormController.stage.show();
    }

    public void orderForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/manageOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierAnchrPan.getChildren().clear();
        cashierAnchrPan.getChildren().add(load);
    }

    public void customerForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierAnchrPan.getChildren().clear();
        cashierAnchrPan.getChildren().add(load);
    }

    public void paymentInfo(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakePayment.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierAnchrPan.getChildren().clear();
        cashierAnchrPan.getChildren().add(load);
    }

    public void dashBoard(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierDashBoardAbout.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierAnchrPan.getChildren().clear();
        cashierAnchrPan.getChildren().add(load);
    }
}
