package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class RateCalcActivity extends AppCompatActivity {

    String currency=null;
    EditText inp2;
    float rate=0f;

    String TAG="rateCalc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_calc);
        //获取传递过来的参数
        currency=getIntent().getStringExtra("currency");
        rate=getIntent().getFloatExtra("rate",0f);
        //利用获取的参数设置页面显示值
        ((TextView)findViewById(R.id.title2)).setText(currency);
        //为input RMB添加监听
        inp2=(EditText)findViewById(R.id.inp2);
        inp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            //当input RMB变化时
            public void afterTextChanged(Editable s) {
                TextView show=(TextView)RateCalcActivity.this.findViewById(R.id.show2);
                if(s.length()>0){
                    float val=Float.parseFloat(s.toString());
                    //计算对应货币值
                    show.setText("结果为："+val/(rate/100));
                }else {
                    show.setText("请输入货币金额");
                }
            }
        });
    }
}