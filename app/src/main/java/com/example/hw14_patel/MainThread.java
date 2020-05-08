package com.example.hw14_patel;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.example.hw14_patel.R;

public class MainThread extends Thread implements Runnable {
    private SurfaceHolder holder;
    private Handler handler;
    boolean isTouched;
    private boolean isRunning = false;
    Context context;
    Paint paint;
    Paint paintText;
    int touchX, touchY;
    private static final Object lock = new Object();

    public MainThread (SurfaceHolder surfaceHolder, Context context) {
        holder = surfaceHolder;
        this.context = context;
        handler = new Handler();
        isTouched = false;
    }

    public void setRunning(boolean b) {
        isRunning = b;
    }

    public void setXY (int x, int y) {
        synchronized (lock) {
            touchX = x;
            touchY = y;
            this.isTouched = true;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                render(canvas);
                holder.unlockCanvasAndPost (canvas);
            }
        }
    }

    // Graphic Loading
    private void loadData (Canvas canvas) {
        Bitmap bmp;
        Bitmap bmp1;
        int newWidth, newHeight;
        float scaleFactor;

        paint = new Paint();
        paintText=new Paint();

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.life);
        newWidth = (int)(canvas.getWidth() * 0.1f);
        scaleFactor = (float)newWidth / bmp.getWidth();
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        Assets.life = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.foodbar);
        newHeight = (int)(canvas.getHeight() * 0.1f);
        Assets.foodbar = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.gameover);
        newHeight = (int)(canvas.getHeight() * 0.1f);
        Assets.gameover = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.bug1);
        newWidth = (int)(canvas.getWidth() * 0.2f);
        scaleFactor = (float)newWidth / bmp.getWidth();
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        Assets.bug1 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.bug2);
        newWidth = (int)(canvas.getWidth() * 0.2f);
        scaleFactor = (float)newWidth / bmp.getWidth();
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        Assets.bug2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.bug3);
        newWidth = (int)(canvas.getWidth() * 0.2f);
        scaleFactor = (float)newWidth / bmp.getWidth();
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        Assets.bug3 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.super1);
        newWidth = (int)(canvas.getWidth() * 0.2f);
        scaleFactor = (float)newWidth / bmp.getWidth();
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        Assets.bigBug = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.super2);
        newWidth = (int)(canvas.getWidth() * 0.2f);
        scaleFactor = (float)newWidth / bmp.getWidth();
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        Assets.bigBug1 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        bmp = null;

        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.super3);
        newWidth = (int)(canvas.getWidth() * 0.2f);
        scaleFactor = (float)newWidth / bmp.getWidth();
        newHeight = (int)(bmp.getHeight() * scaleFactor);
        Assets.bigBug2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
        bmp = null;


        Assets.bug = new Bug[4];
        Assets.bug[0]=new Bug();
        Assets.bug[1]=new Bug();
        Assets.bug[2]=new Bug();
        Assets.bug[3]=new Bug();
        Assets.bigbug =new BigBug();
    }

    private void loadBackground (Canvas canvas, int resId) {
        Bitmap bmp = BitmapFactory.decodeResource (context.getResources(), resId);
        Assets.background = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), canvas.getHeight(), false);
        bmp = null;
    }

    private void loadgameover (Canvas canvas, int resId) {
        Bitmap bmp = BitmapFactory.decodeResource (context.getResources(), resId);
        Assets.gameover = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), canvas.getHeight(), false);
        bmp = null;
    }

    private void render (Canvas canvas) {
        int i, x, y;

        switch (Assets.state) {
            case GettingReady:
                loadBackground (canvas, R.drawable.getready);
                loadData(canvas);
                canvas.drawBitmap (Assets.background, 0, 0, null);
                Assets.soundPool.play(Assets.getReadySound, 1, 1, 1, 0, 1);
                Assets.gameTimer = System.nanoTime() / 1000000000f;
                Assets.state = Assets.GameState.Starting;
                Assets.score=0;
                break;
            case Starting:
                canvas.drawBitmap (Assets.background, 0, 0, null);
                float currentTime = System.nanoTime() / 1000000000f;
                if (currentTime - Assets.gameTimer >= 3) {
                    loadBackground (canvas, R.drawable.gamescreen);//wood
                    Assets.state = Assets.GameState.Running;
                }
                break;
            case Running:
                canvas.drawBitmap (Assets.background, 0, 0, null);
                canvas.drawBitmap (Assets.foodbar, 0, canvas.getHeight()-Assets.foodbar.getHeight(), null);


                paintText.setTextAlign(Paint.Align.LEFT);
                paintText.setColor(Color.BLACK);
                paintText.setTextSize(40);
                int space=150;
                paintText.setTypeface(Typeface.DEFAULT_BOLD);
                String scorePrint=Integer.toString(Assets.score);
                canvas.drawText("SCORE :",canvas.getWidth()/17,canvas.getHeight()/17,paintText);
                canvas.drawText(scorePrint,canvas.getWidth()/17+space,canvas.getHeight()/17,paintText);


                int space1=230;
                int radius = (int)(canvas.getWidth() * 0.05f);
                int spacing = 8;
                x = canvas.getWidth() - radius - spacing;
                y = radius + spacing;
                for (i=0; i<Assets.livesLeft; i++) {
                    //Life Icon
                    canvas.drawBitmap (Assets.life, x, y, null);
                    x -= (radius*2 + spacing);
                }



                int ran=(int) (Math.random()*2);
                if (isTouched) {
                    isTouched = false;
                    boolean isBugKilled=false;
                    boolean isSuperbugkill =Assets.bigbug.supertouched(canvas, touchX, touchY);
                    for(int j=0;j<Assets.bug.length;j++) {
                        isBugKilled = Assets.bug[j].touched(canvas, touchX, touchY);
                    }

                    if (isBugKilled) {

                        switch (ran) {
                            case 0:
                                Assets.soundPool.play(Assets.squishSound, 1, 1, 1, 0, 1);
                                break;
                            case 1:
                                Assets.soundPool.play(Assets.squishSound1, 1, 1, 1, 0, 1);
                                break;
                            default:
                                Assets.soundPool.play(Assets.squishSound2, 1, 1, 1, 0, 1);
                                break;

                        }

                        Assets.score++;
                    }

                    else if(isSuperbugkill){

                        Assets.soundPool.play(Assets.superSquishSound, 1, 1, 1, 0, 1);
                        Assets.score = Assets.score + 10;
                    }
                    else{
                        Assets.soundPool.play(Assets.thumpSound, 1, 1, 1, 0, 1);
                    }


                }



                for(int j=0;j<Assets.bug.length;j++) {
                    Assets.bug[j].drawDead(canvas);
                    Assets.bug[j].move(canvas);
                    Assets.bug[j].birth(canvas);
                }


                Assets.bigbug.move(canvas);

                Assets.bigbug.birth(canvas);
                Assets.bigbug.drawDead(canvas);


                if (Assets.livesLeft == 0)
                    Assets.state = Assets.GameState.GameEnding;
                break;
            case GameEnding:
                final SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

                int High = prefs.getInt("key_highscore",0);
                Assets.highscore=High;
                if (Assets.highscore < Assets.score) {
                    Assets.highscore = Assets.score;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("key_highscore", Assets.score);
                    editor.commit();
                    Assets.highscore=Assets.score;
                    Assets.soundPool.play(Assets.clapSound, 1, 1, 1, 0, 1);
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "New High Score: "+ Assets.score,
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

                Assets.state = Assets.GameState.GameOver;
                if (Assets.mediaPlayer != null)  {
                    Assets.mediaPlayer.release();
                    Assets.mediaPlayer = null;

                }

                break;
            case GameOver:
                loadgameover (canvas, R.drawable.gameover);
                canvas.drawBitmap (Assets.background, 0, 0, null);
                canvas.drawBitmap (Assets.gameover, 0, 0, null);
                break;
        }
    }
}
