package com.example.demo;

public class User {
    private String id;
    private String username;
    private String email;
    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    // We don't include a getter for password for security reasons
}
