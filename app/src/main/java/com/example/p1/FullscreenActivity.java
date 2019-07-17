package com.example.p1;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

public class FullscreenActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "Hi";

    snaky gameView;

    @Override
    public void onBackPressed() {
        setContentView(R.layout.opening_screen);
        initial();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_screen);
        initial();


    }
    public void initial()
    {
        Button b1=findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                gameView = new snaky(FullscreenActivity.this, null);
                gameView.setHandler(handler);
                setContentView(gameView);

            }
        });
        Button b2=findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Settings change will be provided in next version",Toast.LENGTH_SHORT).show();
            }
        });
        Button b3=findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Created by Ashwani",Toast.LENGTH_SHORT).show();
            }
        });
        Button b4=findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




}