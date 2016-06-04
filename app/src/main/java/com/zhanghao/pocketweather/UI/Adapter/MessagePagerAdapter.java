package com.zhanghao.pocketweather.UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 消息界面的适配器
 * Created by Administrator on 2016/5/31.
 */
public class MessagePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;
    private ArrayList<String> titles;
    public MessagePagerAdapter(FragmentManager fm, ArrayList<Fragment> list,ArrayList<String> titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
