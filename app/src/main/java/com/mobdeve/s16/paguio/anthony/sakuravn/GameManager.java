package com.mobdeve.s16.paguio.anthony.sakuravn;

public class GameManager {
    private static GameManager instance;
    private GameEngine gameEngine;

    private GameManager() {
        gameEngine = new GameEngine();
    }

    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
