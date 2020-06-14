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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class StartGame extends AppCompatActivity {

    ImageButton readfast,rooread,logout,info;
    MediaPlayer buttontab,buttonstart,buttonnot;
    HomeWatcher mHomeWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        Log.d("log",username);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_startgame);
        readfast = (ImageButton)findViewById(R.id.fastread);
        rooread = (ImageButton)findViewById(R.id.rooread);
        logout = (ImageButton)findViewById(R.id.logout);
        info = (ImageButton)findViewById(R.id.info);

        buttonstart = MediaPlayer.create(this,R.raw.buttonstart);
        buttontab = MediaPlayer.create(this,R.raw.buttontap);
        buttonnot = MediaPlayer.create(this,R.raw.buttonnot);

        readfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup = new Intent(getApplicationContext(),wordgamepop.class);
                popup.putExtra("username",username);
                buttontab.start();
                startActivity(popup);
                new CountDownTimer(4000, 4000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        buttontab.stop();
                    }

                }.start();
            }
        });
        rooread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gorooreads = new Intent(StartGame.this, howtorooread.class);
                gorooreads.putExtra("username",username);
                mServ.stopMusic();
                buttontab.start();
                startActivity(gorooreads);
                new CountDownTimer(4000, 4000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        buttontab.stop();
                    }

                }.start();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gologout = new Intent(StartGame.this,Login.class);
                gologout.putExtra("username",username);
                mServ.stopMusic();
                buttonnot.start();
                startActivity(gologout);
                new CountDownTimer(4000, 4000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        buttonnot.stop();
                    }

                }.start();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goinfo = new Intent(StartGame.this,infome.class);
                goinfo.putExtra("username",username);
                buttontab.start();
                startActivity(goinfo);
                new CountDownTimer(4000, 4000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        buttontab.stop();
                    }

                }.start();
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
                Scon,Context.BIND_AUTO_CREATE);
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
