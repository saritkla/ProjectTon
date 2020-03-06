package com.example.goodreader;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class wordgamepop extends Activity {
    ImageButton normal,train;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        final String username =bundle.getString("username");
        setContentView(R.layout.activity_wordgamepop);
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

        normal = (ImageButton)findViewById(R.id.normal);
        train = (ImageButton)findViewById(R.id.train);

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tonom = new Intent(wordgamepop.this,Wordgamestart.class);
                tonom.putExtra("username",username);
                startActivity(tonom);
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent totrian = new Intent(wordgamepop.this,traingamestart.class);
                totrian.putExtra("username",username);
                startActivity(totrian);
            }
        });
    }
    public void onBackPressed() {

    }
}
