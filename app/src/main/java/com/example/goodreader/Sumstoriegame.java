package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username =bundle.getString("username");
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        storieID = bundle.getInt("storieID");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_sumstoriegame);
        backtomenu = (ImageButton)findViewById(R.id.backtomenu);
        play = (ImageButton)findViewById(R.id.nextplay);
        lasttime = (TextView)findViewById(R.id.lasttime);
        sumavgtime = (TextView)findViewById(R.id.sumavgtime);
        Countnum = (TextView)findViewById(R.id.Countnum);
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        Countnum.setText(String.valueOf(storieID+1));
//        BigDecimal timeword = new BigDecimal(sumtimepergame);
//        BigDecimal timem = timeword.divide(milisec, 3, HALF_UP);
//        BigDecimal wordgame =new BigDecimal(wordpergame);
        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomenu = new Intent(Sumstoriegame.this,StartGame.class);
                tomenu.putExtra("username",username);
                startActivity(tomenu);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent togame = new Intent(Sumstoriegame.this, StoriegameStart.class);
                togame.putExtra("username",username);
                startActivity(togame);
            }
        });

    }
    public void onBackPressed() {

    }
}
