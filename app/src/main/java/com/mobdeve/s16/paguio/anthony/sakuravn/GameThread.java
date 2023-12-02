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
                        // q: why is there an error here?
                        // a: because the onDraw method is not yet implemented in GameView.java
                        // q: how do i implement the onDraw method?
                        // a: see GameView.java

                        // q: what is the difference of the onDraw method and draw?
                        // a: onDraw is a method that is called by the system to draw the view
                        //    draw is a method that is called by the programmer to draw the view
                        // q: so why do i need to call draw and not onDraw?
                        // a: because onDraw is called by the system, and we want to call it ourselves
                        //    so we call draw instead
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
