package com.zhanghao.pocketweather.UI.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.Model.entity.WeatherInfor;
import com.zhanghao.pocketweather.Present.AsyncTask.WeatherAsyncTask;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.widget.TreadView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * 用来显示WeatherFragment里面的ViewPager中的每个城市的情况
 * Created by Administrator on 2016/5/8.
 */
public class WeatherViewPager extends Fragment {
    private View view;
    private CityInfo cityInfo; //传入的该城市的信息
    /**
     * 折线图
     */
    private TreadView treadView;
    private float treadViewWidth,trendViewHeight; //折线图的宽度和高度
    private ArrayList<Integer> maxTemps;//折线图的最高气温集
    private ArrayList<Integer> minTemps;//折线图的最低气温集


    //更新时间,当前天气，当前温度,当前风向及风力，体感温度，湿度,紫外线强度
    private TextView refreshTime,currentWeather,currentTemp,windDirection,tiganTemp,humidity,ultStrength;
    private ImageView weatherGirl; //天气人物
    //未来几天天气的参数
    private TextView weekDay1,weekDay2,weekDay3,weekDay4,weekDay5; //周几
    private TextView weekdayWeather1,weekdayWeather2,weekdayWeather3,weekdayWeather4,weekdayWeather5; //天气情况
    private TextView weekdayWinDir1,weekdayWinDir2,weekdayWinDir3,weekdayWinDir4,weekdayWinDir5; //风向及风力

    private TextView weekdayDate1,weekdayDate2,weekdayDate3,weekdayDate4,weekdayDate5;//日期

    //进度条
    private FrameLayout progressBar;


    //获取数据路径
    private static final String urL="http://apis.haoservice.com/weather/geo?lon="; //按经纬度查找
    private static final String urL2="http://apis.haoservice.com/weather?cityname=";       //按城市名称查找
    private String currentUrl;

    //数据信息
    private WeatherInfor weatherInfor;

    //WeatherViewPager的Handler
    private Handler weatherHandler;
    //设置传进来的WeatherViewPager的Handler

    public void setWeatherHandler(Handler weatherHandler) {
        this.weatherHandler = weatherHandler;
    }

    //自身的handler
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                Bundle bundle=msg.getData();
                weatherInfor= (WeatherInfor) bundle.getSerializable("weatherInfor");
                //更新UI

