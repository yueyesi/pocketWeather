package com.zhanghao.pocketweather.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * 添加城市的城市界面列表适配器
 * Created by Administrator on 2016/5/16.
 */
public class CityListAdapter extends BaseAdapter{
    private Context context;//上下文对象
    private ArrayList<CityInfo> infos; //城市信息

    private PocketWeatherDB database; //数据库封装类
    public CityListAdapter(Context context, ArrayList<CityInfo> infos) {
        this.context=context;
        this.infos=infos;

        database=PocketWeatherDB.getInstance(context);
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.city_item,parent,false);
            viewHolder.cityIcon= (ImageView) convertView.findViewById(R.id.city_icon);
            viewHolder.cityName= (TextView) convertView.findViewById(R.id.city_name);
            viewHolder.deleteButton= (ImageView) convertView.findViewById(R.id.delete_button);
            viewHolder.locMark= (ImageView) convertView.findViewById(R.id.city_loc_mark);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (infos.get(position).getIsLocated()==1) {
            viewHolder.locMark.setVisibility(View.VISIBLE);
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);
        }
        else {
            viewHolder.locMark.setVisibility(View.INVISIBLE);
            viewHolder.deleteButton.setVisibility(View.VISIBLE);
        }
        viewHolder.cityName.setText(infos.get(position).getCity_name());

        /**   删除按钮的点击事件,
         *    首先要在CityQueen中删除数据，
         *    其次要在HotCityQueen中改变状态
         *    最后要发送广播告诉mainFragment更新UI
         */

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                database.removeCity(infos.get(position).getCity_name());
                database.saveStateToDB(0,infos.get(position).getCity_name());

                 //发送广播给主Fragment,更新UI
                Intent intent=new Intent();
                intent.putExtra("city_name",infos.get(position).getCity_name());
                intent.setAction(ActionConstants.DELET_CITY_ACTION);
                context.sendBroadcast(intent);

                infos.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        ImageView cityIcon;
        ImageView locMark;
        TextView cityName;
        ImageView deleteButton;
    }
}
