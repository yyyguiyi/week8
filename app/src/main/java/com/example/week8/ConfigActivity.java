package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG = "ConfigActivity";
    EditText dollarEditor,euroEditor,wonEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent = getIntent();

        float dollar2 = intent.getFloatExtra("dollar_key",0.0f);
        float euro2 = intent.getFloatExtra("euro_key",0.0f);
        float won2 = intent.getFloatExtra("won_key",0.0f);

        Log.i(TAG, "onCreate:dollar2=" + dollar2);
        Log.i(TAG, "onCreate:euro2=" + euro2);
        Log.i(TAG, "onCreate:won2=" + won2);

        dollarEditor = findViewById(R.id.edit_dollar);
        euroEditor = findViewById(R.id.edit_euro);
        wonEditor = findViewById(R.id.edit_won);

        dollarEditor.setText(String.valueOf(dollar2));
        euroEditor.setText(String.valueOf(euro2));
        wonEditor.setText(String.valueOf(won2));



    }


    public void save(View btn){
        Log.i(TAG,"save: ");

        float newdollar = Float.parseFloat(dollarEditor.getText().toString());
        float neweuro = Float.parseFloat(euroEditor.getText().toString());
        float newwon = Float.parseFloat(wonEditor.getText().toString());

        Log.i(TAG,"save: newdollar = " + newdollar);
        Log.i(TAG,"save: neweuro = " + neweuro);
        Log.i(TAG,"save: newwon = " + newwon);

        Intent ret = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("new_dollar",newdollar);
        bdl.putFloat("new_euro",neweuro);
        bdl.putFloat("new_won",newwon);
        ret.putExtras(bdl);

        setResult(6,ret);

        finish();
    }
}