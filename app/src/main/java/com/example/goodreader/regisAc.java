package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class regisAc extends AppCompatActivity {
    Button submit,cancel;
    EditText username,name,school,age,birth;
//    String User,Name,School,Age,Birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        username = (EditText) findViewById(R.id.Username);
        name = (EditText) findViewById(R.id.nameAnser);
        school =(EditText) findViewById(R.id.schoolname);
        age = (EditText) findViewById(R.id.intage);
        birth = (EditText) findViewById(R.id.birthday);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toStart = new Intent(regisAc.this,StartGame.class);
                startActivity(toStart);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                User= username.getText().toString();
//                Name = name.getText().toString();
//                School = school.getText().toString();
//                Age = age.getText().toString();
//                Birth = birth.getText().toString();
                if (username.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast errorToast = Toast.makeText(regisAc.this, "Pleas Enter your Username", Toast.LENGTH_LONG);
                    errorToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    errorToast.show();
                }
                if(name.getText().toString().trim().equalsIgnoreCase("")){
                    Toast errorToast = Toast.makeText(regisAc.this, "Pleas Enter your Name and surname", Toast.LENGTH_LONG);
                    errorToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    errorToast.show();
                }
                if (school.getText().toString().trim().equalsIgnoreCase("")){
                    Toast errorToast = Toast.makeText(regisAc.this, "Pleas Enter your School", Toast.LENGTH_LONG);
                    errorToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    errorToast.show();
                }
                if (age.getText().toString().trim().equalsIgnoreCase("")){
                    Toast errorToast = Toast.makeText(regisAc.this, "Pleas Enter your Age", Toast.LENGTH_LONG);
                    errorToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    errorToast.show();
                }
                if (birth.getText().toString().trim().equalsIgnoreCase("")){
                    Toast errorToast = Toast.makeText(regisAc.this, "Pleas Enter your Birthday", Toast.LENGTH_LONG);
                    errorToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    errorToast.show();
                }
                DbHelper dbHelper = new DbHelper(getApplicationContext());

            }
        });
    }
}
