package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class sumwordgame extends AppCompatActivity {
    ImageButton backtomenu,play;
    TextView lasttime,sumavgtime,Countnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username =bundle.getString("username");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_sumwordgame);
        backtomenu = (ImageButton)findViewById(R.id.backtomenu);
        play = (ImageButton)findViewById(R.id.nextplay);
        lasttime = (TextView)findViewById(R.id.lasttime);
        sumavgtime = (TextView)findViewById(R.id.sumavgtime);
        Countnum = (TextView)findViewById(R.id.Countnum);
        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomenu = new Intent(sumwordgame.this,StartGame.class);
                tomenu.putExtra("username",username);
                startActivity(tomenu);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent togame = new Intent(sumwordgame.this,Wordgame1.class);
                togame.putExtra("username",username);
                startActivity(togame);
            }
        });
    }
    public void onBackPressed() {

    }
}
