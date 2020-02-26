package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.webkit.WebView;

public class Wordgame1 extends AppCompatActivity {

    WebView imagecount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
