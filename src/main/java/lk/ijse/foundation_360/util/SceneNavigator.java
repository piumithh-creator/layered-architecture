package lk.ijse.foundation_360.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {


    public static void navigateToScene(Stage stage, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(SceneNavigator.class.getResource(fxmlPath));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    SceneNavigator.class.getResource("/styles/app.css").toExternalForm()
            );
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load scene: " + fxmlPath, e);
        }
    }


    public static void navigateToNewWindow(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(SceneNavigator.class.getResource(fxmlPath));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    SceneNavigator.class.getResource("/styles/app.css").toExternalForm()
            );
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load scene: " + fxmlPath, e);
        }
    }


    public static Parent loadFXML(String fxmlPath) {
        try {
            return FXMLLoader.load(SceneNavigator.class.getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load FXML: " + fxmlPath, e);
        }
    }
}
