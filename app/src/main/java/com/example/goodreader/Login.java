package com.example.goodreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
    boolean issuccess;
    String username;
    public HashMap arruser;
    HomeWatcher mHomeWatcher;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_login);
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
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
        editname = (EditText) findViewById(R.id.editname);
        issuccess = false;
        myRef = FirebaseDatabase.getInstance().getReference().child("username");
        database = FirebaseDatabase.getInstance();
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                username = editname.getText().toString();
                myRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        arruser = (HashMap) dataSnapshot.getValue();
                        Log.d("arr", String.valueOf(arruser));
                        if (arruser != null) issuccess = true;
                        if (issuccess){
                            Intent goStart = new Intent(Login.this,StartGame.class);
                            goStart.putExtra("username",username);
                            startActivity(goStart);
                        }
                        else Toast.makeText(Login.this, "ชื่อผู้ใช้ไม่ถูกต้อง กรุณาลงทะเบียน", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        regisbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregis = new Intent(Login.this, RegisAc.class);
                startActivity(goregis);
            }
        });
    }
    @Override
    public void onBackPressed() {

    }

//    public boolean getuser(String user) {
////        Log.d("User is",(String)user);
//        boolean resule = false;
//        TodoListDAO todoListDAO = new TodoListDAO(getApplicationContext());
//        todoListDAO.open();
//        ArrayList<String> mylist = todoListDAO.getAllTodoList();
//        Object[] mStringArray = mylist.toArray();
//        for(int i = 0; i < mStringArray.length ; i++){
//            Log.d("string is",(String)mStringArray[i]);
//            if (mStringArray[i].equals(user)) {
//                resule = true;
//            }
//        }
//        todoListDAO.close();
//        return resule;
//    }

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
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }
    @Override
    protected void onPause() {
        super.onPause();

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
}
