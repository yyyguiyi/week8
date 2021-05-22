package com.example.week8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
////import android.os.Handler;
////import android.os.Looper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.provider.SyncStateContract.Helpers.update;


public class RateActivity extends AppCompatActivity implements Runnable {


    EditText rmb;
    TextView result;
    private float dollarRate = 0.1503f;
    private float euroRate = 0.1266f;
    private float wonRate = 170.2708f;
    private static final String TAG = "RateActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb = findViewById(R.id.inputrmb);
        result = findViewById(R.id.result);

        SharedPreferences sharedPreferences= getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarRate =sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate =sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate =sharedPreferences.getFloat("won_rate",0.0f);
        String updateStr=sharedPreferences.getString("update_str","");
        Log.i(TAG, "onCreate: dollar"+dollarRate);
        Log.i(TAG, "onCreate: updateStr="+updateStr);

        LocalDate today= LocalDate.now();
        Log.i(TAG, "onCreate: today="+today.toString());
        //比较日期
        if(updateStr.equals(today.toString())){
            Log.i(TAG, "onCreate: 日期相等，不更新");
        }else{
            Thread t=new Thread(this);
            t.start();
        }

        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                //收到消息
                if(msg.what==6){
                    Bundle bdl=(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("dollar");
                    euroRate=bdl.getFloat("euro");
                    wonRate=bdl.getFloat("won");

                    SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putFloat("dollar_rate",dollarRate);
                    editor.putFloat("euro_rate",euroRate);
                    editor.putFloat("won_rate",wonRate);

                    //保存日期
                    editor.putString("update_str",today.toString());
                    editor.apply();
                    Toast.makeText(RateActivity.this,"数据已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
//        handler = new Handler(Looper.myLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg){
//                //收到消息
//                if(msg.what==6){
//                    Bundle bdl=(Bundle)msg.obj;
//                    dollarRate=bdl.getFloat("dollar");
//                    euroRate=bdl.getFloat("euro");
//                    wonRate=bdl.getFloat("won");
//
//                    SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
//                    SharedPreferences.Editor editor=sp.edit();
//                    editor.putFloat("dollar_rate",dollarRate);
//                    editor.putFloat("euro_rate",euroRate);
//                    editor.putFloat("won_rate",wonRate);
//
//                    //保存日期
//                    editor.putString("update_str",today.toString());
//                    editor.apply();
//                    Toast.makeText(RateActivity.this,"数据已更新",Toast.LENGTH_SHORT).show();
//                }
//                super.handleMessage(msg);
//            }
//        };


    }

    public void click(View btn) {

        String str = rmb.getText().toString();

        if(str!=null && str.length() > 0){

            float r = 0.1f;

            if(btn.getId()==R.id.dollar){
                r = dollarRate;
            }
            else if(btn.getId()==R.id.euro){
                r = euroRate;
            }
            else{
                r = wonRate;
            }
            r = Float.parseFloat(str)/(r/100);

            result.setText(String.format("%.2f",r));
        }

        else{
            Toast.makeText(this,"请输入计算的金额！", Toast.LENGTH_SHORT).show();
        }
    }
    public void openConfig(View btn){
        Log.i(TAG, "openConfig: ");
        show();
    }

    private void show() {
        Intent conf = new Intent(this, ConfigActivity.class);
        conf.putExtra("dollar_key", dollarRate);
        conf.putExtra("euro_key", euroRate);
        conf.putExtra("won_key", wonRate);

        Log.i(TAG, "openConfig: dollarRate=" + dollarRate);
        Log.i(TAG, "openConfig: euroRate=" + euroRate);
        Log.i(TAG, "openConfig: wonRate=" + wonRate);

        startActivityForResult(conf, 3);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode==3 && resultCode==6){//3,6的位置被换过
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("new_dollar", 0.0f);
            euroRate = bundle.getFloat("new_euro", 0.0f);
            wonRate = bundle.getFloat("new_won", 0.0f);

            SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putFloat("dollar_rate",dollarRate);//dollar_rate的命名可能有误
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);


            editor.apply();

            Log.i(TAG, "onActivityResult: dollarRate=" +dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" +euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" +wonRate);

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    //菜单
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_setting){
            show();
        }
        return super.onOptionsItemSelected(item);
    }

    //线程实现与同步
    @Override
    public void run(){
        Log.i(TAG, "run: run()");
        URL url=null;
        try {
            url=new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http=(HttpURLConnection)url.openConnection();
            InputStream in=http.getInputStream();
            String html=inputStream2String(in);
            Log.i(TAG, "run: html="+html);//日志输出html页面
            Log.i(TAG, "run: output=00000000000000000000000000000000");
        }catch (MalformedURLException e){
          e.printStackTrace();
            Log.e(TAG, "run: ex="+e.toString());
        }catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "run: ex="+e.toString());
        }

        Bundle bundle=new Bundle();
        //发送消息
//        Message msg=handler.obtainMessage(6,"kkkkkk");原
        Message msg=handler.obtainMessage(6,bundle);//新
//        msg.what=6;
//        msg.obj="Hello from run()";
//        handler.sendMessage(msg);原
        handler.sendMessage(msg);//新

//思想，html是一个树，找到含有汇率的那个table（枝），在该枝中找汇率对应的td
//获取标签内容：
        Document doc = null;

        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: title="+doc.title());
//            Elements tables =doc.getElementsByTag("table");//table在这里指名称
//            Elenment table1=tables.first();

//            Log.i(TAG, "run: table1="+table1);//第一个分枝数？

//            Elements tds=table.getElementsByTag("td");
//            for(Element td:tds){
//                Log.i(TAG, "run: td="+td);//不同的获取方式
//                Log.i(TAG, "run: td="+td.html());
//                Log.i(TAG, "run: td="+td.text());
//            }

//            Elements class1=table1.getElementsByClass("bz");//属性？
//            for(Element td:class1){
//                Log.i(TAG, "run: td="+td);
//            }
            Element table=doc.getElementsByTag("table").first();//获取元素集中第一个元素
            Elements trs=table.getElementsByTag("tr");
            //方法一：
            for(Element tr:trs){
                Elements tds=tr.getElementsByTag("td");
                if(tds.size()>0){
                    String str=tds.get(0).text();
                    String val=tds.get(5).text();
                    //此处老师原文为：log(str+"=>"+val);
                    Log.i(TAG, "run: "+str+"=>"+val);
                }
            }
            //方法二：
            //此处老师原文为：Element e1=...
            Elements e1=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(27) > td:nth-child(6)");
            dollarRate=Float.valueOf(e1.text().toString());
            Log.i(TAG, "run: 美元汇率="+e1.text());

            Elements e2=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(8) > td:nth-child(6)");
            euroRate=Float.valueOf(e2.text().toString());
            Log.i(TAG, "run: 欧元汇率="+e2.text());

            Elements e3=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(14) > td:nth-child(6)");
            wonRate=Float.valueOf(e3.text().toString());
            Log.i(TAG, "run: 韩元汇率="+e3.text());



        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "run: "+e.toString());
        }

    }

    private String inputStream2String(InputStream inputStream)
            throws IOException{
        final int bufferSize=1024;
        final char[] buffer= new char[bufferSize];
        final StringBuilder out= new StringBuilder();
        Reader in= new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }

}