package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);
        //button
        ListView listView=findViewById(R.id.mylist);
        List<String> list1=new ArrayList<String>();
        for(int i=1;i<100;i++){
            list1.add("Stu"+i);
        }
        ListAdapter adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list1);
        //setListAdapter(adapter);
        listView.setAdapter(adapter);//当父类不是时，为控件设计绑定，点击函数，右键对应的java文件，
    }
}