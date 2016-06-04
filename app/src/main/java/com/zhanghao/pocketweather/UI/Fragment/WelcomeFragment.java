package com.zhanghao.pocketweather.UI.Fragment;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.Model.entity.CitySearchInfo;
import com.zhanghao.pocketweather.R;

import java.util.List;

/**
 * 欢迎界面
 * Created by Administrator on 2016/5/1.
 */
public class WelcomeFragment extends Fragment {
    //当前fragment的视图
    private View welcomeView;
    //将IS_FIRST保存在SharedPreferences中
    private static final String IS_FIRST="isFirst";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Handler handler=new Handler();
    //fragment
    private SplashFragment splashFragment;
    private MainFragment mainFragment;

    //百度地图定位
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    //数据库
    private PocketWeatherDB pocketWeatherDB;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        welcomeView=inflater.inflate(R.layout.fragment_welcome,container,false);

        mLocationClient = new LocationClient(getActivity());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        initLocation();
        mLocationClient.start();

        pocketWeatherDB= PocketWeatherDB.getInstance(getActivity());

        openSharedPreference();
        if (!sharedPreferences.getBoolean(IS_FIRST,false)){
            splashFragment=new SplashFragment();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame,splashFragment).commit();
                }
            },1500);
            editor.putBoolean(IS_FIRST,true);
            editor.commit();
        }
        else
        {
            mainFragment=new MainFragment();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame,mainFragment,"mainFragment").commit();
                }
            },1500);

        }
        return welcomeView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void openSharedPreference(){
        sharedPreferences=getActivity().getSharedPreferences("pocketWeather",getActivity().MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    /**
     * 配置定位SDK参数
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    /**
     * 百度定位监听接口 BDLocationListener
     * 如果定位成功，就更新数据库
     * 数据库的操作用单例封装
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            if (location.getLocType()==61||location.getLocType()==161||location.getLocType()==66)
            {
                //定位成功
                //存储信息到数据库，两个数据库都必须存储
                CityInfo cityInfo=new CityInfo();
                cityInfo.setCity_name(location.getAddrStr());
                cityInfo.setCity_lon(location.getLongitude());
                cityInfo.setCity_lat(location.getLatitude());
                cityInfo.setIsLocated(1);
                cityInfo.setDate(location.getTime());
                pocketWeatherDB.saveCityQeen(cityInfo);


                CitySearchInfo citySearchInfo=new CitySearchInfo();
                citySearchInfo.setIsSelected(1);
                citySearchInfo.setCityName(location.getAddrStr());
                citySearchInfo.setIsLocated(1);
                pocketWeatherDB.saveHotCity(citySearchInfo);


            }
            else
            {
                //定位失败
            }
            mLocationClient.stop();
        }
    }
}
