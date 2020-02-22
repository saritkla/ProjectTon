package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
                        startActivity(goStart);
                    }
                    else {
                        Toast.makeText(Login.this, "Pleas Register", Toast.LENGTH_SHORT).show();
                        Intent goregis = new Intent(Login.this,regisAc.class);
                        startActivity(goregis);
                    }
                }

        });
        regisbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregis = new Intent(Login.this,regisAc.class);
                startActivity(goregis);
            }
        });
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
