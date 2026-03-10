package lk.ijse.foundation_360.util;

public class UserSession {

    private static String role;
    private static String username;
    private static Integer userId;

    public static void setRole(String role) {
        UserSession.role = role;
    }

    public static String getRole() {
        return role;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUserId(Integer userId) {
        UserSession.userId = userId;
    }

    public static Integer getUserId() {
        return userId;
    }


    public static void clearSession() {
        role = null;
        username = null;
        userId = null;
    }
}
