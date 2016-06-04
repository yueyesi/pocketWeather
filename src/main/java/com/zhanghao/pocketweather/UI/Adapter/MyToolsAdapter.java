package com.zhanghao.pocketweather.UI.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhanghao.pocketweather.R;

import java.util.ArrayList;

/**
 * MyFragment里面的工具栏的适配
 * Created by Administrator on 2016/5/25.
 */
public class MyToolsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
   public MyToolsAdapter(Context context, ArrayList<String> list) {
       this.context=context;
       this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.my_tools_item,null);
            viewHolder.toolsName= (TextView) convertView.findViewById(R.id.tools_name);
            viewHolder.toolsPic= (ImageView) convertView.findViewById(R.id.tools_pic);
            viewHolder.pro_Info= (TextView) convertView.findViewById(R.id.pro_info);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(position==0){
            viewHolder.pro_Info.setVisibility(View.INVISIBLE);
            viewHolder.toolsPic.setVisibility(View.VISIBLE);
            viewHolder.toolsPic.setImageResource(R.mipmap.clothes);
            viewHolder.toolsName.setText(list.get(position));
        }
        if(position==1){
            viewHolder.pro_Info.setVisibility(View.INVISIBLE);
            viewHolder.toolsPic.setVisibility(View.VISIBLE);
            viewHolder.toolsPic.setImageResource(R.mipmap.festival);
            viewHolder.toolsName.setText(list.get(position));
        }
        if(position==2){
            viewHolder.pro_Info.setVisibility(View.INVISIBLE);
            viewHolder.toolsPic.setVisibility(View.VISIBLE);
            viewHolder.toolsPic.setImageResource(R.mipmap.skin);
            viewHolder.toolsName.setText(list.get(position));
        }
        if(position==3){
            viewHolder.pro_Info.setVisibility(View.INVISIBLE);
            viewHolder.toolsPic.setVisibility(View.VISIBLE);
            viewHolder.toolsPic.setImageResource(R.mipmap.friend);
            viewHolder.toolsName.setText(list.get(position));
        }
        if(position==4){
            viewHolder.pro_Info.setVisibility(View.INVISIBLE);
            viewHolder.toolsPic.setVisibility(View.VISIBLE);
            viewHolder.toolsPic.setImageResource(R.mipmap.set);
            viewHolder.toolsName.setText(list.get(position));
        }
        if(position==5){
            viewHolder.pro_Info.setVisibility(View.VISIBLE);
            viewHolder.toolsPic.setVisibility(View.INVISIBLE);
            viewHolder.toolsName.setVisibility(View.INVISIBLE);
            viewHolder.pro_Info.setText("产品信息");
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray));


        }
        if(position==6){
            viewHolder.pro_Info.setVisibility(View.INVISIBLE);
            viewHolder.toolsPic.setVisibility(View.INVISIBLE);
            viewHolder.toolsName.setText(list.get(position));
        }
        if (position==7){
            viewHolder.pro_Info.setVisibility(View.INVISIBLE);
            viewHolder.toolsPic.setVisibility(View.INVISIBLE);
            viewHolder.toolsName.setText(list.get(position));
        }
        return convertView;
    }
    private static class ViewHolder{
         ImageView toolsPic;
         TextView toolsName;
        TextView pro_Info;
    }
}
