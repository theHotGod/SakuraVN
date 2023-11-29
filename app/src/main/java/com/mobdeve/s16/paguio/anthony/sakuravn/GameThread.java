package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    SurfaceHolder surfaceHolder; // object that holds the surface
    boolean isRunning; // boolean to check if the game is running
    long startTime, loopTime; // long variables to hold the start and loop time
    long DELAY = 33; // delay in milliseconds between screen refreshes
    private GameView gameView;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            startTime = System.currentTimeMillis();
            // q: what do i do here in the while loop?

            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        gameView.onDraw(canvas);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            }

    }




    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean state) {
        isRunning = state;
    }




}