                refreshTime.setText(weatherInfor.getCurrentWeather().getRefreshTime().trim()+"更新");
                currentWeather.setText(weatherInfor.getTodayWeather().getWeather().trim());
                currentTemp.setText(weatherInfor.getCurrentWeather().getCurrTemp().trim()+"°");
                windDirection.setText(weatherInfor.getCurrentWeather().getWindDirection().trim()+weatherInfor.getCurrentWeather().getWindStrength().trim());
                tiganTemp.setText(weatherInfor.getCurrentWeather().getCurrTemp().trim()+"°");
                humidity.setText(weatherInfor.getCurrentWeather().getHumidity().trim()+"%");
                ultStrength.setText(weatherInfor.getTodayWeather().getUv_index().trim());
                weekDay1.setText("今日");
                weekDay2.setText(weatherInfor.getFutureWeather().getFuture().get(0).getWeek());
                weekDay3.setText(weatherInfor.getFutureWeather().getFuture().get(1).getWeek());
                weekDay4.setText(weatherInfor.getFutureWeather().getFuture().get(2).getWeek());
                weekDay5.setText(weatherInfor.getFutureWeather().getFuture().get(3).getWeek());
                weekdayWeather1.setText(weatherInfor.getTodayWeather().getWeather());
                weekdayWeather2.setText(weatherInfor.getFutureWeather().getFuture().get(0).getWeather());
                weekdayWeather3.setText(weatherInfor.getFutureWeather().getFuture().get(1).getWeather());
                weekdayWeather4.setText(weatherInfor.getFutureWeather().getFuture().get(2).getWeather());
                weekdayWeather5.setText(weatherInfor.getFutureWeather().getFuture().get(3).getWeather());
                weekdayWinDir1.setText(weatherInfor.getTodayWeather().getWind());
                weekdayWinDir2.setText(weatherInfor.getFutureWeather().getFuture().get(0).getWind());
                weekdayWinDir3.setText(weatherInfor.getFutureWeather().getFuture().get(1).getWind());
                weekdayWinDir4.setText(weatherInfor.getFutureWeather().getFuture().get(2).getWind());
                weekdayWinDir5.setText(weatherInfor.getFutureWeather().getFuture().get(3).getWind());
                weekdayDate1.setText(weatherInfor.getTodayWeather().getDate());
                weekdayDate2.setText(weatherInfor.getFutureWeather().getFuture().get(0).getDate());
                weekdayDate3.setText(weatherInfor.getFutureWeather().getFuture().get(1).getDate());
                weekdayDate4.setText(weatherInfor.getFutureWeather().getFuture().get(2).getDate());
                weekdayDate5.setText(weatherInfor.getFutureWeather().getFuture().get(3).getDate());
                maxTemps.clear();
                minTemps.clear();
                maxTemps.add(Integer.valueOf(weatherInfor.getTodayWeather().getMaxTemp()));
                minTemps.add(Integer.valueOf(weatherInfor.getTodayWeather().getMinTemp()));
                maxTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(0).getMaxTemp()));
                maxTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(1).getMaxTemp()));
                maxTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(2).getMaxTemp()));
                maxTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(3).getMaxTemp()));
                minTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(0).getMinTemp()));
                minTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(1).getMinTemp()));
                minTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(2).getMinTemp()));
                minTemps.add(Integer.valueOf(weatherInfor.getFutureWeather().getFuture().get(3).getMinTemp()));
                treadView.setTemperature(maxTemps,minTemps);
                //如果是定位城市发送给UI去更新城市名称
                if (cityInfo.getIsLocated()==1){
                    Message msg1=new Message();
                    msg1.what=1;
                    Bundle bundle1=new Bundle();
                    bundle1.putString("cityName",weatherInfor.getTodayWeather().getCity());
                    msg1.setData(bundle1);
                    weatherHandler.sendMessage(msg1);
                }

                //进度条
                progressBar.setVisibility(View.INVISIBLE);


            }
        }
    };
    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_weather_viewpager,container,false);

        initView();
        //判断是否为定位城市，以不同的方式获取数据
        if (cityInfo.getIsLocated()==1){
            currentUrl=urL+cityInfo.getCity_lon()+"&lat="+cityInfo.getCity_lat()+"&key=898d29f0c1d54373a030b09704323cea";
                new WeatherAsyncTask(handler,getContext(),1).execute(currentUrl); //按经纬度查找数据
        }
        else{
            currentUrl=urL2+cityInfo.getCity_name()+"&key=898d29f0c1d54373a030b09704323cea";
               new WeatherAsyncTask(handler,getContext(),0).execute(currentUrl); //按城市名查找数据
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    /**
     * 初始化控件
     */
    public void initView(){
        //进度条
        progressBar= (FrameLayout) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //折线图
        treadViewWidth=getActivity().getWindowManager().getDefaultDisplay().getWidth();
        trendViewHeight=getActivity().getWindowManager().getDefaultDisplay().getHeight()*2/(3.0f);
        treadView= (TreadView) view.findViewById(R.id.trendView);
        treadView.setSize(treadViewWidth,trendViewHeight);
        maxTemps=new ArrayList<>();
        minTemps=new ArrayList<>();
        maxTemps.add(21);
        maxTemps.add(21);
        maxTemps.add(21);
        maxTemps.add(21);
        maxTemps.add(21);
        minTemps.add(19);
        minTemps.add(19);
        minTemps.add(19);
        minTemps.add(19);
        minTemps.add(19);
        //今天天气控件
        treadView.setTemperature(maxTemps,minTemps);
        refreshTime= (TextView) view.findViewById(R.id.refresh_time);
        currentWeather= (TextView) view.findViewById(R.id.current_weather);
        currentTemp= (TextView) view.findViewById(R.id.current_temp);
        weatherGirl= (ImageView) view.findViewById(R.id.weather_girl);
        windDirection= (TextView) view.findViewById(R.id.wind_direction);
        tiganTemp= (TextView) view.findViewById(R.id.tigan_temp_text);
        humidity= (TextView) view.findViewById(R.id.humidity_text);
        ultStrength= (TextView) view.findViewById(R.id.ult_strength_text);
        //未来天气控件
        weekDay1= (TextView) view.findViewById(R.id.weekday1);
        weekDay2= (TextView) view.findViewById(R.id.weekday2);
        weekDay3= (TextView) view.findViewById(R.id.weekday3);
        weekDay4= (TextView) view.findViewById(R.id.weekday4);
        weekDay5= (TextView) view.findViewById(R.id.weekday5);
        weekdayWeather1= (TextView) view.findViewById(R.id.weekday1_weather);
        weekdayWinDir1= (TextView) view.findViewById(R.id.weekday1_windDirection);

        weekdayDate1= (TextView) view.findViewById(R.id.weekday1_date);
        weekdayWeather2= (TextView) view.findViewById(R.id.weekday2_weather);
        weekdayWinDir2= (TextView) view.findViewById(R.id.weekday2_windDirection);

        weekdayDate2= (TextView) view.findViewById(R.id.weekday2_date);
        weekdayWeather3= (TextView) view.findViewById(R.id.weekday3_weather);
        weekdayWinDir3= (TextView) view.findViewById(R.id.weekday3_windDirection);

        weekdayDate3= (TextView) view.findViewById(R.id.weekday3_date);
        weekdayWeather4= (TextView) view.findViewById(R.id.weekday4_weather);
        weekdayWinDir4= (TextView) view.findViewById(R.id.weekday4_windDirection);

        weekdayDate4= (TextView) view.findViewById(R.id.weekday4_date);
        weekdayWeather5= (TextView) view.findViewById(R.id.weekday5_weather);
        weekdayWinDir5= (TextView) view.findViewById(R.id.weekday5_windDirection);

        weekdayDate5= (TextView) view.findViewById(R.id.weekday5_date);


    }

}
