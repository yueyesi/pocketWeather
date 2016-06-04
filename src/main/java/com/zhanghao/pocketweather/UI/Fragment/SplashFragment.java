package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhanghao.pocketweather.R;

import java.util.ArrayList;

/**
 * 引导界面
 * Created by Administrator on 2016/5/1.
 */
public class SplashFragment extends Fragment {
    private View splashView;
    //组合控件
    private ViewPager spalshViewPager;
    //引导界面的四个视图
    private View spalshView1,spalshView2,spalshView3;
    private ArrayList<View> views;
    private ImageView spalshButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater,container);
        setData();
        return splashView;
    }

    /**
     * 初始化控件
     * @param inflater
     * @param container
     */
    public void initView(LayoutInflater inflater,ViewGroup container){
        splashView=inflater.inflate(R.layout.fragment_splash,container,false);
       spalshViewPager= (ViewPager) splashView.findViewById(R.id.spalsh_viewPager);
        spalshView1=inflater.inflate(R.layout.spalsh_view1,null);
        spalshView2=inflater.inflate(R.layout.spalsh_view2,null);
        spalshView3=inflater.inflate(R.layout.spalsh_view3,null);
        spalshButton= (ImageView) spalshView3.findViewById(R.id.splash_button);
        spalshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mainFragment=new MainFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame,mainFragment,"mainFragment").commit();
            }
        });
    }
    /**
     * 设置适配器
     */
    public void setData(){
        views=new ArrayList<>();
        views.add(spalshView1);
        views.add(spalshView2);
        views.add(spalshView3);

        spalshViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(views.get(position));
                }
        });
    }
}
