package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;

public class Wordgame1 extends AppCompatActivity {

    pl.droidsonroids.gif.GifImageView imagecount;
//    WebView imagecount;
    TextView showtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_wordgame1);
        imagecount = (pl.droidsonroids.gif.GifImageView ) findViewById(R.id.imagecount);
        showtext = (TextView)findViewById(R.id.textshow);
//        WebSettings webSettings = imagecount.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        final String gitfile = "file:android_asset/treetwoone2.gif";
        imagecount.setImageResource(R.drawable.treetwoone2);
        new CountDownTimer(3000,3000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                imagecount.setImageResource(0);
                showtext.setText("klh");
            }
        }.start();
//

    }
}
