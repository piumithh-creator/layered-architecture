package lk.ijse.foundation_360.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private int id;
    private Integer userId;
    private String userRole;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private String relatedTaskId;

    public NotificationDTO() {
    }

    public NotificationDTO(int id, Integer userId, String userRole, String message,
                          boolean isRead, LocalDateTime createdAt, String relatedTaskId) {
        this.id = id;
        this.userId = userId;
        this.userRole = userRole;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.relatedTaskId = relatedTaskId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRelatedTaskId() {
        return relatedTaskId;
    }

    public void setRelatedTaskId(String relatedTaskId) {
        this.relatedTaskId = relatedTaskId;
    }
}
