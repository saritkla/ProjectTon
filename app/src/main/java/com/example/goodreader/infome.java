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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class infome extends AppCompatActivity {
    TextView User;
    TextView IntageV;
    TextView AgeV;
    TextView SchoolV;
    TextView NameV;
    String username,Intage,Name,Age,School;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_infome);

        User= (TextView)findViewById(R.id.User);
        IntageV= (TextView)findViewById(R.id.IntageV);
        AgeV= (TextView)findViewById(R.id.AgeV);
        SchoolV= (TextView)findViewById(R.id.SchoolV);
        NameV= (TextView)findViewById(R.id.NameV);

        User.setText(username);
        myRef = FirebaseDatabase.getInstance().getReference().child("username").child(username);
        database = FirebaseDatabase.getInstance();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Name = String.valueOf(dataSnapshot.child("Name").getValue());
                Intage = String.valueOf(dataSnapshot.child("Intage").getValue());
                School = String.valueOf(dataSnapshot.child("School").getValue());
                Age = String.valueOf(dataSnapshot.child("Age").getValue());
                NameV.setText(Name);
                IntageV.setText(Intage);
                AgeV.setText(Age);
                SchoolV.setText(School);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
