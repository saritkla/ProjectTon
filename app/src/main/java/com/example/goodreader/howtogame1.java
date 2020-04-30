package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class howtogame1 extends AppCompatActivity {
    ImageButton howtotest;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtogame1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        database = FirebaseDatabase.getInstance();
        howtotest = (ImageButton)findViewById(R.id.howtotest);
        howtotest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tostart = new Intent(howtogame1.this,Wordgamestart.class);
                tostart.putExtra("username",username);
                startActivity(tostart);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }
    public void onBackPressed() {

    }
}


