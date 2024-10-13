package com.ooad;

public class UserSession {
    private static UserSession instance;
    private String username;
    private String userId;
    private String role;

    // Private constructor to restrict instantiation
    private UserSession() {}

    // Method to get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getters and setters for session data
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Clear session data (for example, on logout)
    public void clearSession() {
        this.username = null;
        this.userId = null;
    }

    // Optional: Method to check if a user is logged in
    public boolean isLoggedIn() {
        return this.userId != null;
    }

    
}