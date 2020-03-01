package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class StartGame extends AppCompatActivity {

    ImageButton readfast,rooread,setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_startgame);
        readfast = (ImageButton)findViewById(R.id.fastread);
        rooread = (ImageButton)findViewById(R.id.rooread);
        setting = (ImageButton)findViewById(R.id.setting);

        readfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gofastread =new Intent(StartGame.this,Wordgamestart.class);
                startActivity(gofastread);
            }
        });
        rooread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gorooread = new Intent(StartGame.this, StoriegameStart.class);
                startActivity(gorooread);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gosetting = new Intent(StartGame.this,settingAc.class);
                startActivity(gosetting);
            }
        });

    }
    public void onBackPressed() {

    }

}
