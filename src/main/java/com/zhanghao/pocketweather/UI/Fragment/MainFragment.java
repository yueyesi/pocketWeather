package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.TabPagerAdapter;
import com.zhanghao.pocketweather.UI.widget.TabViewPager;

import java.util.ArrayList;

/**
 * 主界面
 * Created by Administrator on 2016/5/1.
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    private View mainView;
    //viewPager
    private TabViewPager tabViewPager;
    //底部tab
    private View layoutView;
    private ImageView tab_weather,tab_city,tab_message,tab_my;
    private TextView tab_weather_text,tab_city_text,tab_message_text,tab_my_text;
    //tabViewPager的适配器
    private TabPagerAdapter adapter;
    private FragmentManager manager;
    //数据
    private ArrayList<Fragment> fragList=new ArrayList<>();
    private WeatherFragment weatherFragment;
    private CityFragment cityFragment;
    private MessageFragment messageFragment;
    private MyFragment myFragment;

    private LinearLayout lin1,lin2,lin3,lin0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.fragment_main,container,false);

        if (fragList.size()==0){

        }
        else{
            fragList.clear();
            Log.i("info2","不为0"+fragList.size());
        }
        initView();
        getData();
        setAdatper();
        setOnListener();
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 初始化控件
     */
    public void initView(){
        tabViewPager= (TabViewPager) mainView.findViewById(R.id.tab_viewPager);
        layoutView=mainView.findViewById(R.id.bottom_tab);

        tab_weather= (ImageView) layoutView.findViewById(R.id.weather_tab);
        tab_weather_text= (TextView) layoutView.findViewById(R.id.weather_tab_text);
//        tab_city= (ImageView) layoutView.findViewById(R.id.city_tab);
//        tab_city_text= (TextView) layoutView.findViewById(R.id.city_tab_text);
        tab_message= (ImageView) layoutView.findViewById(R.id.message_tab);
        tab_message_text= (TextView) layoutView.findViewById(R.id.message_tab_text);
        tab_my= (ImageView) layoutView.findViewById(R.id.my_tab);
        tab_my_text= (TextView) layoutView.findViewById(R.id.my_tab_text);
        lin0= (LinearLayout) layoutView.findViewById(R.id.lin0);
//        lin1= (LinearLayout) layoutView.findViewById(R.id.lin1);
        lin2= (LinearLayout) layoutView.findViewById(R.id.lin2);
        lin3= (LinearLayout) layoutView.findViewById(R.id.lin3);
    }
    /**
     * 为tabViewPager设置适配器
     */
    public void setAdatper(){
        tabViewPager.setOffscreenPageLimit(fragList.size()-1);

        manager=getActivity().getSupportFragmentManager();
        adapter=new TabPagerAdapter(manager,fragList);
        tabViewPager.setAdapter(adapter);
    }
    /**
     * 获得数据
     */
    public void getData(){

        weatherFragment = new WeatherFragment();
//        cityFragment=new CityFragment();
        messageFragment=new MessageFragment();
        myFragment=new MyFragment();

        fragList.add(weatherFragment);
//        fragList.add(cityFragment);
        fragList.add(messageFragment);
        fragList.add(myFragment);
    }
    /**
     * 设置监听
     */
    public void setOnListener() {
        lin0.setOnClickListener(this);
//        lin1.setOnClickListener(this);
        lin2.setOnClickListener(this);
        lin3.setOnClickListener(this);
        //为tabViewPager设置监听
        tabViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetView();
                switch (position) {
                    case 0:
                        tab_weather.setImageResource(R.mipmap.tab_pressed_1);
                        tab_weather_text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_text_color_pressed));
                        break;
                    case 1:
                        tab_message.setImageResource(R.mipmap.tab_pressed_3);
                        tab_message_text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_text_color_pressed));
                        break;
                    case 2:
                        tab_my.setImageResource(R.mipmap.tab_pressed_4);
                        tab_my_text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_text_color_pressed));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 按钮的点击事件监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        resetView();
        switch (v.getId()){
            case R.id.lin0:
                tab_weather.setImageResource(R.mipmap.tab_pressed_1);
                tab_weather_text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_text_color_pressed));
                tabViewPager.setCurrentItem(0);
                break;
//            case R.id.lin1:
//                tab_city.setImageResource(R.mipmap.tab_pressed_2);
//                tab_city_text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_text_color_pressed));
//                tabViewPager.setCurrentItem(1);
//                break;
            case R.id.lin2:
                tab_message.setImageResource(R.mipmap.tab_pressed_3);
                tab_message_text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_text_color_pressed));
                tabViewPager.setCurrentItem(1);
                break;
            case R.id.lin3 :
                tab_my.setImageResource(R.mipmap.tab_pressed_4);
                tab_my_text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_text_color_pressed));
                tabViewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 每次滑动viewPager前 重置view
     */
    public void resetView(){
        tab_weather.setImageResource(R.mipmap.tab_normal_1);
//        tab_city.setImageResource(R.mipmap.tab_normal_2);
        tab_message.setImageResource(R.mipmap.tab_normal_3);
        tab_my.setImageResource(R.mipmap.tab_normal_4);
      //  tab_weather_text.setTextColor(getResources().getColor(R.color.tab_text_color)); 6.0 方法弃用
        tab_weather_text.setTextColor(ContextCompat.getColor(getContext(),R.color.tab_text_color));
//        tab_city_text.setTextColor(ContextCompat.getColor(getContext(),R.color.tab_text_color));
        tab_message_text.setTextColor(ContextCompat.getColor(getContext(),R.color.tab_text_color));
        tab_my_text.setTextColor(ContextCompat.getColor(getContext(),R.color.tab_text_color));
    }
}
