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

//    pl.droidsonroids.gif.GifImageView imagecount;
    WebView imagecount;
    TextView showtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_wordgame1);
        imagecount = (WebView) findViewById(R.id.imagecount);
//        showtext = (TextView)findViewById(R.id.textshow);
        WebSettings webSettings = imagecount.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final String gitfile = "file:android_asset/treetwo.html";
//        imagecount.setImageResource(R.drawable.treetwoone);

        new CountDownTimer(4000,4000){
            @Override
            public void onTick(long l) {
                imagecount.loadUrl(gitfile);
            }

            @Override
            public void onFinish() {
                imagecount.loadUrl(String.valueOf(0));
//                showtext.setText("kla");
            }
        }.start();
//

    }
}
