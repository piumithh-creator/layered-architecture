package lk.ijse.foundation_360.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.Collections;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML private AnchorPane rootNode;
    @FXML private StackPane contentArea;
    @FXML private Button btnNotifications;
    @FXML private Label lblNotificationBadge, lblTotalClients, lblTotalEmployees, lblActiveProjects, lblPendingApprovals, lblLowStockAlerts;

    private final NotificationBO notificationBO = (NotificationBO) BOFactory.getInstance().getBO(BOFactory.BOType.NOTIFICATION);
    private Popup notificationPopup;
    private NotificationPanelController notificationPanelController;

    @FXML private void client(ActionEvent e) {
        String r = UserSession.getRole();
        if (r == null || !"ADMIN".equalsIgnoreCase(r)) { showError("Access Denied", "Only administrators can access client details."); return; }
        loadUI("/lk/ijse/foundation_360/client.fxml");
    }
    @FXML private void projects(ActionEvent e) { loadUI("/lk/ijse/foundation_360/Project.fxml"); }
    @FXML private void order(ActionEvent e) { loadUI("/lk/ijse/foundation_360/Orders.fxml"); }
    @FXML private void design(ActionEvent e) { loadUI("/lk/ijse/foundation_360/Design.fxml"); }
    @FXML private void employees(ActionEvent e) { loadUI("/lk/ijse/foundation_360/Employee.fxml"); }
    @FXML private void order_design(ActionEvent e) { loadUI("/lk/ijse/foundation_360/Design.fxml"); }
    @FXML private void previous_project(ActionEvent e) { loadUI("/lk/ijse/foundation_360/PreviousProjectsView.fxml"); }
    @FXML private void expenses(ActionEvent e) { loadUI("/lk/ijse/foundation_360/ExpensesReportView.fxml"); }
    @FXML private void salary(ActionEvent e) { loadUI("/lk/ijse/foundation_360/SalaryReportView.fxml"); }
    @FXML private void approval(ActionEvent e) { loadUI("/lk/ijse/foundation_360/ExpensesApprovalView.fxml"); }
    @FXML private void approvals(ActionEvent e) { loadUI("/lk/ijse/foundation_360/ApprovalView.fxml"); }
    @FXML private void reports(ActionEvent e) { loadUI("/lk/ijse/foundation_360/ReportsView.fxml"); }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateNotificationBadge(); loadDashboardStatistics();
        javafx.animation.Timeline tl = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(30), e -> { updateNotificationBadge(); loadDashboardStatistics(); }));
        tl.setCycleCount(javafx.animation.Animation.INDEFINITE); tl.play();
        if (rootNode != null && rootNode.getScene() != null && rootNode.getScene().getWindow() instanceof Stage s && !s.isMaximized()) s.setMaximized(true);
    }

    @FXML
    private void showNotifications(ActionEvent event) {
        try {
            if (notificationPopup == null) {
                notificationPopup = new Popup(); notificationPopup.setAutoHide(true);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/foundation_360/NotificationPanel.fxml"));
                Parent panel = loader.load();
                notificationPanelController = loader.getController();
                notificationPanelController.setUserContext(UserSession.getRole(), UserSession.getUserId(), true);
                notificationPanelController.setOnNotificationClick(() -> { notificationPopup.hide(); updateNotificationBadge(); });
                panel.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 2);");
                notificationPopup.getContent().add(panel);
            }
            if (notificationPanelController != null) notificationPanelController.loadNotifications();
            double x = btnNotifications.localToScreen(btnNotifications.getBoundsInLocal()).getMinX();
            double y = btnNotifications.localToScreen(btnNotifications.getBoundsInLocal()).getMaxY() + 5;
            notificationPopup.show(btnNotifications.getScene().getWindow(), x - 300, y);
        } catch (IOException e) { e.printStackTrace(); showError("Error", "Failed to load notifications!"); }
    }

    private void updateNotificationBadge() {
        try {
            int count = notificationBO.getUnreadCountForAdmin();
            lblNotificationBadge.setText(String.valueOf(count)); lblNotificationBadge.setVisible(count > 0);
        } catch (Exception e) { lblNotificationBadge.setVisible(false); }
    }


    private void loadUI(String fxmlPath) {
        try {

            if (fxmlPath == null || fxmlPath.trim().isEmpty()) {
                showError("Error", "FXML path cannot be empty");
                return;
            }


            if (!fxmlPath.startsWith("/")) {
                fxmlPath = "/" + fxmlPath;
            }


            URL resourceURL = getClass().getResource(fxmlPath);
            if (resourceURL == null) {
                System.err.println("❌ FXML resource not found at: " + fxmlPath);
                System.err.println("❌ Classpath: " + System.getProperty("java.class.path"));
                showError("Resource Not Found", "FXML file not found: " + fxmlPath +
                         "\n\nEnsure the file exists in: src/main/resources" + fxmlPath);
                return;
            }

            System.out.println("✓ Loading FXML from: " + resourceURL.toExternalForm());


            FXMLLoader loader = new FXMLLoader(resourceURL);
            loader.setCharset(java.nio.charset.StandardCharsets.UTF_8);


            Parent root = loader.load();


            contentArea.getChildren().setAll(Collections.singleton(root));
            System.out.println("✓ Successfully loaded: " + fxmlPath);

        } catch (NullPointerException e) {
            System.err.println("❌ NullPointerException loading: " + fxmlPath);
            e.printStackTrace();
            showError("Error", "FXML file not found: " + fxmlPath);
        } catch (IOException e) {
            System.err.println("❌ IOException loading: " + fxmlPath);
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            showError("Error", "Failed to load section: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error loading: " + fxmlPath);
            e.printStackTrace();
            showError("Error", "Unexpected error: " + e.getMessage());
        }
    }

    private void loadDashboardStatistics() {
        lblTotalClients.setText("12"); lblTotalEmployees.setText("24");
        lblActiveProjects.setText("8"); lblPendingApprovals.setText("5"); lblLowStockAlerts.setText("3");
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            Stage stage = (Stage) rootNode.getScene().getWindow();
            SceneNavigator.navigateToScene(stage, "/lk/ijse/foundation_360/Login.fxml", "Foundation 360 - Login");
        } catch (Exception e) { e.printStackTrace(); showError("Logout Error", "Failed to logout!"); }
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR); a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
