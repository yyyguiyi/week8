package com.example.week8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {

    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HashMap<String,String>> list) {
        super(context, resource, list);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView =convertView;
        if(itemView==null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Map<String,String> map=(Map<String,String>)getItem(position);//获得数据
        //根据数据填充控件
        TextView title=(TextView)itemView.findViewById(R.id.itemTitle);
        TextView detail=(TextView)itemView.findViewById(R.id.itemDetail);
        title.setText("T:"+map.get("ItemTitle"));
        detail.setText("d:"+map.get("ItemDetail"));

        return itemView;
    }
}
