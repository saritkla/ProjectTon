package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;


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
                    CheckUser();
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
    private void CheckUser(){
        String User = editname.getText().toString().trim().toLowerCase();
        boolean issuccess = false;
        try {
            JSONArray jArray = new JSONArray(readJSONFromAsset());
            String name;
            for (int i = 0 ;i<=jArray.length();i++){
                name = jArray.getJSONObject(i).getString("Username");
                if (name.equals(User)){
                    issuccess = true;
                }
            }
//                        txtarea.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (issuccess){
            Intent goStart = new Intent(Login.this,StartGame.class);
            startActivity(goStart);
        }
        else {
            Intent goregis = new Intent(Login.this,regisAc.class);
            startActivity(goregis);
        }
    }
    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("user.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
