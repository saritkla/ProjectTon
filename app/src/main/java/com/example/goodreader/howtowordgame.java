package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class howtowordgame extends AppCompatActivity {
    ImageButton howtowordgame;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtowordgame);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Bundle bundle = getIntent().getExtras();
        howtowordgame = (ImageButton)findViewById(R.id.howtotrain);
        username = bundle.getString("username");
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        database = FirebaseDatabase.getInstance();
        howtowordgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tostart = new Intent(howtowordgame.this,traingamestart.class);
                tostart.putExtra("username",username);
                startActivity(tostart);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }
}
