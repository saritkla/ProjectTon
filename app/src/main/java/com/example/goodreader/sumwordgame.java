package com.example.goodreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_UP;

public class sumwordgame extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_sumwordgame);
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
//        timem = sumtimepergame/1000;
//        lastgame = timem/wordpergame;
//        String lastgameshow = Double.toString(lastgame);
        BigDecimal timeword = new BigDecimal(sumtimepergame);
        BigDecimal timem = timeword.divide(milisec, 3, HALF_UP);
        BigDecimal wordgame =new BigDecimal(wordpergame);
        BigDecimal lastgame = wordgame.divide(timem,3, HALF_UP);
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
                Intent tomenu = new Intent(sumwordgame.this,StartGame.class);
                tomenu.putExtra("username",username);
                startActivity(tomenu);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent togame = new Intent(sumwordgame.this,Wordgamestart.class);
                togame.putExtra("username",username);
                startActivity(togame);
            }
        });
    }
    public void onBackPressed() {

    }
}
