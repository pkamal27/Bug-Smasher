package com.example.hw14_patel;

import android.graphics.Canvas;

import java.util.Random;
import com.example.hw14_patel.R;

public class Bug {
    enum BugState {
        Dead,
        ComingBackToLife,
        Alive,
        DrawDead,
    };

    BugState state;
    int x,y;
    int i=1,j=2;
    int xdis=0;
    double speed;

    float timeToBirth;
    float startBirthTimer;
    float deathTime;
    float animateTimer;


    public Bug () {
        state = BugState.Dead;
    }


    public void birth (Canvas canvas) {

        if (state == BugState.Dead) {

            state = BugState.ComingBackToLife;

            timeToBirth = (float)Math.random () * 5;

            startBirthTimer = System.nanoTime() / 1000000000f;
        }

        else if (state == BugState.ComingBackToLife) {
            float curTime = System.nanoTime() / 1000000000f;

            if (curTime - startBirthTimer >= timeToBirth) {

                state = BugState.Alive;

                x = (int)(Math.random() * canvas.getWidth());

                if (x < Assets.bug1.getWidth()/2)
                    x = Assets.bug1.getWidth()/2;
                else if (x > canvas.getWidth() - Assets.bug1.getWidth()/2)
                    x = canvas.getWidth() - Assets.bug1.getWidth()/2;
                y = 0;


                int rand=(int)((Math.random()*3)+4);
                speed = canvas.getHeight() /rand;

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
                canvas.drawBitmap(Assets.bug1, x, y, null);
                i++;
            }
            else{
                canvas.drawBitmap(Assets.bug2, x,  y, null);
                j++;
            }


            if (y >= canvas.getHeight()) {

                state = BugState.Dead;

                Assets.soundPool.play(Assets.bugReachSound, 1, 1, 1, 0, 1);
                Assets.livesLeft--;
            }
        }
    }


    public boolean touched (Canvas canvas, int touchx, int touchy) {
        boolean touched = false;

        if (state == BugState.Alive) {

            float dis = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y)));

            if (dis <= Assets.bug1.getWidth()*0.75f) {
                state = BugState.DrawDead;
                touched = true;

                deathTime = System.nanoTime() / 1000000000f;
            }
            else if (dis <= Assets.bug2.getWidth()*0.75f) {
                state = BugState.DrawDead;
                touched = true;

                deathTime = System.nanoTime() / 1000000000f;
            }
        }
        return (touched);
    }

    Random rand=new Random(2);

    public void drawDead (Canvas canvas) {
        if (state == BugState.DrawDead) {

            int k=rand.nextInt(2);

            canvas.drawBitmap(Assets.bug3, x,  y, null);


            float curTime = System.nanoTime() / 1000000000f;
            float timeSinceDeath = curTime - deathTime;

            if (timeSinceDeath >4)
                state = BugState.Dead;
        }
    }

}