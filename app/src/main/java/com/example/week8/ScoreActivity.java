package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private static final String TAG = "ScoreActivity";
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    private void show() {
        Log.i(TAG, "show: score=" + score);
        TextView show = findViewById(R.id.score1);
        show.setText(String.valueOf(score));
    }
    public void btn3(View v){
        score +=3;
        show();

    }
    public void btn2(View v){
        score +=2;
        show();

    }
    public void btn1(View v){
        score +=1;
        show();

    }
    public void reset(View v){
        score =0;
        show();

    }

}