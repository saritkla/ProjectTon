package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Sumstoriegame extends AppCompatActivity {
    ImageButton backtomenu,play;
    TextView lasttime,sumavgtime,Countnum;
    int storieID;
    String username;
    FirebaseDatabase database;
    DatabaseReference myRef;
    MediaPlayer music1,buttontab,buttonstart;
    HomeWatcher mHomeWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        storieID = bundle.getInt("StorieID");

        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_sumstoriegame);

        backtomenu = (ImageButton)findViewById(R.id.backtomenu);
        play = (ImageButton)findViewById(R.id.nextplay);
        lasttime = (TextView)findViewById(R.id.lasttime);
        sumavgtime = (TextView)findViewById(R.id.sumavgtime);
        Countnum = (TextView)findViewById(R.id.Countnum);
        Countnum.setText(String.valueOf(storieID));

        buttonstart = MediaPlayer.create(this,R.raw.buttonstart);
        buttontab = MediaPlayer.create(this,R.raw.buttontap);
        music1 = MediaPlayer.create(this,R.raw.win);
        music1.start();

        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomenu = new Intent(Sumstoriegame.this,StartGame.class);
                tomenu.putExtra("username",username);
                if (mServ != null) {
                    mServ.startMusic();
                }
                buttontab.start();
                startActivity(tomenu);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent togame = new Intent(Sumstoriegame.this, StoriegameStart.class);
                togame.putExtra("username",username);
                buttonstart.start();
                startActivity(togame);
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
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
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
        music.setClass(this,MusicService.class);
        mHomeWatcher.stopWatch();
        stopService(music);

    }
    @Override
    public void onBackPressed() {

    }

}
