package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


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
//        issuccess = validate(editname.getText().toString());
        loginbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    issuccess = validate(editname.getText().toString());
                    if (issuccess){
                        Intent goStart = new Intent(Login.this,StartGame.class);
                        startActivity(goStart);
                    }
                    else {
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


    public boolean validate(String userName) {
        String[] user = new String[0];
        String name;
        int lang = 0;
        boolean ch = false;
        try {
            JSONArray jArray = new JSONArray(readJSONFromAsset());
            lang = jArray.length();
//            user = new String[lang];
            for (int i = 0 ;i < lang;i++){
                JSONObject currObject = jArray.getJSONObject(i);
                name = currObject.getString("Username");
                if (name.equals(userName)) ch = true;
//                user[i] = name;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        for(int j =0 ; j < lang ;j++) {
//            if (user[j].equals(userName)) ch = true;
//        }
        return ch;
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
