package com.zhanghao.pocketweather.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.print.PrintHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.Present.AsyncTask.CityAsyncTask;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.SearchCityListAdapter;

import java.util.ArrayList;

/**
 * 搜索城市界面，使用EditText+ListView实现动态搜索的效果
 * Created by Administrator on 2016/5/18.
 */
public class SearchCityFragment extends Fragment implements View.OnClickListener{
    private View searchCityView;
    private EditText searchEdit; //搜索框
    private ListView city_list;  //城市列表
    private Button cancelButton; //取消按钮

    private ProgressBar progressBar; //进度条

    private static final String Url="http://apis.haoservice.com/weather/city?key=898d29f0c1d54373a030b09704323cea";
    private String currenUrl; //当前路径

    private PocketWeatherDB database; //数据库封装类

    private ArrayList<String> cities; //城市信息

    private SearchCityListAdapter adapter;//适配器

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //获取城市从AsyncTask中传来的城市信息
            if (msg.what==1){
                Bundle bundle=msg.getData();
                cities= (ArrayList<String>) bundle.getSerializable("city_list");
                adapter=new SearchCityListAdapter(getContext(),cities);
                city_list.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchCityView=inflater.inflate(R.layout.fragment_search_city,container,false);
        database=PocketWeatherDB.getInstance(getContext());
        initView();
        addListener();
        return searchCityView;
    }
    /**
     * 初始化控件
     */
    public void initView(){
        searchEdit= (EditText) searchCityView.findViewById(R.id.search_edit);
        city_list= (ListView) searchCityView.findViewById(R.id.city_list);
        cancelButton= (Button) searchCityView.findViewById(R.id.cancel_button);
        progressBar= (ProgressBar) searchCityView.findViewById(R.id.search_progressBar);


      new CityAsyncTask(progressBar,city_list,getContext(),handler).execute(Url);


    }
    /**
     * 设置监听器
     */
    public void addListener(){
        cancelButton.setOnClickListener(this); //取消按钮

        //EditText的监听器
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //ListView的监听
        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                * 首先判断数据库中是否有这样的数据，如果有Toast，已存在城市
                * 如果没有先写入数据库，然后跳转到WeatherFragment
                *
                */

                if (database.isLived(adapter.getCities().get(position))){
                    Toast.makeText(getContext(),"该城市已存在",Toast.LENGTH_SHORT).show();
                }
                else{
                    //存入数据库
                    CityInfo cityInfo=new CityInfo();
                    cityInfo.setIsLocated(0);
                    cityInfo.setCity_name(adapter.getCities().get(position));
                    database.saveCityQeen(cityInfo);

                    //发送广播给WeatherFragment更改UI
                    Intent intent=new Intent();
                    intent.putExtra("city_name",adapter.getCities().get(position));
                    intent.setAction(ActionConstants.SEARCH_CITY_ADD_ACTION);
                    getContext().sendBroadcast(intent);
                    //返回主页面
                   gotoWeatherFragment();

                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_button:
                //点击取消按钮，回到热门城市界面
                gotoHotCityFragment();
                break;
        }
    }

    /**
     * 回到SearchCityFragment
     */
    public void gotoHotCityFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment searchCityFragment=fragmentManager.findFragmentByTag("searchCityFragment");
        Fragment hotCityFragment=fragmentManager.findFragmentByTag("hotCityFragment");
        fragmentTransaction.remove(searchCityFragment);
        fragmentTransaction.show(hotCityFragment);
        fragmentTransaction.commit();
    }
    /**
     * 点击ListView跳转到主界面
     */
    public void gotoWeatherFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment mainFragment=fragmentManager.findFragmentByTag("mainFragment");
        Fragment addCityFragment=fragmentManager.findFragmentByTag("addCityFragment");
        Fragment searchCityFragment=fragmentManager.findFragmentByTag("searchCityFragment");
        Fragment hotCityFragment=fragmentManager.findFragmentByTag("hotCityFragment");
        fragmentTransaction.remove(addCityFragment);
        fragmentTransaction.remove(hotCityFragment);
        fragmentTransaction.remove(searchCityFragment);
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.commit();
    }
}
