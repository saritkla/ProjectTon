package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class manual extends AppCompatActivity {
    MediaPlayer buttontab,buttonstart,buttonnot;
    ImageButton close;
    String username;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");

        buttonstart = MediaPlayer.create(this,R.raw.buttonstart);
        buttontab = MediaPlayer.create(this,R.raw.buttontap);
        buttonnot = MediaPlayer.create(this,R.raw.buttonnot);
        close = (ImageButton)findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomenu = new Intent(manual.this,infome.class);
                tomenu.putExtra("username",username);
                buttonnot.start();
                startActivity(tomenu);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }
}
