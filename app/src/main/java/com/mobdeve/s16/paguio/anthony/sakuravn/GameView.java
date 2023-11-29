package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ImageView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    // making the whole screen of the game clickable and responsive
    private GameThread gameThread;
    private GameEngine gameEngine;

    public GameView(Context context) {
        super(context);
        init(context);
        gameEngine = GameManager.getInstance().getGameEngine();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int screenWidth = canvas.getWidth();
        int screenHeight = canvas.getHeight();

        super.onDraw(canvas);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.school_entrance);
        icon = Bitmap.createScaledBitmap(icon, screenWidth, screenHeight, false);
        canvas.drawColor(android.graphics.Color.WHITE);
        canvas.drawBitmap(icon, 0, 0, new android.graphics.Paint());

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(!gameThread.isRunning()) {
            gameThread = new GameThread(holder, this);
            gameThread.start();
        }
        else {
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(gameThread.isRunning()) {
            gameThread.setIsRunning(false);
            boolean retry = true;
            while(retry) {
                try {
                    gameThread.join();
                    retry = false;
                }
                catch(InterruptedException e) {}
            }
        }
    }



    private void init(Context context) {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(holder, this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Display a Toast when the screen is touched
            Toast.makeText(getContext(), "SurfaceView is working!", Toast.LENGTH_SHORT).show();
            gameEngine.next();

        }
        return true;
    }

}
