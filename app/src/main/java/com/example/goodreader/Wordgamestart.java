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


public class Wordgamestart extends AppCompatActivity {

    ImageButton ready;
    TextView textcount;
    String username;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int wordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_wordgamestart);
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        database = FirebaseDatabase.getInstance();
        ready = (ImageButton)findViewById(R.id.readyicon);
        textcount = (TextView)findViewById(R.id.textcount);

        myRef.child("wordtest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wordID = Integer.valueOf(String.valueOf(dataSnapshot.getChildrenCount()));
                Log.d("wodID", String.valueOf(wordID));
                textcount.setText(String.valueOf(wordID+1));
                ready.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tostart = new Intent(Wordgamestart.this, Wordgame1.class);
                        tostart.putExtra("username",username);
                        tostart.putExtra("wordID",wordID);
                        startActivity(tostart);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
