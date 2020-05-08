//Kamalkumar Patel
//L20466518

package com.example.hw14_patel;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw14_patel.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView play;
    private ImageView highscore;
    MainView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = new MainView(this);

        play=(ImageView) findViewById(R.id.play);
        highscore=(ImageView) findViewById(R.id.highscore);

        play.setOnClickListener(this);
        highscore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.play:
                startActivity(new Intent(MainActivity.this, Game.class));
                break;
            case R.id.highscore:
                startActivity(new Intent(MainActivity.this, PrefsActivity.class));

        }

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    decorView.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}

