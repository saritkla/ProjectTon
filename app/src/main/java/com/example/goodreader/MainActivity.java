package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.os.CountDownTimer;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        setContentView(R.layout.activity_main);

        new CountDownTimer(4000, 4000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent tostart = new Intent(MainActivity.this,Login.class);
                startActivity(tostart);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }

        }.start();
    }
}

