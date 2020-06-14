package com.example.goodreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoriegameStart extends AppCompatActivity {
    ImageButton ready,backtomenu;
    TextView textcount;
    String username;
    FirebaseDatabase database;
    DatabaseReference myRef;
    MediaPlayer mplay;
    MediaPlayer music1, buttontab, buttonstart, buttonnot,buttonnext;
    HomeWatcher mHomeWatcher;
    int storieID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_storiegame_start);
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        database = FirebaseDatabase.getInstance();
        ready = (ImageButton)findViewById(R.id.readyicon);
        textcount = (TextView)findViewById(R.id.textcount);
        backtomenu = (ImageButton)findViewById(R.id.backtomenu);
        buttonstart = MediaPlayer.create(this, R.raw.buttonstart);
        buttontab = MediaPlayer.create(this, R.raw.buttontap);
        buttonnot = MediaPlayer.create(this, R.raw.buttonnot);
        buttonnext = MediaPlayer.create(this,R.raw.buttonnext);
        music1 = MediaPlayer.create(this, R.raw.greenery);
        mplay = MediaPlayer.create(this, R.raw.start);
        mplay.setLooping(true);
        mplay.start();
        myRef.child("storiegame").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storieID = Integer.valueOf(String.valueOf(dataSnapshot.getValue()));
                Log.d("wodID", String.valueOf(storieID));
                textcount.setText(String.valueOf(storieID +1));
                ready.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tostart = new Intent(StoriegameStart.this, Storiegame1.class);
                        tostart.putExtra("username",username);
                        tostart.putExtra("storieID", storieID);
                        overridePendingTransition(android.R.anim.cycle_interpolator,android.R.anim.cycle_interpolator);
                        startActivity(tostart);
                        mplay.stop();

                    }
                });
                backtomenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tomenu = new Intent(StoriegameStart.this,StartGame.class);
                        tomenu.putExtra("username",username);
                        if (mServ != null) {
                            mServ.startMusic();
                        }
                        mplay.stop();
                        buttontab.start();
                        startActivity(tomenu);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    //BIND Music Service
    doBindService();
    Intent music = new Intent();
        music.setClass(this, MusicService.class);
    startService(music);

    //Start HomeWatcher
    mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
        @Override
        public void onHomePressed() {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }

        @Override
        public void onHomeLongPressed() {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }
    });

        mHomeWatcher.startWatch();
}

    //Bind/Unbind music service
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Detect idle screen
        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //UNBIND music service
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        mHomeWatcher.stopWatch();
        stopService(music);

    }

    public void onBackPressed() {
        Intent tomenu = new Intent(StoriegameStart.this, StartGame.class);
        tomenu.putExtra("username", username);
        buttontab.start();
        mplay.stop();
        startActivity(tomenu);
        if (mServ != null) {
            mServ.startMusic();
        }

    }
}