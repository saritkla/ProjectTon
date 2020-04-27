package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class StartGame extends AppCompatActivity {

    ImageButton readfast,rooread,setting,info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        Log.d("log",username);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_startgame);
        readfast = (ImageButton)findViewById(R.id.fastread);
        rooread = (ImageButton)findViewById(R.id.rooread);
        setting = (ImageButton)findViewById(R.id.setting);
        info = (ImageButton)findViewById(R.id.info);

        readfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup = new Intent(getApplicationContext(),wordgamepop.class);
                popup.putExtra("username",username);
                startActivity(popup);
            }
        });
        rooread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gorooreads = new Intent(StartGame.this, StoriegameStart.class);
                gorooreads.putExtra("username",username);
                startActivity(gorooreads);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gosetting = new Intent(StartGame.this,settingAc.class);
                gosetting.putExtra("username",username);
                startActivity(gosetting);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goinfo = new Intent(StartGame.this,infome.class);
                goinfo.putExtra("username",username);
                startActivity(goinfo);
            }
        });

    }
    @Override
    public void onBackPressed() {

    }

}
