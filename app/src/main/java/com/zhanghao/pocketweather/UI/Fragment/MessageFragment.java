package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.MessagePagerAdapter;

import java.util.ArrayList;

/**
 * 消息界面
 * Created by Administrator on 2016/5/5.
 */
public class MessageFragment extends Fragment {
    private View messageView;
    private TabLayout messageTitle;
    private ViewPager messageViewPager;

    private ArrayList<String> messageTitles;
    private ArrayList<Fragment> fragments;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        messageView=inflater.inflate(R.layout.fragment_message,container,false);
        initView();
        setData();
        return messageView;
    }
    /**
     * 初始化控件
     */
    public void initView(){
        messageTitle= (TabLayout) messageView.findViewById(R.id.message_title);
        messageViewPager= (ViewPager) messageView.findViewById(R.id.message_viewPager);
    }

    /**
     *设置数据
     */
    public void setData(){
        messageTitles=new ArrayList<>();
        fragments=new ArrayList<>();
        messageTitles.add("口袋消息");
        messageTitles.add("历史上的今天");
        PocketMessage pocketMessage=new PocketMessage();
        HistoryFragment historyFragment=new HistoryFragment();
        fragments.add(pocketMessage);
        fragments.add(historyFragment);
        messageTitle.addTab(messageTitle.newTab().setText(messageTitles.get(0)));
        messageTitle.addTab(messageTitle.newTab().setText(messageTitles.get(1)));

        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        MessagePagerAdapter adapter=new MessagePagerAdapter(fragmentManager,fragments,messageTitles);
        messageViewPager.setAdapter(adapter);
        messageTitle.setupWithViewPager(messageViewPager);
    }
}
