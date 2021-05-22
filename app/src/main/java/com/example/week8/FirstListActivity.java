package com.example.week8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //准备数据
        List<String> list1=new ArrayList<String>();
        for(int i=1;i<100;i++){
            list1.add("item"+i);
        }
        String[] list_data={"one","two","three","four"};
        ListAdapter adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list1);
        setListAdapter(adapter);
        Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==9){
                    ArrayList<String> list2=(ArrayList<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(FirstListActivity.this,
                            android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
            }
        };

        MyTask task=new MyTask();
        task.setHandler(handler);
        Thread t=new Thread(task);
        t.start();//task.run()
    }
}