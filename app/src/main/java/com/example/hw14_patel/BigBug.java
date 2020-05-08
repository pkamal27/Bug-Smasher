package com.example.hw14_patel;

import android.graphics.Canvas;
import com.example.hw14_patel.R;

public class BigBug {
    enum BugState {
        Dead,
        ComingBackToLife,
        Alive,
        DrawDead,
    };

    BugState state;
    int x,y;
    int i=1,j=2;
    int k=1,l=2;
    int touchcount=0;
    int newtouch=0;
    double speed;

    float timeToBirth;
    float startBirthTimer;
    float deathTime;
    float animateTimer;


    public BigBug () {
        state = BugState.Dead;
    }


    public void birth (Canvas canvas) {
        if (state == BugState.Dead) {
            state = BugState.ComingBackToLife;
            startBirthTimer = System.nanoTime() / 1000000000f;
        }

        else if (state == BugState.ComingBackToLife) {
            float curTime = System.nanoTime() / 1000000000f;

            if (curTime - deathTime >20) {

                state = BugState.Alive;

                x = (int)(Math.random() * canvas.getWidth());

                if (x < Assets.bigBug.getWidth()/2)
                    x = Assets.bigBug.getWidth()/2;
                else if (x > canvas.getWidth() - Assets.bigBug.getWidth()/2)
                    x = canvas.getWidth() - Assets.bigBug.getWidth()/2;
                y = 0;

                speed = canvas.getHeight() / 6;
                animateTimer = curTime;
            }
        }
    }

    public void move (Canvas canvas) {

        if (state == BugState.Alive) {

            float curTime = System.nanoTime() / 1000000000f;
            float elapsedTime = curTime - animateTimer;
            animateTimer = curTime;

            y += (speed * elapsedTime);
            if(i<j) {
                canvas.drawBitmap(Assets.bigBug, x, y, null);
                i++;
            }
            else{
                canvas.drawBitmap(Assets.bigBug1, x, y, null);
                j++;
            }


            if (y >= canvas.getHeight()) {

                state = BugState.Dead;

                Assets.soundPool.play(Assets.bugReachSound, 1, 1, 1, 0, 1);
                Assets.livesLeft--;
            }
        }
    }

    public boolean supertouched (Canvas canvas, int touchx, int touchy) {
        boolean touched = false;

        if (state == BugState.Alive) {

            float dis = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y)));

            if (dis <= Assets.bigBug.getWidth()*0.75f) {
                touchcount++;
            }

            if (touchcount-newtouch == 4 && dis <= Assets.bigBug.getWidth()*0.75f) {
                newtouch=touchcount;
                state = BugState.DrawDead;
                touched = true;

                deathTime = System.nanoTime() / 1000000000f;
            }
        }
        return (touched);
    }

    public void drawDead (Canvas canvas) {
        if (state == BugState.DrawDead) {

            canvas.drawBitmap(Assets.bigBug2, x, y, null);

            float curTime = System.nanoTime() / 1000000000f;
            float timeSinceDeath = curTime - deathTime;
            if (timeSinceDeath > 4)
                state = BugState.Dead;
        }
    }

}

