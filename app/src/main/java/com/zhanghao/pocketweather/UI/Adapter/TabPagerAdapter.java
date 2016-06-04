package com.zhanghao.pocketweather.UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 主页面的适配器
 * Created by Administrator on 2016/5/5.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    public TabPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
