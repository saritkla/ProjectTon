package com.example.goodreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
    public  String aws;
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
            int min = 0;
            int max = 30;
            String question = jArray.getJSONObject(random_int).getString("storie");
            Textquestion.setText(question);
            String cha = jArray.getJSONObject(random_int).getString("a");
            choseA.setText(cha);
            String chb = jArray.getJSONObject(random_int).getString("b");
            choseB.setText(chb);
            String chc = jArray.getJSONObject(random_int).getString("c");
            choseC.setText(chc);
            String chd = jArray.getJSONObject(random_int).getString("d");
            choseD.setText(chd);
            aws = jArray.getJSONObject(random_int).getString("aswer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
