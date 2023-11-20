package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.view.SurfaceHolder;

public class GameThread extends Thread {
    SurfaceHolder surfaceHolder; // object that holds the surface
    boolean isRunning; // boolean to check if the game is running
    long startTime, loopTime; // long variables to hold the start and loop time
    long DELAY = 33; // delay in milliseconds between screen refreshes

    public GameThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        isRunning = true;
    }

    @Override
    public void run() {

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean state) {
        isRunning = state;
    }


}
