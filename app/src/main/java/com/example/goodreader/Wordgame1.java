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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Wordgame1 extends AppCompatActivity {

    pl.droidsonroids.gif.GifImageView imagecount;
    TextView showtext,textcount;
    ImageButton nextpage;
    long pauseoffset;
    Chronometer chronometer;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String username;
    boolean running;
    int countword,countmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_wordgame1);
        imagecount = (pl.droidsonroids.gif.GifImageView ) findViewById(R.id.imagecount);
        showtext = (TextView)findViewById(R.id.textshow);
        textcount = (TextView)findViewById(R.id.textcount);
        nextpage = (ImageButton)findViewById(R.id.nextpagebt);
        chronometer = (Chronometer)findViewById(R.id.chrometer);
        myRef = FirebaseDatabase.getInstance().getReference().child("username");
        database = FirebaseDatabase.getInstance();
        countmain = 1;
        myRef.child(username).child("wordtest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int wordID = Integer.valueOf(String.valueOf(dataSnapshot.getChildrenCount()));
                Log.d("wodID", String.valueOf(wordID));
                textcount.setText(String.valueOf(wordID));
                if (wordID == 1)countword = 0;
                else countword = wordID;
                nextword(countword);
                nextpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(countmain == 10){
                            resetChrometer();
                            Intent tosum = new Intent(Wordgame1.this,sumwordgame.class);
                            tosum.putExtra("username",username);
                            startActivity(tosum);
                        }
                        else{
                            pauseChrometer();
                            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                            String co = Integer.toString(countword);
                            myRef.child(username).child("wordtest").child(co).child("Time").setValue(elapsedMillis);
                            Log.d("Time is", String.valueOf((long)elapsedMillis));
                            resetChrometer();
                            showtext.setText("");
                            countword++;
                            String col = Integer.toString(countword+1);
                            textcount.setText(col);
                            nextword(countword);
                            pauseChrometer();
                        }
                        countmain++;
                        Log.d("countmain is ", String.valueOf(countmain));
                        Log.d("countword is ", String.valueOf(countword));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    public void nextword(final int wordID){
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
                    String stringcount;
                    word = jArray.getJSONObject(wordID).getString("words");
                    stringcount = jArray.getJSONObject(wordID).getString("StringCount");
                    String co = Integer.toString(wordID);
                    myRef.child(username).child("wordtest").child(co).child("word").setValue(word);
                    myRef.child(username).child("wordtest").child(co).child("StringCount").setValue(stringcount);
                    showtext.setText(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
    public void onBackPressed() {

    }
}
