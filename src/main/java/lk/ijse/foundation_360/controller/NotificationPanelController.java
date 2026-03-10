package lk.ijse.foundation_360.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.NotificationBO;
import lk.ijse.foundation_360.dto.NotificationDTO;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationPanelController implements Initializable {

    @FXML private VBox notificationList;
    @FXML private Label lblEmpty;
    @FXML private Button btnMarkAllRead;

    private final NotificationBO notificationBO = (NotificationBO) BOFactory.getInstance().getBO(BOFactory.BOType.NOTIFICATION);
    private Runnable onNotificationClick;
    private String userRole;
    private Integer userId;
    private boolean isAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void setUserContext(String role, Integer userId, boolean isAdmin) {
        this.userRole = role; this.userId = userId; this.isAdmin = isAdmin;
        loadNotifications();
    }

    public void setOnNotificationClick(Runnable callback) { this.onNotificationClick = callback; }

    public void loadNotifications() {
        notificationList.getChildren().clear();
        try {
            List<NotificationDTO> notifications = isAdmin
                ? notificationBO.getAllNotifications()
                : notificationBO.getNotificationsForUser(userRole, userId);
            if (notifications.isEmpty()) {
                lblEmpty.setVisible(true); btnMarkAllRead.setDisable(true);
            } else {
                lblEmpty.setVisible(false); btnMarkAllRead.setDisable(false);
                for (NotificationDTO n : notifications) notificationList.getChildren().add(createCard(n));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private VBox createCard(NotificationDTO n) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: " + (n.isRead() ? "#f3f4f6" : "#dbeafe") +
                "; -fx-background-radius: 8; -fx-padding: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 8;");
        card.setMaxWidth(Double.MAX_VALUE);
        Label msg = new Label(n.getMessage()); msg.setWrapText(true); msg.setFont(Font.font(13)); msg.setTextFill(Color.web("#1f2937"));
        HBox info = new HBox(12); info.setAlignment(Pos.CENTER_LEFT);
        Label roleLabel = new Label(n.getUserRole());
        roleLabel.setStyle("-fx-background-color: #6366f1; -fx-background-radius: 4; -fx-padding: 2 8;");
        roleLabel.setTextFill(Color.WHITE); roleLabel.setFont(Font.font(10));
        LocalDateTime t = n.getCreatedAt();
        Label time = new Label(t != null ? t.format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")) : "Just now");
        time.setFont(Font.font(10)); time.setTextFill(Color.web("#6b7280"));
        info.getChildren().addAll(roleLabel, time);
        card.getChildren().addAll(msg, info);
        card.setOnMouseClicked(e -> {
            try { if (!n.isRead()) notificationBO.markAsRead(n.getId()); } catch (Exception ex) { ex.printStackTrace(); }
            if (onNotificationClick != null) onNotificationClick.run();
            loadNotifications();
        });
        return card;
    }

    @FXML
    private void markAllAsRead() {
        try { notificationBO.markAllAsRead(userRole, userId); loadNotifications(); } catch (Exception e) { e.printStackTrace(); }
    }
}
