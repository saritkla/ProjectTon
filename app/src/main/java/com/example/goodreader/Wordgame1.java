package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class Wordgame1 extends AppCompatActivity {

    WebView imagecount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_wordgame1);
        imagecount = (WebView) findViewById(R.id.imagecount);
        new CountDownTimer(3000,1000){
            public void onTick(long millisUntilFinished) {
                imagecount.loadUrl("file:///android_asset/treetwo.html");
            }

            public void onFinish() {

            }
        }.start();
    }
}
