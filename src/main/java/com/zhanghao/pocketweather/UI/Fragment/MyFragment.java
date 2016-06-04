package com.zhanghao.pocketweather.UI.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.userInfo;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.MyToolsAdapter;

import java.util.ArrayList;

/**
 * MyFragment
 * Created by Administrator on 2016/5/5.
 */
public class MyFragment extends Fragment implements View.OnClickListener{
    private View myView;
    private Button user_name;//用户名
    private ImageView user_pic,user_message; //用户头像,消息
    private ListView tools; //工具栏
    private ArrayList<String> arr; //工具栏的数据
    private MyToolsAdapter adapter; //工具栏的适配器
    private View footerView;

    private PocketWeatherDB pocketWeatherDB;
    private ArrayList<userInfo> userInfos; //用户信息

    private ImageView quit_button; //推出账号

    private MyFragmentBroadcastReceiver broadcastReceiver; //广播接收者
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.fragment_my,container,false);
        pocketWeatherDB=PocketWeatherDB.getInstance(getContext());
        userInfos=pocketWeatherDB.getAllUser(); //获取用户信息

        registerBroadcast();
        initView();
        getData();
        addListener();
        return myView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }

    /**
     * 初始化控件
     */
    public void initView(){
        user_pic= (ImageView) myView.findViewById(R.id.user_pic);
        user_name= (Button) myView.findViewById(R.id.user_name);
        user_message= (ImageView) myView.findViewById(R.id.user_message);
        tools= (ListView) myView.findViewById(R.id.tools);
        footerView=LayoutInflater.from(getContext()).inflate(R.layout.footer_view,null);
        quit_button= (ImageView) footerView.findViewById(R.id.quit_button);
        //开始进入时
        if (userInfos.size()!=0)
            user_name.setText(userInfos.get(userInfos.size()-1).getUser_name());

        //开始进入时
        if (userInfos.size()!=0) {
            if (userInfos.get(userInfos.size() - 1).getUser_sex().equals("男")) {
                user_pic.setImageResource(R.mipmap.main_icon);
            } else if (userInfos.get(userInfos.size() - 1).getUser_sex().equals("女")) {
                user_pic.setImageResource(R.mipmap.women_icon2);
            }
        }
    }
    /**
     * 数据获取
     */
    public void getData(){
        //工具栏数据的获取
        arr=new ArrayList<>();
        String str[]=getActivity().getResources().getStringArray(R.array.tools);
        for(int i=0;i<str.length;i++){
            arr.add(str[i]);
        }

        //设置ListView的适配器

        adapter=new MyToolsAdapter(getContext(),arr);
        tools.addFooterView(footerView);
        tools.setAdapter(adapter);

    }
    /**
     * 设置监听器
     */
    public void addListener(){
        tools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickList(position);
            }
        });

        user_name.setOnClickListener(this);

        quit_button.setOnClickListener(this); //推出账号
    }

    public void clickList(int position){
        switch (position){
            case 0:
                Toast.makeText(getContext(),"该功能尚未开发",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getContext(),"该功能尚未开发",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getContext(),"该功能尚未开发",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getContext(),"该功能尚未开发",Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getContext(),"该功能尚未开发",Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(getContext(),"该功能尚未开发",Toast.LENGTH_SHORT).show();
                break;
            case 7:
               showDialog();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_name:
                //点击登录按钮
                if (user_name.getText().equals("登陆")){
                    gotoLoginFragment();
                }
                break;
            case R.id.quit_button:
                //推出登陆
                user_name.setText("登陆");
                user_pic.setImageResource(R.mipmap.women_icon2);
                pocketWeatherDB.deleeAllUserInfo();
                break;
        }
    }

    /**
     * 跳转到登录页面
     */
    public void gotoLoginFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment mainFragment=fragmentManager.findFragmentByTag("mainFragment");
        LoginFragment loginFragment=new LoginFragment();
        fragmentTransaction.add(R.id.main_activity_frame,loginFragment,"loginFragment");
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.show(loginFragment);
        fragmentTransaction.commit();
    }
    /**
     * 注册广播
     */
    public void registerBroadcast(){
        broadcastReceiver=new MyFragmentBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ActionConstants.SQLITE_LOGIN_SUCCESS_ACTION);
        intentFilter.addAction(ActionConstants.BMOB_LOGIN_SUCCESS_ACTION);
        getContext().registerReceiver(broadcastReceiver,intentFilter);
    }
    //广播接收者
    class MyFragmentBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(ActionConstants.SQLITE_LOGIN_SUCCESS_ACTION)){
                    //从数据库获取更新UI

                    String user_name_text=intent.getStringExtra("user_name");
                    String user_sex=intent.getStringExtra("user_sex");
                    String user_head_pic=intent.getStringExtra("user_head_pic");
                    if (user_sex.equals("男")){
                        user_pic.setImageResource(R.mipmap.main_icon);
                    }
                    else if (user_sex.equals("女")){
                        user_pic.setImageResource(R.mipmap.women_icon2);
                    }
                    if (!user_name_text.equals("")){
                        user_name.setText(user_name_text);
                    }
                }
              else if (intent.getAction().equals(ActionConstants.BMOB_LOGIN_SUCCESS_ACTION)){
                //从云服务器获取更新UI
                  Log.i("info","云服务器");
                String user_name_text=intent.getStringExtra("user_name");
                String user_sex=intent.getStringExtra("user_sex");
                String user_head_pic=intent.getStringExtra("user_head_pic");
                if (user_sex.equals("男")){
                    user_pic.setImageResource(R.mipmap.main_icon);
                }
                else if (user_sex.equals("女")){
                    user_pic.setImageResource(R.mipmap.women_icon2);
                }
                if (!user_name_text.equals("")){
                    user_name.setText(user_name_text);
                }
            }

        }
    }
    /**
     * dialog
     */
    public void showDialog(){
        View view=LayoutInflater.from(getContext()).inflate(R.layout.developer_info,null);


        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setCancelable(false);

        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
