package com.zhanghao.pocketweather.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhanghao.pocketweather.Model.entity.History;
import com.zhanghao.pocketweather.R;

import java.util.ArrayList;

/**
 * 历史ListView的适配器
 * Created by Administrator on 2016/5/31.
 */
public class HistoryListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<History> histories; //数据
    public HistoryListAdapter(Context context, ArrayList<History> histories) {
        this.context=context;
        this.histories=histories;
    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.history_list_item,null);
            viewHolder.historyIcon= (ImageView) convertView.findViewById(R.id.history_icon);
            viewHolder.historyTitle= (TextView) convertView.findViewById(R.id.history_title);
            viewHolder.historyLunar= (TextView) convertView.findViewById(R.id.history_lunar);
            viewHolder.historyYear= (TextView) convertView.findViewById(R.id.history_year);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.historyTitle.setText(histories.get(position).getTitle());
        viewHolder.historyLunar.setText(histories.get(position).getLunar());
        viewHolder.historyYear.setText(histories.get(position).getYear());
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return histories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private static class ViewHolder{
        ImageView historyIcon;
        TextView historyTitle;
        TextView historyLunar;
        TextView historyYear;
    }
}
