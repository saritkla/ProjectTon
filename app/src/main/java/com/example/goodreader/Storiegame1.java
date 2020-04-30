package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class Storiegame1 extends AppCompatActivity {
    pl.droidsonroids.gif.GifImageView imagecount;
    TextView showtext,textcount;
    ImageButton nextpage;
    long pauseoffset;
    Chronometer chronometer;
    FirebaseDatabase database;
    DatabaseReference myRef;
    boolean running;
    int storieID;
    String username;
    MediaPlayer countdown,buttonstart,buttontab,buttonnot,buttonnext;
    public int random_int;
    public  long elapsedMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        storieID = bundle.getInt("storieID");
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_storiegame1);
        imagecount = (pl.droidsonroids.gif.GifImageView ) findViewById(R.id.imagecount);
        showtext = (TextView)findViewById(R.id.textshow);
        textcount = (TextView)findViewById(R.id.textcount);
        nextpage = (ImageButton)findViewById(R.id.nextpagebt);
        chronometer = (Chronometer)findViewById(R.id.chrometer);
        textcount.setText(String.valueOf(storieID+1));
        buttonstart = MediaPlayer.create(this, R.raw.buttonstart);
        buttontab = MediaPlayer.create(this, R.raw.buttontap);
        buttonnot = MediaPlayer.create(this, R.raw.buttonnot);
        buttonnext = MediaPlayer.create(this, R.raw.buttonnext);
        countdown = MediaPlayer.create(this, R.raw.coutdown);
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        nextword(storieID);
        countdown.start();
        nextpage.setVisibility(View.INVISIBLE);
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pauseChrometer();
//                elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                if(storieID==29) myRef.child("storiegame").setValue(0);
                else myRef.child("storiegame").setValue(storieID+1);
//                resetChrometer();
                Intent tosum = new Intent(Storiegame1.this,abcd.class);
                tosum.putExtra("username",username);
                tosum.putExtra("storieID",storieID);
                startActivity(tosum);
                buttonstart.start();
            }
        });
    }
//    public  void startChrometer(){
//        if(!running){
//            chronometer.setBase(SystemClock.elapsedRealtime() - pauseoffset);
//            chronometer.start();
//            running = true;
//        }
//    }
//    public  void pauseChrometer(){
//        if(running){
//            chronometer.stop();
//            pauseoffset = SystemClock.elapsedRealtime() - chronometer.getBase();
//            running = false;
//        }
//    }
//    public void resetChrometer(){
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        pauseoffset = 0;
//    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("storie.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void nextword(int random_int){
        imagecount.setImageResource(R.drawable.treetwoone2);
        new CountDownTimer(3000,3000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                imagecount.setImageResource(android.R.color.transparent);
//                startChrometer();
                try {
                    JSONArray jArray = new JSONArray(readJSONFromAsset());
                    String word;
                    word = jArray.getJSONObject(Storiegame1.this.storieID).getString("storie");
                    showtext.setMovementMethod(new ScrollingMovementMethod());
                    showtext.setText(word);
                    nextpage.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
    public void onBackPressed() {

    }
}