package lk.ijse.foundation_360.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.NotificationBO;
import lk.ijse.foundation_360.util.SceneNavigator;
import lk.ijse.foundation_360.util.UserSession;
import java.io.IOException;
import java.net.URL;

public class EmpDashboardController {

    @FXML private AnchorPane rootNode;
    @FXML private Label lblWelcome, lblNotificationBadge, lblMyTasks, lblActiveProjects, lblPendingApprovals, lblNotificationCount;
    @FXML private Button btnNotifications;
    @FXML private StackPane contentArea;

    private final NotificationBO notificationBO = (NotificationBO) BOFactory.getInstance().getBO(BOFactory.BOType.NOTIFICATION);
    private Popup notificationPopup;
    private NotificationPanelController notificationPanelController;

    public void initialize() {
        lblWelcome.setText("Welcome, " + (UserSession.getUsername() != null ? UserSession.getUsername() : UserSession.getRole()) + "!");
        updateNotificationBadge();
        javafx.animation.Timeline tl = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(30), e -> updateNotificationBadge()));
        tl.setCycleCount(javafx.animation.Animation.INDEFINITE); tl.play();
        javafx.application.Platform.runLater(() -> {
            Stage s = (Stage) rootNode.getScene().getWindow(); if (s != null) s.setMaximized(true);
        });
        lblMyTasks.setText("0"); lblActiveProjects.setText("0"); lblPendingApprovals.setText("0"); lblNotificationCount.setText("0");
    }

    @FXML private void showNotifications(ActionEvent event) {
        try {
            if (notificationPopup == null) {
                notificationPopup = new Popup(); notificationPopup.setAutoHide(true);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/foundation_360/NotificationPanel.fxml"));
                Parent panel = loader.load();
                notificationPanelController = loader.getController();
                notificationPanelController.setUserContext(UserSession.getRole(), UserSession.getUserId(), false);
                notificationPanelController.setOnNotificationClick(() -> { notificationPopup.hide(); updateNotificationBadge(); });
                panel.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 2);");
                notificationPopup.getContent().add(panel);
            }
            if (notificationPanelController != null) notificationPanelController.loadNotifications();
            double x = btnNotifications.localToScreen(btnNotifications.getBoundsInLocal()).getMinX();
            double y = btnNotifications.localToScreen(btnNotifications.getBoundsInLocal()).getMaxY() + 5;
            notificationPopup.show(btnNotifications.getScene().getWindow(), x - 300, y);
        } catch (IOException e) { new Alert(Alert.AlertType.ERROR, "Failed to load notifications!").show(); }
    }

    private void updateNotificationBadge() {
        try {
            int count = notificationBO.getUnreadCountForUser(UserSession.getRole(), UserSession.getUserId());
            lblNotificationBadge.setText(String.valueOf(count)); lblNotificationBadge.setVisible(count > 0);
        } catch (Exception e) { lblNotificationBadge.setVisible(false); }
    }


    private void loadContent(String fxmlPath) {
        try {

            if (fxmlPath == null || fxmlPath.trim().isEmpty()) {
                showError("FXML path cannot be empty");
                return;
            }


            if (!fxmlPath.startsWith("/")) {
                fxmlPath = "/" + fxmlPath;
            }


            URL resourceURL = getClass().getResource(fxmlPath);
            if (resourceURL == null) {
                System.err.println("❌ FXML resource not found at: " + fxmlPath);
                showError("FXML file not found: " + fxmlPath);
                return;
            }

            System.out.println("✓ Loading FXML from: " + resourceURL.toExternalForm());


            FXMLLoader loader = new FXMLLoader(resourceURL);
            loader.setCharset(java.nio.charset.StandardCharsets.UTF_8);


            Parent content = loader.load();


            contentArea.getChildren().clear();
            contentArea.getChildren().add(content);
            System.out.println("✓ Successfully loaded: " + fxmlPath);

        } catch (IOException e) {
            System.err.println("❌ IOException loading: " + fxmlPath);
            e.printStackTrace();
            showError("Failed to load module: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error loading: " + fxmlPath);
            e.printStackTrace();
            showError("Unexpected error: " + e.getMessage());
        }
    }

    @FXML private void myTasks(ActionEvent e) { loadContent("/lk/ijse/foundation_360/MyTasks.fxml"); }
    @FXML private void projects(ActionEvent e) { loadContent("/lk/ijse/foundation_360/Project.fxml"); }
    @FXML private void reports(ActionEvent e) { loadContent("/lk/ijse/foundation_360/ReportsView.fxml"); }
    @FXML private void timeSheets(ActionEvent e) { showInfo("Time Sheets under development"); }
    @FXML private void expenses(ActionEvent e) { loadContent("/lk/ijse/foundation_360/Expense.fxml"); }
    @FXML private void inventory(ActionEvent e) { loadContent("/lk/ijse/foundation_360/storeroom.fxml"); }
    @FXML private void approvals(ActionEvent e) { loadContent("/lk/ijse/foundation_360/approval.fxml"); }
    @FXML private void clients(ActionEvent e) { loadContent("/lk/ijse/foundation_360/client.fxml"); }
    @FXML private void designs(ActionEvent e) { loadContent("/lk/ijse/foundation_360/Design.fxml"); }
    @FXML private void history(ActionEvent e) { loadContent("/lk/ijse/foundation_360/PreviousProjects.fxml"); }

    @FXML
    private void logout(ActionEvent event) {
        try {
            UserSession.clearSession();
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/Login.fxml", "Foundation 360 - Login");
        } catch (Exception e) { showError("Failed to logout!"); }
    }

    private void showError(String msg) { Alert a = new Alert(Alert.AlertType.ERROR); a.setTitle("Error"); a.setHeaderText(null); a.setContentText(msg); a.showAndWait(); }
    private void showInfo(String msg) { Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle("Info"); a.setHeaderText(null); a.setContentText(msg); a.showAndWait(); }
}
