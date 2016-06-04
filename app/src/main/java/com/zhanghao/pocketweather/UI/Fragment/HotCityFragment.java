package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.CitySearchInfo;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.HotCityAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/17.
 */
public class HotCityFragment extends Fragment implements View.OnClickListener{
    private View hotCityView; //热门城市视图
    private ImageView searchCityButton; //搜索城市按钮
    private GridView hotCityGrid; //热门城市表格
    private ImageView backButton; //返回按钮

    private PocketWeatherDB database; //数据库封装类
    private ArrayList<CitySearchInfo> list;

    private Button moreCity; //更多城市按钮

    private HotCityAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        hotCityView=inflater.inflate(R.layout.fragment_hot_city,container,false);



        database=PocketWeatherDB.getInstance(getContext());
        if (database.queryAllHotCity().size()<=1){

            //第一次进入时
            String items[]=getResources().getStringArray(R.array.arrays);
            for (int i=0;i<items.length;i++){

                CitySearchInfo info=new CitySearchInfo();
                info.setCityName(items[i]);
                info.setIsLocated(0);
                info.setIsSelected(0);
                database.saveHotCity(info);

            }
        }
        else{
            //后面进入时
        }
        list=database.queryAllHotCity();

        initView();
        addListener();
        return hotCityView;
    }
    /**
     * 初始化控件
     */
    public void initView(){
        searchCityButton= (ImageView) hotCityView.findViewById(R.id.search_button_main);
        hotCityGrid= (GridView) hotCityView.findViewById(R.id.hot_city_grid);
        backButton= (ImageView) hotCityView.findViewById(R.id.back_button_se);
        moreCity= (Button) hotCityView.findViewById(R.id.more_city);


        adapter=new HotCityAdapter(getContext(),list);
        hotCityGrid.setAdapter(adapter);

    }
    /**
     * 设置监听
     */
    public void addListener(){
        searchCityButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        moreCity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button_main:
                //点击按钮跳转到搜索城市界面
                gotoSearchCityFragment();
                break;
            case R.id.back_button_se:
                //跳转到添加城市界面
                gotoAddCityFragment();
                break;
            case R.id.more_city:
                //跳转到搜索城市界面
                gotoSearchCityFragment();
                break;
        }
    }

    /**
     * 跳转到搜索城市界面
     */
    public void gotoSearchCityFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment hotCityFragment=fragmentManager.findFragmentByTag("hotCityFragment");
        SearchCityFragment searchCityFragment=new SearchCityFragment();
        transaction.add(R.id.main_activity_frame,searchCityFragment,"searchCityFragment");
        transaction.show(searchCityFragment);
        transaction.hide(hotCityFragment);
        transaction.commit();
    }
    /**
     * 跳转到添加城市界面
     */
    public void gotoAddCityFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment addCityFragment=fragmentManager.findFragmentByTag("addCityFragment");
        Fragment hotCityFragment=fragmentManager.findFragmentByTag("hotCityFragment");
        transaction.remove(hotCityFragment);
        transaction.show(addCityFragment);
        transaction.commit();
    }
}
