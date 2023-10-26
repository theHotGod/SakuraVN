package com.mobdeve.s16.paguio.anthony.sakuravn.models;

public class UserAccount {
    // Attributes
    private String userUID;
    private String email;
    private String username;
    private String password;
    private Gender playerMC;

    public enum Gender {
        M, F
    }
}
