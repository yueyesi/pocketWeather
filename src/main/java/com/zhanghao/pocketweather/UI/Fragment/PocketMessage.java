package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zhanghao.pocketweather.Model.entity.Pocket_Message;
import com.zhanghao.pocketweather.Present.AsyncTask.PocketMessageAsyncTask;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.PocketMessageAdatper;

import java.util.ArrayList;

/**
 * 口袋消息界面
 * Created by Administrator on 2016/5/31.
 */
public class PocketMessage extends Fragment {
    private View pocketMessageView;
    private ProgressBar pocketProgress; //进度条
    private ListView pocketetMessageListView; //ListView

    private ArrayList<Pocket_Message> list;

    private PocketMessageAdatper messageAdatper;
    private String url="http://v.juhe.cn/weixin/query?key=d97d3ee9f63471913a6ffc1265f3e623"; //路径

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if(msg.what==1){


               Bundle bundle=msg.getData();
               list= (ArrayList<Pocket_Message>) bundle.getSerializable("pocket_Messages");

               messageAdatper=new PocketMessageAdatper(getContext(),list);
               pocketetMessageListView.setAdapter(messageAdatper);
               pocketProgress.setVisibility(View.INVISIBLE);
           }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pocketMessageView=inflater.inflate(R.layout.fragment_pocket_message,container,false);
        initView();
        setData();
        addListener();
        return pocketMessageView;
    }
    /**
     * 初始化控件
     */
    public void initView(){
        pocketProgress= (ProgressBar) pocketMessageView.findViewById(R.id.pocket_progress);
        pocketetMessageListView= (ListView) pocketMessageView.findViewById(R.id.pocket_message_list);
    }
    /**
     * 设置数据
     */
    public void setData(){
        new PocketMessageAsyncTask(handler).execute(url);
    }
    /**
     * 设置监听
     */
    public void addListener(){
        pocketetMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gotoWebViewFragment(position);
            }
        });
    }
    /**
     * 跳转到WebViewFragment
     */
    public void gotoWebViewFragment(int position){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        WebViewFragment webViewFragment=new WebViewFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",list.get(position).getUrl());
        webViewFragment.setArguments(bundle);
        transaction.add(R.id.main_activity_frame,webViewFragment,"webViewFragment");
        transaction.show(webViewFragment);
        Fragment mainFragment=fragmentManager.findFragmentByTag("mainFragment");
        transaction.hide(mainFragment);
        transaction.commit();
    }
}
