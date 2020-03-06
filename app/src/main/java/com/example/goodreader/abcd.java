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
    public  String aws,cha,chb,chc,chd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username =bundle.getString("username");
        final int random_int = bundle.getInt("storieId");
        final int time = bundle.getInt("time");
        requestWindowFeature(
                Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_abcd);
        textcount = (TextView)findViewById(R.id.textcount);
        String storieid = Integer.toString(random_int);
        textcount.setText(storieid);
        Textquestion = (TextView)findViewById(R.id.question);
        choseA = (Button)findViewById(R.id.choseA);
        choseB = (Button)findViewById(R.id.choseB);
        choseC = (Button)findViewById(R.id.choseC);
        choseD = (Button)findViewById(R.id.choseD);
        try {
            JSONArray jArray = new JSONArray(readJSONFromAsset());
            String word;
            String question = jArray.getJSONObject(random_int).getString("question");
            Textquestion.setText(question);
            cha = jArray.getJSONObject(random_int).getString("a");
            choseA.setText(cha);
            chb = jArray.getJSONObject(random_int).getString("b");
            choseB.setText(chb);
            chc = jArray.getJSONObject(random_int).getString("c");
            choseC.setText(chc);
            chd = jArray.getJSONObject(random_int).getString("d");
            choseD.setText(chd);
            aws = jArray.getJSONObject(random_int).getString("aswer");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        choseA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aws:  ",aws);
                if(aws == "a"){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("aws",cha);
                    startActivity(gocorrect);
                }
                else {
                    Intent gowrong = new Intent(abcd.this,Wrong.class);
                    startActivity(gowrong);
                }
            }
        });
        choseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aws == "b"){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("aws",chb);
                    startActivity(gocorrect);
                }
                else {
                    Intent gowrong = new Intent(abcd.this,Wrong.class);
                    startActivity(gowrong);
                }

            }
        });
        choseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aws == "c"){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("aws",chc);
                    startActivity(gocorrect);
                }
                else {
                    Intent gowrong = new Intent(abcd.this,Wrong.class);
                    startActivity(gowrong);
                }
            }
        });
        choseD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aws == "d"){
                    Intent gocorrect = new Intent(abcd.this,Correct.class);
                    gocorrect.putExtra("username",username);
                    gocorrect.putExtra("aws",chd);
                    startActivity(gocorrect);
                }
                else {
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
}
