package com.mobdeve.s16.paguio.anthony.sakuravn.models;

public class PlayerMC {
    // Attributes
    private String playerName;
    private Gender playerMC;

    public enum Gender {
        M, F
    }
    // constructor
    public PlayerMC(String playerName, Gender playerMC) {
        this.playerName = playerName;
        this.playerMC = playerMC;
    }



    // getter
    public String getPlayerName() {
        return this.playerName;
    }

    // getter
    public Gender getPlayerMC() {
        return this.playerMC;
    }

    // setter
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // setter
    public void setPlayerMC(Gender playerMC) {
        this.playerMC = playerMC;
    }
}
