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

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class sumtraingame extends AppCompatActivity {
    ImageButton backtomenu,play;
    TextView lasttime,sumavgtime,Countnum;
    String username;
    int wordpergame,countallword;
    long sumtimepergame,sumtimeall;
    double lastgame,timem,allgame;
    FirebaseDatabase database;
    DatabaseReference myRef;
    BigDecimal milisec = new BigDecimal(1000);
    BigDecimal sec = new BigDecimal(60);
    MediaPlayer music1,buttontab,buttonstart;
    HomeWatcher mHomeWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_sumtraingame);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        wordpergame = bundle.getInt("countwordpergame");
        sumtimepergame = bundle.getLong("sumtimepergame");
        countallword = bundle.getInt("countallwordplay");
        backtomenu = (ImageButton)findViewById(R.id.backtomenu);
        play = (ImageButton)findViewById(R.id.nextplay);
        lasttime = (TextView)findViewById(R.id.lasttime);
        sumavgtime = (TextView)findViewById(R.id.sumavgtime);
        Countnum = (TextView)findViewById(R.id.Countnum);
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        database = FirebaseDatabase.getInstance();
        Countnum.setText(String.valueOf(countallword+1));


        buttonstart = MediaPlayer.create(this,R.raw.buttonstart);
        buttontab = MediaPlayer.create(this,R.raw.buttontap);
        music1 = MediaPlayer.create(this,R.raw.win);
        music1.start();

//        timem = sumtimepergame/1000;
//        lastgame = timem/wordpergame;
//        String lastgameshow = Double.toString(lastgame);
        BigDecimal timeword = new BigDecimal(sumtimepergame);
        BigDecimal timem = timeword.divide(milisec, 3, HALF_UP);
        BigDecimal wordgame =new BigDecimal(wordpergame);
        BigDecimal lastgame = timem.divide(wordgame,3, HALF_UP);
        lasttime.setText(lastgame.toString());
        myRef.child("wordtest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sumtimeall = 0;
                for (int i = 0; i <= countallword; i++) {
                    String is = String.valueOf(i);
                    Long data = (Long) dataSnapshot.child(is).child("Time").getValue();
                    sumtimeall = sumtimeall + data;
                }
//                timem = sumtimeall/1000;
//                timem = timem / 60;
//                allgame = countallword / timem
                BigDecimal timeall = new BigDecimal(sumtimeall);
                BigDecimal timem = timeall.divide(milisec, 3, HALF_UP);
                timem = timem.divide(sec, 3, HALF_UP);
                BigDecimal allword =new BigDecimal(countallword);
                BigDecimal allgame = allword.divide(timem,3, HALF_UP);;
                sumavgtime.setText(allgame.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomenu = new Intent(sumtraingame.this,StartGame.class);
                tomenu.putExtra("username",username);
                buttontab.start();
                startActivity(tomenu);
                if (mServ != null) {
                    mServ.startMusic();
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent togame = new Intent(sumtraingame.this,traingamestart.class);
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