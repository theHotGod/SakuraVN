package com.mobdeve.s16.paguio.anthony.sakuravn.models;

public class UserAccount {
    // Attributes
    private String email;
    private String username;
    private String password;

    public UserAccount() {
    }

    public UserAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
