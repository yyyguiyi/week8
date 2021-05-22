package com.example.week8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;


import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyList3Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    ListView listView;
    private static final String TAG = "MyList3Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list3);//获得控件
        listView = findViewById(R.id.mylist3);
        ProgressBar  progressBar = findViewById(R.id.progressBar);


        /*
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        //准备数据
        ArrayList<HashMap<String,String>> listItems = new ArrayList<HashMap<String,String>>();
        for(int i = 0; i < 10; i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItemTitle","Rate: " + i); //标题文字
            map.put("ItemDetail","detail: " +i); //详情描述
            listItems.add(map);

        }

         */

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==9){
                    ArrayList<HashMap<String,String>> retlist = (ArrayList<HashMap<String,String>>) msg.obj;
                    //系统提供的适配器：把数据和布局联系起来
//                    SimpleAdapter adapter=new SimpleAdapter(MyList3Activity.this,
//                            retlist,
//                            R.layout.list_item,
//                            new String[]{"ItemTitle","ItemDetail"},
//                            new int[]{R.id.itemTitle,R.id.itemDetail}
//                    );

                    //用自定义的适配器：activity,布局，数据集：hashmap
                    //自定义适配器还可以用自定义的数据类型：例如：在java/com.example.week8中创建class：RateItem（具体见5.21的18:00视频 12:28
                    MyAdapter adapter=new MyAdapter(MyList3Activity.this,R.layout.list_item,retlist);

                    listView.setAdapter(adapter);//把适配器和控件联系起来

                    //切换显示
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                super.handleMessage(msg);
            }
        };

        listView.setOnItemClickListener(this);

        MapTask task = new MapTask();
        task.setHandler(handler);
//
        Thread t = new Thread(task);
        t.start(); //task.run()


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtPosition=listView.getItemAtPosition(position);
        HashMap<String,String> map=(HashMap<String, String>)itemAtPosition;
        String titleStr=map.get("ItemTitle");
        String detailStr=map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr"+titleStr);
        Log.i(TAG, "onItemClick: detailStr"+detailStr);
//        方法二：
//        TextView title=(TextView)view.findViewById(R.id.itemTitle);
//        TextView detail=(TextView)view.findViewById(R.id.itemDetail);
//        String title2=String.valueOf(title.getText());
//        String detail2=String.valueOf(detail.getText());

        //传参：将要传递的参数放到intent里；打开新页面：启动intent，使跳转
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("currency",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);
//        方法二：
//        //获取货币名和汇率
//        //声明与对应控件对应的可编辑文本
//        EditText currency,r;
//        Intent intent = getIntent();
//        //放到包里
//        titleStr = intent.getStringExtra("ItemTitle");
//        detailStr = intent.getStringExtra("DetailTitle");
//        //找到calculate中对应控件
//        currency = findViewById(R.id.currency);
//        r = findViewById(R.id.inputRmb);
//        //设置对应控件值
//        currency.setText(titleStr);
//        r.setText(detailStr);
    }
}