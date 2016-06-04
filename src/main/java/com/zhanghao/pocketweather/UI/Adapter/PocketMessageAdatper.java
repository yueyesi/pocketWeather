package com.zhanghao.pocketweather.UI.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhanghao.pocketweather.Model.entity.Pocket_Message;
import com.zhanghao.pocketweather.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CancellationException;

/**
 * 消息
 * Created by Administrator on 2016/5/31.
 */
public class PocketMessageAdatper extends BaseAdapter {

    private ArrayList<Pocket_Message> list;
    private Context context;
    private Calendar calendar;
    private String date="";
    public PocketMessageAdatper(Context context, ArrayList<Pocket_Message> list) {
        this.context=context;
        this.list=list;
        //日期
        calendar=Calendar.getInstance();
        date=date+calendar.get(Calendar.YEAR)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.message_list_item,null);
            viewHolder.messageIcon= (ImageView) convertView.findViewById(R.id.pocket_message_icon);
            viewHolder.messageSource= (TextView) convertView.findViewById(R.id.pocket_message_source);
            viewHolder.messageTitle= (TextView) convertView.findViewById(R.id.pocket_message_title);
            viewHolder.pocketMessageYear= (TextView) convertView.findViewById(R.id.pocket_message_year);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.messageTitle.setText(list.get(position).getTitle());
        viewHolder.messageSource.setText(list.get(position).getSource());
        viewHolder.pocketMessageYear.setText(date);
        return convertView;
    }
    private static class ViewHolder{
        ImageView messageIcon;
        TextView messageTitle;
        TextView messageSource;
        TextView pocketMessageYear;
    }
}
