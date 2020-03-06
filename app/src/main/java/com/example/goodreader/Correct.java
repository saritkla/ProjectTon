package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;

public class Correct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        final String chose = bundle.getString("aws");
        setContentView(R.layout.activity_correct);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8) , (int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        new CountDownTimer(2000,2000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent gosum = new Intent(Correct.this,Sumstoriegame.class);
                gosum.putExtra("username",username);
                startActivity(gosum);
            }

        }.start();

    }
    public void onBackPressed() {

    }
}
