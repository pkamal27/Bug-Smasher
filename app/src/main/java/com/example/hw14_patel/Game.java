package com.example.hw14_patel;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hw14_patel.R;

public class Game extends AppCompatActivity {
    MainView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Start the view

        Assets.mediaPlayer = null;
        if (Assets.mediaPlayer != null) {
            Assets.mediaPlayer.release();
            Assets.mediaPlayer = null;
        }
        Assets.mediaPlayer = MediaPlayer.create (this, R.raw.game_play);
        Assets.mediaPlayer.setLooping(true);
        Assets.mediaPlayer.start();
        v = new MainView(this);
        setContentView(v);
    }


    @Override
    protected void onPause () {
        super.onPause();
        v.pause();
        if (Assets.mediaPlayer != null) {
            Assets.mediaPlayer.setLooping(false);
            Assets.mediaPlayer.pause();
        }

    }

    public void onBackPressed () {
        if (Assets.mediaPlayer != null) {
            Assets.mediaPlayer.setLooping(false);
            Assets.mediaPlayer.stop();
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume () {
        super.onResume();
        v.resume();
        if (Assets.mediaPlayer != null) {
            Assets.mediaPlayer.setLooping(true);
            Assets.mediaPlayer.start();
        }
    }
}

