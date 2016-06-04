package com.zhanghao.pocketweather.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Fragment.WelcomeFragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "60e44e93cb22a97f195e861838ad165d");

        changeUI();
    }
    /**
     *首先进入欢迎界面
     */
    public void changeUI(){
            WelcomeFragment welcomeFragment=new WelcomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame,welcomeFragment).commit();

    }

}
