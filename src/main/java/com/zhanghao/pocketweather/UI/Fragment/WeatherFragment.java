package com.zhanghao.pocketweather.UI.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.Model.entity.FutureWeather;
import com.zhanghao.pocketweather.Present.AsyncTask.WeatherAsyncTask;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.WeatherPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 主页面，Weather
 * Created by Administrator on 2016/5/5.
 */
public class WeatherFragment extends Fragment  implements View.OnClickListener{
    private View weatherView;


    //viewPager
    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    private ArrayList<WeatherViewPager> fragments;

    //数据库封装类
    private PocketWeatherDB pocketWeatherDB;
    //城市信息
    private ArrayList<CityInfo> cityInfos;

    //顶部的控件
    private ImageView addCityButton,sharedButton,locMark; //添加城市按钮，分享按钮,定位图片
    private TextView cityName,cityTime; //城市名称，事件

    private Calendar calendar; //日历

    private WeatherPagerAdapter  adapter; //适配器

    //广播接收器
    private  WeatherReceiver weatherReceiver;
    //WeatherFragment的Handler
    private Handler weatherHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1){
                //如果是定位城市，就修改城市名称
                Bundle bundle=msg.getData();
                cityName.setText(bundle.getString("cityName"));
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        weatherView=inflater.inflate(R.layout.fragment_weather,container,false);

        pocketWeatherDB=PocketWeatherDB.getInstance(getContext());
        getData();
        initView(); //初始化控件
        setAdapter();//适配器
        setOnListener();
        registerBroadcast();
        return weatherView;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBraodcast();

    }

    /**
     * 初始化控件
     */
    public void initView(){
        viewPager= (ViewPager) weatherView.findViewById(R.id.weather_viewPager);
        viewPager.setOffscreenPageLimit(0);
        cityName= (TextView) weatherView.findViewById(R.id.top_city_name);
        cityTime= (TextView) weatherView.findViewById(R.id.top_city_time);
        locMark= (ImageView) weatherView.findViewById(R.id.top_loc_mark);
        addCityButton= (ImageView) weatherView.findViewById(R.id.top_add_city_button); //添加城市按钮
        sharedButton= (ImageView) weatherView.findViewById(R.id.shared_button); //分享按钮
        if (cityInfos.size()!=0)
            cityName.setText(cityInfos.get(0).getCity_name());
        //设置时间
        calendar=Calendar.getInstance();
        String day_of_week="";
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:
                day_of_week="周日";
                break;
            case 2:
                day_of_week="周一";
                break;
            case 3:
                day_of_week="周二";
                break;
            case 4:
                day_of_week="周三";
                break;
            case 5:
                day_of_week="周四";
                break;
            case 6:
                day_of_week="周五";
                break;
            case 7:
                day_of_week="周六";
                break;
        }
        String date=""+((calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日"+"  "+day_of_week);
        cityTime.setText(date);
    }

    /**
     * 获取数据
     */
    public  void getData(){
            //获取城市信息
            fragments=new ArrayList<>();
            cityInfos=pocketWeatherDB.queryCityQueen();
            for (int i=0;i<cityInfos.size();i++){
                WeatherViewPager view=new WeatherViewPager();
                view.setCityInfo(cityInfos.get(i));
                view.setWeatherHandler(weatherHandler);
                fragments.add(view);
            }
    }
    /**
     * 设置适配器
     */
    public void setAdapter(){
        fragmentManager=getActivity().getSupportFragmentManager();
         adapter=new WeatherPagerAdapter(fragmentManager,fragments);
        viewPager.setAdapter(adapter);
    }
    /**
     * 设置监听
     */
    public void setOnListener(){
        //viewPager的监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                   cityName.setText(cityInfos.get(position).getCity_name());
                    cityTime.setText("2016/5/15");
                   if(cityInfos.get(position).getIsLocated()==1){
                       locMark.setVisibility(View.VISIBLE);

                   }
                   else{
                       locMark.setVisibility(View.INVISIBLE);
                   }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //添加城市按钮的监听事件
        addCityButton.setOnClickListener(this);
        //分享按钮的监听事件
        sharedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_add_city_button:
                //添加城市按钮
                AddCityFragment addCityFragment=new AddCityFragment();
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmentMain=getActivity().getSupportFragmentManager().findFragmentByTag("mainFragment");
                transaction.hide(fragmentMain);
                transaction.add(R.id.main_activity_frame,addCityFragment,"addCityFragment");
                transaction.commit();
                break;
            case R.id.shared_button:
                //分享按钮
                break;
        }
    }

    /**
     * 广播接收器
     */
    private class WeatherReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //在addFragment中删除城市,在WeatherFragment中加页面
            if (intent.getAction().equals(ActionConstants.DELET_CITY_ACTION)){
                int count=cityInfos.size();
                int x[]=new int[count];
                for (int i=0;i<cityInfos.size();i++){
                    x[i]=0;
                    if (cityInfos.get(i).getCity_name().equals(intent.getStringExtra("city_name"))&&cityInfos.get(i).getIsLocated()==0){
                        x[i]=1;
                    }
                }
                for (int i=0;i<count;i++){
                    if (x[i]==1) {
                        cityInfos.remove(i);
                        fragments.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();

            }
            //添加热门城市Action
            else if (intent.getAction().equals(ActionConstants.HOT_CITY_ADD_ACTION)){
                Bundle bundle=intent.getExtras();
                CityInfo cityInfo= (CityInfo) bundle.getSerializable("cityInfo");
                cityInfos.add(cityInfo);
                WeatherViewPager fragment=new WeatherViewPager();
                fragment.setWeatherHandler(weatherHandler);
                fragment.setCityInfo(cityInfo);
                fragments.add(fragment);
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(fragments.size()-1);

            }
            //删除热门城市Action
            else if (intent.getAction().equals(ActionConstants.HOT_CITY_CANCEL_ACTION)){
                //找出名称相同的城市
                int count=cityInfos.size();
                int x[]=new int[count];
                for (int i=0;i<cityInfos.size();i++){
                    x[i]=0;
                    if (cityInfos.get(i).getCity_name().equals(intent.getStringExtra("city_name"))&&cityInfos.get(i).getIsLocated()==0){
                        x[i]=1;
                    }
                }
                for (int i=0;i<count;i++){
                    if (x[i]==1) {
                        cityInfos.remove(i);
                        fragments.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            else if (intent.getAction().equals(ActionConstants.SEARCH_CITY_ADD_ACTION)){
                //添加查找城市后改变UI
                Log.i("info","sfs");
                CityInfo cityInfo=new CityInfo();
                cityInfo.setCity_name(intent.getStringExtra("city_name"));
                cityInfo.setIsLocated(0);
                cityInfos.add(cityInfo);
                WeatherViewPager fragment=new WeatherViewPager();
                fragment.setWeatherHandler(weatherHandler);
                fragment.setCityInfo(cityInfo);
                fragments.add(fragment);
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(fragments.size()-1);
            }
        }
    }
    /**
     * 注册广播
     *
     */
    public void registerBroadcast(){
        weatherReceiver=new WeatherReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(ActionConstants.DELET_CITY_ACTION);
        filter.addAction(ActionConstants.HOT_CITY_ADD_ACTION);
        filter.addAction(ActionConstants.HOT_CITY_CANCEL_ACTION);
        filter.addAction(ActionConstants.SEARCH_CITY_ADD_ACTION);
        getContext().registerReceiver(weatherReceiver,filter);
    }
    public void unregisterBraodcast(){
        getContext().unregisterReceiver(weatherReceiver);
    }
}
