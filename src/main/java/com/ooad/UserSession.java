package com.ooad;

public class UserSession {
    private static UserSession instance;
    private String username;
    private String userId;
    private String role;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getUserId() {return userId;}             
    public void setUserId(String userId) {this.userId = userId;}
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public void clearSession() {
        this.username = null;
        this.userId = null;
    }

    public boolean isLoggedIn() {
        return this.userId != null;
    }

    
}