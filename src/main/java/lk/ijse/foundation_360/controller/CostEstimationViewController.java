package lk.ijse.foundation_360.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class CostEstimationViewController implements Initializable {

    @FXML
    private AnchorPane root;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent adminRoot = FXMLLoader.load(
                    getClass().getResource("/lk/ijse/foundation_360/admindashboard.fxml")
            );
            Stage stage = (Stage) root.getScene().getWindow();
            Scene scene = new Scene(adminRoot, 1000, 600);
            scene.getStylesheets().add(
                    getClass().getResource("/styles/app.css").toExternalForm()
            );
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unable to go back to dashboard!").show();
        }
    }

}
