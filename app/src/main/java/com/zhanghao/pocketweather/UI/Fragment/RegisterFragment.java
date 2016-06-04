package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.service.voice.VoiceInteractionService;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zhanghao.pocketweather.R;

/**
 * 注册页面
 * Created by Administrator on 2016/5/25.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private View registerView;
    private ImageView back; //返回按钮



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerView=inflater.inflate(R.layout.fragment_register,null);


        initView();
        addListener();
        return registerView;
    }
    /**
     * 初始化控件
     */
    public void initView(){
            back= (ImageView) registerView.findViewById(R.id.register_back);
    }
    /**
     * 设置监听器
     */
    public void addListener(){
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_back:
                //返回按钮

                gotoLoginFragment();
                break;
        }

    }
    /**
     * 回到登录页面
     */
    public void gotoLoginFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment loginFragment=fragmentManager.findFragmentByTag("loginFragment");
        Fragment registerFragment=fragmentManager.findFragmentByTag("registerFragment");
        fragmentTransaction.remove(registerFragment);
        fragmentTransaction.show(loginFragment);
        fragmentTransaction.commit();
    }
}
