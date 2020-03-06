package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;

public class Wordgame1 extends AppCompatActivity {

    pl.droidsonroids.gif.GifImageView imagecount;
    TextView showtext,textcount;
    ImageButton nextpage;
    long pauseoffset;
    Chronometer chronometer;
    boolean running;
    int count = 1;
    public  int random_int;
    public int min = 0;
    public int max = 1193;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username =bundle.getString("username");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_wordgame1);
        imagecount = (pl.droidsonroids.gif.GifImageView ) findViewById(R.id.imagecount);
        showtext = (TextView)findViewById(R.id.textshow);
        textcount = (TextView)findViewById(R.id.textcount);
        nextpage = (ImageButton)findViewById(R.id.nextpagebt);
        chronometer = (Chronometer)findViewById(R.id.chrometer);
        random_int = (int) (Math.random() * (max - min + 1) + min);
        nextword(random_int);


        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 11){
                    resetChrometer();
                    Intent tosum = new Intent(Wordgame1.this,sumwordgame.class);
                    tosum.putExtra("username",username);
                    startActivity(tosum);
                }
                else{
                    pauseChrometer();
                    long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                    Log.d("Time is", String.valueOf((long)elapsedMillis));
                    resetChrometer();
                    showtext.setText("");
                    String co = Integer.toString(count);
                    textcount.setText(co);
                    random_int = (int) (Math.random() * (max - min + 1) + min);
                    nextword(random_int);
                    pauseChrometer();
                }
                count++;
            }
        });
    }

    public  void startChrometer(){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseoffset);
            chronometer.start();
            running = true;
        }
    }
    public  void pauseChrometer(){
        if(running){
            chronometer.stop();
            pauseoffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void resetChrometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseoffset = 0;
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("word.json");
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
    public void nextword(final int random_int){
        imagecount.setImageResource(R.drawable.treetwoone2);
        new CountDownTimer(3000,3000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                imagecount.setImageResource(android.R.color.transparent);
                startChrometer();
                try {
                    JSONArray jArray = new JSONArray(readJSONFromAsset());
                    String word;
                    word = jArray.getJSONObject(random_int).getString("words");
                    showtext.setText(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
}
