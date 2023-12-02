package com.mobdeve.s16.paguio.anthony.sakuravn.models;

public class UserAccount {
    // Attributes
    private String email;
    private String username;
    private String password;
    public Integer currentIndex;
    public String gender;

    public UserAccount() {
    }
    public UserAccount(String username, String email, String password, Integer currentIndex, String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.currentIndex = currentIndex;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
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
