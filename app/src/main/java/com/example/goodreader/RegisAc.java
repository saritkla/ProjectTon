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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisAc extends AppCompatActivity {
    ImageButton submit,cancel;
    EditText username,name,school,age,birth;
    String User,Name,School,Age,Birth;
//    DbHelper db;
    public HashMap arruser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    boolean insert = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_regispage);
        myRef = FirebaseDatabase.getInstance().getReference().child("username");
        database = FirebaseDatabase.getInstance();
        username = (EditText) findViewById(R.id.Username);
        name = (EditText) findViewById(R.id.nameAnser);
        school =(EditText) findViewById(R.id.schoolname);
        age = (EditText) findViewById(R.id.intage);
        birth = (EditText) findViewById(R.id.birthday);
        submit = (ImageButton) findViewById(R.id.submit);
        cancel = (ImageButton) findViewById(R.id.cancel);

//        db = new DbHelper(this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(RegisAc.this,Login.class);
                startActivity(toLogin);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User = username.getText().toString();
                Name = name.getText().toString();
                School = name.getText().toString();
                Age = age.getText().toString();
                Birth = birth.getText().toString();
                if (User.isEmpty()) Toast.makeText(RegisAc.this, "กรุณาใส่ข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                else {
                    myRef.child(User).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            arruser = (HashMap) dataSnapshot.getValue();
                            if (arruser == null) insert = true;
                            else Toast.makeText(RegisAc.this, "กรุณาใช้ชื่อผู้ใช้อื่น", Toast.LENGTH_SHORT).show();
                            if (insert && !User.equals("") && !Name.equals("") && !School.equals("") && !Age.equals("") && !Birth.equals("")) {
                                myRef.child(User).child("Name").setValue(Name);
                                myRef.child(User).child("Age").setValue(Age);
                                myRef.child(User).child("Birthday").setValue(Birth);
                                myRef.child(User).child("School").setValue(School);
                                myRef.child(User).child("wordtrain").child("0").child("Time").setValue(0);
                                myRef.child(User).child("wordtest").child("0").child("Time").setValue(0);
                                Toast.makeText(RegisAc.this, "ลงทะเบียนเสร็จสิ้น", Toast.LENGTH_SHORT).show();
                                Intent toLogin = new Intent(RegisAc.this, Login.class);
                                startActivity(toLogin);
                            }
                            else Toast.makeText(RegisAc.this, "กรุณาใส่ข้อมูล", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}
