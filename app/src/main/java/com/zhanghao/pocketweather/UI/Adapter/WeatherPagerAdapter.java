package com.zhanghao.pocketweather.UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.zhanghao.pocketweather.UI.Fragment.WeatherViewPager;

import java.util.ArrayList;

/**
 * 天气信息界面的适配器
 * Created by Administrator on 2016/5/8.
 */
public class WeatherPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<WeatherViewPager> list;
    public WeatherPagerAdapter(FragmentManager fm, ArrayList<WeatherViewPager> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
}
