package com.example.goodreader;

import androidx.annotation.NonNull;
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
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class traingame1 extends AppCompatActivity {
    pl.droidsonroids.gif.GifImageView imagecount;
    TextView showtext,textcount;
    ImageButton nextpage;
    long pauseoffset;
    Chronometer chronometer;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String username;
    boolean running;
    int countword,countmain,wordID;
    long sumtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_traingame1);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        wordID = bundle.getInt("wordID");
        imagecount = (pl.droidsonroids.gif.GifImageView ) findViewById(R.id.imagecount);
        showtext = (TextView)findViewById(R.id.textshow);
        textcount = (TextView)findViewById(R.id.textcount);
        nextpage = (ImageButton)findViewById(R.id.nextpagebt);
        chronometer = (Chronometer)findViewById(R.id.chrometer);
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        database = FirebaseDatabase.getInstance();
        countmain = 1;
        sumtime = 0;
        imagecount.setImageResource(R.drawable.treetwoone2);
        textcount.setText(String.valueOf(wordID+1));
        new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                imagecount.setImageResource(android.R.color.transparent);
                Log.d("wodID", String.valueOf(wordID));
                textcount.setText(String.valueOf(wordID+1));
                if (wordID == 1) countword = 0;
                else countword = wordID;
                nextword(countword);
                nextpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countmain++;
                        if (countmain == 11){
                            pauseChrometer();
                            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                            String co = Integer.toString(countword);
                            myRef.child("wordtain").child(co).child("Time").setValue(elapsedMillis);
                            sumtime = sumtime + elapsedMillis;
                            resetChrometer();
                            Intent tosum = new Intent(traingame1.this,sumwordgame.class);
                            tosum.putExtra("username",username);
                            tosum.putExtra("sumtimepergame",sumtime);
                            tosum.putExtra("countwordpergame",countmain);
                            tosum.putExtra("countallwordplay",countword);
                            startActivity(tosum);
                        }
                        else{
                            pauseChrometer();
                            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                            String co = Integer.toString(countword);
                            myRef.child("wordtain").child(co).child("Time").setValue(elapsedMillis);
                            sumtime = sumtime + elapsedMillis;
                            Log.d("Time is", String.valueOf((long)elapsedMillis));
                            Log.d("time all =", String.valueOf(sumtime));
                            resetChrometer();
                            showtext.setText("");
                            countword++;
                            String col = Integer.toString(countword+1);
                            textcount.setText(col);
                            nextword(countword);
                            pauseChrometer();
                        }

                        Log.d("countmain is ", String.valueOf(countmain));
                        Log.d("countword is ", String.valueOf(countword));
                    }
                });
            }
        }.start();


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
    public void nextword(final int wordID){
        try {
            JSONArray jArray = new JSONArray(readJSONFromAsset());
            final String word;
            String brind;
            final String Stringcount;
            brind = jArray.getJSONObject(wordID).getString("brinds");
            word = jArray.getJSONObject(wordID).getString("words");
            Stringcount = jArray.getJSONObject(wordID).getString("StringCount");
            showtext.setText(brind);
            new CountDownTimer(3000,3000) {
                @Override
                public void onTick(long l) {

                }
                @Override
                public void onFinish() {
                    startChrometer();
                    String co = Integer.toString(wordID);
                    myRef.child("wordtrain").child(co).child("word").setValue(word);
                    myRef.child("wordtrain").child(co).child("StringCount").setValue(Stringcount);
                    showtext.setText(word);
                }
            }.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {

    }
}
