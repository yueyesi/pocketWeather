package com.zhanghao.pocketweather.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.R;

import java.util.ArrayList;

/**
 * SearchFragment里面的城市列表适配器
 * Created by Administrator on 2016/5/19.
 */
public class SearchCityListAdapter extends BaseAdapter implements Filterable{
    private Context context;

    public ArrayList<String> getCities() {
        return cities;
    }

    private ArrayList<String> originalCities;//城市
    private CitiesFilter citiesFilter; //过滤器
    private ArrayList<String> cities; //真正适配的数据

    public SearchCityListAdapter(Context context, ArrayList<String> cites) {
        this.context=context;
        this.originalCities=cites;
        this.cities=cites;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.search_city_list_item,null);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.search_list_city_name);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(cities.get(position));
        return convertView;
    }
    private static class ViewHolder{
        TextView textView;
    }

    @Override
    //获取一个过滤器对象
    public Filter getFilter() {
        if (citiesFilter==null){
            citiesFilter=new CitiesFilter(originalCities);
        }
        return citiesFilter;
    }
    //过滤器类
    private class CitiesFilter extends Filter{
        private ArrayList<String> mcities;
        public CitiesFilter(ArrayList<String> cities) {
            this.mcities=originalCities;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if (constraint==null||constraint.length()==0){
                results.values=mcities;
                results.count=mcities.size();
            }
            else{
                ArrayList<String> lists=new ArrayList<>();
                for (String p:mcities){
                    if (p.toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        lists.add(p);
                    }
                }
                results.values=lists;
                results.count=lists.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cities= (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
