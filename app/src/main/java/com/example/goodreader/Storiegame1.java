package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class Storiegame1 extends AppCompatActivity {
    pl.droidsonroids.gif.GifImageView imagecount;
    TextView showtext,textcount;
    ImageButton nextpage;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_storiegame1);
        imagecount = (pl.droidsonroids.gif.GifImageView ) findViewById(R.id.imagecount);
        showtext = (TextView)findViewById(R.id.textshow);
        textcount = (TextView)findViewById(R.id.textcount);
        nextpage = (ImageButton)findViewById(R.id.nextpagebt);
        nextword();
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count++;
                showtext.setText("");
                String co = Integer.toString(count);
                textcount.setText(co);
                nextword();
                if(count == 11){
                    Intent tosum = new Intent(Storiegame1.this,StoriegameStart.class);
                    startActivity(tosum);
                }
            }
        });
    }


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
    public void nextword(){
        imagecount.setImageResource(R.drawable.treetwoone2);
        new CountDownTimer(3000,3000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                imagecount.setImageResource(0);
                try {
                    JSONArray jArray = new JSONArray(readJSONFromAsset());
                    String word;
                    int min = 0;
                    int max = 30;
                    int random_int = (int) (Math.random() * (max - min + 1) + min);
                    word = jArray.getJSONObject(random_int).getString("storie");
                    showtext.setText(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
}