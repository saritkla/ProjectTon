package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;


public class Login extends AppCompatActivity {

    Button loginbt;
    Button regisbt;
    EditText editname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbt = (Button) findViewById(R.id.loginBt);
        regisbt = (Button) findViewById(R.id.regisBt);
        editname = (EditText) findViewById(R.id.editname);
        loginbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkLogin();
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

    private void checkLogin(){
        boolean isSuccess = false;
        String username = editname.getText().toString().trim().toLowerCase();


        if (isSuccess) {
            Intent intent = new Intent(Login.this, StartGame.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(Login.this, regisAc.class);
            startActivity(intent);
        }
    }
}
