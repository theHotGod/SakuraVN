package com.mobdeve.s16.paguio.anthony.sakuravn.models;

public class UserAccount {
    // Attributes
    private String confirmPassword;
    private String email;
    private String username;
    private String password;
    private Gender playerMC;

    public enum Gender {
        M, F
    }

    public UserAccount() {
    }

    public UserAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Gender getPlayerMC() {
        return playerMC;
    }

    public void setPlayerMC(Gender playerMC) {
        this.playerMC = playerMC;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
