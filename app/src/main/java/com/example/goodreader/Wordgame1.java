package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
    TextView showtext;
    ImageButton nextpage;
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
        nextpage = (ImageButton)findViewById(R.id.nextpagebt);
//        WebSettings webSettings = imagecount.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        final String gitfile = "file:android_asset/treetwoone2.gif";
        nextword();
        int i =1;
        while (true)
        {
            if(i<=10)
            {
                nextpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showtext.setText(null);
                        nextword();
                    }
                });
            }
            else
            {
                Intent gosum = new Intent(Wordgame1.this,Wordgamestart.class);
                startActivity(gosum);
            }
            i++;
        }



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
                    int max = 1229;
                    int random_int = (int) (Math.random() * (max - min + 1) + min);
                    word = jArray.getJSONObject(random_int).getString("words");
                    showtext.setText(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
}
