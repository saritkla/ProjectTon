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
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class Login extends AppCompatActivity {
    Button loginbt;
    Button regisbt;
    EditText editname;
    boolean issuccess =false;
    String username;
    public HashMap arruser;
    HomeWatcher mHomeWatcher;
    FirebaseDatabase database;
    DatabaseReference myRef;
    MediaPlayer music1,buttontab,buttonstart,buttonnot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_login);
        doBindService();
        buttonstart = MediaPlayer.create(this,R.raw.buttonstart);
        buttontab = MediaPlayer.create(this,R.raw.buttontap);
        buttonnot = MediaPlayer.create(this,R.raw.buttonnot);
        music1 = MediaPlayer.create(this,R.raw.greenery);
        music1.setLooping(true);
        music1.start();

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

        loginbt = (Button) findViewById(R.id.loginBt);
        regisbt = (Button) findViewById(R.id.regisBt);
        editname = (EditText) findViewById(R.id.usernamelogin);
        myRef = FirebaseDatabase.getInstance().getReference().child("username");
        database = FirebaseDatabase.getInstance();
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                username = editname.getText().toString();
                Log.d("user: ",username);
                if (username.isEmpty()) {
                    Toast.makeText(Login.this, "กรุณาใส่ชื่อผู้ใช้", Toast.LENGTH_SHORT).show();
                    buttonnot.start();
                    new CountDownTimer(1500, 1500) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            buttonnot.stop();
                        }

                    }.start();
                }
                else{
                    myRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            issuccess = false;
                            arruser = (HashMap) dataSnapshot.getValue();
                            if (arruser != null) issuccess = true;
                            if (issuccess){
                                Intent goStart = new Intent(Login.this,StartGame.class);
                                goStart.putExtra("username",username);
                                music1.stop();
                                buttonstart.start();
                                startActivity(goStart);
                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                //BIND Music Service
                                doBindService();
                                Intent music = new Intent();
                                music.setClass(Login.this, MusicService.class);
                                startService(music);
                                mHomeWatcher.startWatch();
                                if (mServ != null) {
                                    mServ.startMusic();
                                }


                            }
                            else {
                                buttonnot.start();
                                new CountDownTimer(1500, 1500) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        buttonnot.stop();
                                    }

                                }.start();
                                Toast.makeText(Login.this, "ชื่อผู้ใช้ไม่ถูกต้อง กรุณาลงทะเบียน", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        regisbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregis = new Intent(Login.this, RegisAc.class);
                buttontab.start();
                new CountDownTimer(1500, 1500) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        buttontab.stop();
                    }

                }.start();
                music1.stop();
                startActivity(goregis);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });


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
