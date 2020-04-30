package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;

public class Correct extends Activity {

    int StorieID,time;
    String username;
    MediaPlayer correct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        final String chose = bundle.getString("aws");
        StorieID = bundle.getInt("StorieID");
        Log.d("StorieID   ", String.valueOf(StorieID));
        time = bundle.getInt("ETime");
        setContentView(R.layout.activity_correct);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8) , (int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        correct = MediaPlayer.create(this, R.raw.correct);
        correct.start();

        getWindow().setAttributes(params);
        new CountDownTimer(1000,1000){
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                Intent gosum = new Intent(Correct.this,Sumstoriegame.class);
                gosum.putExtra("username",username);
                gosum.putExtra("StorieID",StorieID+1);
                gosum.putExtra("ETime",time);
                startActivity(gosum);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }

        }.start();

    }
    public void onBackPressed() {

    }
}
