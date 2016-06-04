package com.zhanghao.pocketweather.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.Model.entity.CitySearchInfo;
import com.zhanghao.pocketweather.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 热门城市GridView的适配器
 * Created by Administrator on 2016/5/18.
 */
public class HotCityAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<CitySearchInfo> list;

    private PocketWeatherDB pocketWeatherDB; //数据库封装类
    public HotCityAdapter(Context context, ArrayList<CitySearchInfo> list) {
        this.context=context;
        this.list=list;
        pocketWeatherDB=PocketWeatherDB.getInstance(context);
    }

    @Override
    public int getCount()
    {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.hot_city_item,null);
            holder.textView= (TextView) convertView.findViewById(R.id.hot_city_item);
            holder.loc_mark= (ImageView) convertView.findViewById(R.id.hot_city_mark);
            holder.lin= (LinearLayout) convertView.findViewById(R.id.hot_city_lin);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getCityName());
        //如果是定位城市，显示定位按钮
        if (list.get(position).getIsLocated()==1){
            holder.loc_mark.setVisibility(View.VISIBLE);
            holder.textView.setText("定位");

        }
        else
            holder.loc_mark.setVisibility(View.INVISIBLE);

        //看城市是否被选中
        if (list.get(position).getIsSelected()==1){
            holder.lin.setBackgroundResource(R.drawable.hot_city_selected_bg);
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.hotCitySeletctedText));
        }
        if (list.get(position).getIsSelected()==0){
            holder.lin.setBackgroundResource(R.drawable.hot_city_bg);
            holder.textView.setTextColor(ContextCompat.getColor(context,R.color.hotCityColor));
        }

        //设置点击事件
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定位城市不可点击
                if (list.get(position).getIsLocated()==0) {
                    /**
                     *   发送广播给AddCityFragment,WeatherViewPagerFragment
                     */


                    if (list.get(position).getIsSelected() == 1) {
                        //如果处于选中状态，首先改变UI,其次改变HotCityQueen中的selected值，最后删除CityQueen中的城市
                        holder.lin.setBackgroundResource(R.drawable.hot_city_bg);
                        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.hotCityColor));
                        list.get(position).setIsSelected(0);

                        pocketWeatherDB.saveStateToDB(0, list.get(position).getCityName());
                        pocketWeatherDB.removeCity(list.get(position).getCityName());

                        //发送广播
                        Intent intent=new Intent();
                        intent.putExtra("city_name",list.get(position).getCityName());
                        intent.putExtra("isLocated",list.get(position).getIsLocated());
                        intent.setAction(ActionConstants.HOT_CITY_CANCEL_ACTION);
                        context.sendBroadcast(intent);

                    } else {
                        //如果处于没选中状态,先要改变UI,然后改变HotCityTable中的选中状态，然后添加城市到CityQueen中
                        holder.lin.setBackgroundResource(R.drawable.hot_city_selected_bg);
                        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.hotCitySeletctedText));
                        list.get(position).setIsSelected(1);
                        pocketWeatherDB.saveStateToDB(1, list.get(position).getCityName());

                        CityInfo cityInfo = new CityInfo();
                        cityInfo.setCity_name(list.get(position).getCityName());
                        cityInfo.setIsLocated(0);
                        pocketWeatherDB.saveCityQeen(cityInfo);

                        //发送广播
                        Intent intent=new Intent();
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("cityInfo", cityInfo);
                        intent.putExtras(bundle);
                        intent.setAction(ActionConstants.HOT_CITY_ADD_ACTION);
                        context.sendBroadcast(intent);
                    }
                }
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        TextView textView;
        ImageView loc_mark;
        LinearLayout lin;
    }
}
