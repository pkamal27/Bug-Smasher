package com.example.hw14_patel;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hw14_patel.R;


public class Assets extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset);
    }

    static Bitmap background;
    static Bitmap gameover;
    static Bitmap foodbar;
    static Bitmap bug1;
    static Bitmap bug2;
    static Bitmap bug3;
    static Bitmap bigBug;
    static Bitmap bigBug1;
    static Bitmap bigBug2;
    static Bitmap life;
    public static MediaPlayer mediaPlayer;
    static float gameTimer;
    static int livesLeft;
    static int score;
    static int highscore;
    static SoundPool soundPool;
    static int getReadySound;
    static int squishSound;
    static int squishSound1;
    static int squishSound2;
    static int superSquishSound;
    static int clapSound;
    static int thumpSound;
    static int bugReachSound;
    static Context context;
    static Bug[] bug;
    static BigBug bigbug;
    static GameState state;

    enum GameState {
        GettingReady,
        Starting,
        Running,
        GameEnding,
        GameOver,
    };


}
