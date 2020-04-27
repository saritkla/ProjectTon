package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class abcd extends AppCompatActivity {

    TextView textcount,Textquestion;
    Button choseA,choseB,choseC,choseD;
    String aws,cha,chb,chc,chd,username;
    int wrongcount = 0,StorieId,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username =bundle.getString("username");
        StorieId = bundle.getInt("storieID");
        time = bundle.getInt("time");
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_abcd);
        textcount = (TextView)findViewById(R.id.textcount);
        textcount.setText(String.valueOf(StorieId+1));
        Textquestion = (TextView)findViewById(R.id.question);
        choseA = (Button)findViewById(R.id.choseA);
        choseB = (Button)findViewById(R.id.choseB);
        choseC = (Button)findViewById(R.id.choseC);
        choseD = (Button)findViewById(R.id.choseD);
        try {
            JSONArray jArray = new JSONArray(readJSONFromAsset());
            String word;
            String question = jArray.getJSONObject(StorieId).getString("question");
            Textquestion.setText(question);
            cha = jArray.getJSONObject(StorieId).getString("a");
            choseA.setText(cha);
            chb = jArray.getJSONObject(StorieId).getString("b");
            choseB.setText(chb);
            chc = jArray.getJSONObject(StorieId).getString("c");
            choseC.setText(chc);
            chd = jArray.getJSONObject(StorieId).getString("d");
            choseD.setText(chd);
            aws = jArray.getJSONObject(StorieId).getString("aswer");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        choseA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aws:  ",aws);
                if(aws.equals("a")){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("StorieID",StorieId);
                    gocorrect.putExtra("ETime",time);
                    gocorrect.putExtra("aws",cha);
                    startActivity(gocorrect);
                }
                else {
                    wrongcount +=1;
                    Intent gowrong = new Intent(abcd.this,Wrong.class);
                    startActivity(gowrong);
                }
            }
        });
        choseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aws.equals("b")){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("StorieID",StorieId);
                    gocorrect.putExtra("ETime",time);
                    gocorrect.putExtra("aws",chb);
                    startActivity(gocorrect);
                }
                else {
                    wrongcount +=1;
                    Intent gowrong = new Intent(abcd.this,Wrong.class);
                    startActivity(gowrong);
                }

            }
        });
        choseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aws.equals("c")){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("StorieID",StorieId);
                    gocorrect.putExtra("ETime",time);
                    gocorrect.putExtra("aws",chc);
                    startActivity(gocorrect);
                }
                else {
                    wrongcount +=1;
                    Intent gowrong = new Intent(abcd.this,Wrong.class);
                    startActivity(gowrong);
                }
            }
        });
        choseD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aws.equals("d")){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("StorieID",StorieId);
                    gocorrect.putExtra("ETime",time);
                    gocorrect.putExtra("aws",chd);
                    startActivity(gocorrect);
                }
                else {
                    wrongcount +=1;
                    Intent gowrong = new Intent(abcd.this,Wrong.class);
                    startActivity(gowrong);
                }
            }
        });

    }


    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("storie.json");
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
    public void onBackPressed() {

    }
}
