package com.zhanghao.pocketweather.UI.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.CityListAdapter;

import java.util.ArrayList;

/**
 * 添加城市界面
 * Created by Administrator on 2016/5/16.
 */
public class AddCityFragment extends Fragment implements View.OnClickListener{
    private View addCityView;
    private ImageView backButton, addCity; //返回按钮，添加城市按钮
    private ListView cityList;//城市列表

    //数据库封装类
    private PocketWeatherDB pocketWeatherDB;
    //城市信息
    private ArrayList<CityInfo> cityInfos;
    //城市列表适配器
    private CityListAdapter adapter;
    //广播接收者
    private AddBraodcastReceiver addBraodcastReceiver;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addCityView = inflater.inflate(R.layout.fragment_add_city, container, false);
        registerBroadCast(); //注册广播
        initView();
        addListener();

        //设置listView的适配器
        pocketWeatherDB=PocketWeatherDB.getInstance(getContext());//获取数据库类
        cityInfos=pocketWeatherDB.queryCityQueen();
        adapter=new CityListAdapter(getContext(),cityInfos);
        cityList.setAdapter(adapter);
        return addCityView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadCast(); //注销广播
    }

    /**
     * 初始化控件
     */
    public void initView() {
        backButton = (ImageView) addCityView.findViewById(R.id.back);
        addCity = (ImageView) addCityView.findViewById(R.id.add_city);
        cityList = (ListView) addCityView.findViewById(R.id.city_info_list);
    }

    /**
     * 设置监听事件
     */
    public void addListener() {
        backButton.setOnClickListener(this);
        addCity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                //返回按钮，回到主Fragment
                backToMain();
                break;
            case R.id.add_city:
                //添加城市
                gotoHotCityFragment();
                break;
        }
    }

    /**
     * 返回MainFragment
     */
    public void backToMain(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment addCityFragment=fragmentManager.findFragmentByTag("addCityFragment");
        Fragment fragmentMain=getActivity().getSupportFragmentManager().findFragmentByTag("mainFragment");
        transaction.remove(addCityFragment);
        transaction.show(fragmentMain);
        transaction.commit();
    }
    /**
     * 进入热门城市界面
     */
    public void gotoHotCityFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment addCityFragment=fragmentManager.findFragmentByTag("addCityFragment");
        HotCityFragment hotCityFragment=new HotCityFragment();
        transaction.add(R.id.main_activity_frame,hotCityFragment,"hotCityFragment");
        transaction.hide(addCityFragment);
        transaction.show(hotCityFragment);
        transaction.commit();
    }
    /**
     * 广播接收器
     */
    private class  AddBraodcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //添加热门城市Action
            if (intent.getAction().equals(ActionConstants.HOT_CITY_ADD_ACTION)){
                Bundle bundle=intent.getExtras();
                CityInfo cityInfo= (CityInfo) bundle.getSerializable("cityInfo");
                cityInfos.add(cityInfo);
                adapter.notifyDataSetChanged();
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
                    if (x[i]==1)
                        cityInfos.remove(i);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
    /**
     * 注册广播
     */
    public void registerBroadCast(){
        addBraodcastReceiver=new AddBraodcastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(ActionConstants.HOT_CITY_ADD_ACTION);
        filter.addAction(ActionConstants.HOT_CITY_CANCEL_ACTION);
        getContext().registerReceiver(addBraodcastReceiver,filter);
    }
    public void unRegisterBroadCast(){
        getContext().unregisterReceiver(addBraodcastReceiver);
    }
}
