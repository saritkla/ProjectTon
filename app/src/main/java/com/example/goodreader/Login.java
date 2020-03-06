package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Login extends AppCompatActivity {
    Button loginbt;
    Button regisbt;
    EditText editname;
    boolean issuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_login);
        loginbt = (Button) findViewById(R.id.loginBt);
        regisbt = (Button) findViewById(R.id.regisBt);
        editname = (EditText) findViewById(R.id.editname);
        issuccess = false;
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    issuccess = getuser(editname.getText().toString());
                    if (issuccess){
                        Intent goStart = new Intent(Login.this,StartGame.class);
                        goStart.putExtra("username",editname.toString());
                        startActivity(goStart);
                    }
                    else {
                        Toast.makeText(Login.this, "ชื่อผู้ใช้ไม่ถูกต้อง กรุณาลงทะเบียน", Toast.LENGTH_SHORT).show();
                    }
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

    public boolean getuser(String user) {
//        Log.d("User is",(String)user);
        boolean resule = false;
        TodoListDAO todoListDAO = new TodoListDAO(getApplicationContext());
        todoListDAO.open();
        ArrayList<String> mylist = todoListDAO.getAllTodoList();
        Object[] mStringArray = mylist.toArray();
        for(int i = 0; i < mStringArray.length ; i++){
            Log.d("string is",(String)mStringArray[i]);
            if (mStringArray[i].equals(user)) {
                resule = true;
            }
        }
        todoListDAO.close();
        return resule;
    }
}
