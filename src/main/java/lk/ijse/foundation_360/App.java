package lk.ijse.foundation_360;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(
                getClass().getResource("/lk/ijse/foundation_360/Login.fxml")
        );

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("/styles/app.css").toExternalForm()
        );
        stage.setScene(scene);
        stage.setTitle("Foundation 360 - Login");
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
