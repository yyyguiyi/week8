package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CalculateViaRate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_via_rate);
        //1.（mylist3)从mylist3.java获取货币名和汇率
        //2.(mylist3+calculate)传参到该.java
        //3.(在监听器内计算，设置sleep或者监听到输入直接计算）计算结果：（计算方法）r = Float.parseFloat(str)/(r/100);
        // （1）（float.valueof()+R.id...)获取输入的rmb值，转化为数字
        // （2）计算:（计算方法:r = Float.parseFloat(str)/(r/100);)
        // （3）法一：在plaintext上加监听器；法二：在下面加按钮

    }
}