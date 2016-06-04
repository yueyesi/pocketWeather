package com.zhanghao.pocketweather.UI.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanghao.pocketweather.Constants.ActionConstants;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.Model.entity.userInfo;
import com.zhanghao.pocketweather.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 登录界面
 * Created by Administrator on 2016/5/25.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private View loginView;

    private ImageView loginBack; //返回按钮

    private Button loginRegister; //手机号快速注册
    private Button forgetPassword; //忘记密码

    private EditText user_phone,user_psd; //用户手机，用户密码
    private Button login_Button; //登陆
    private ImageView qqLogin,weixinLogin,weiboLogin; //qq,微博，微信

    private TextView error; //错误

    private PocketWeatherDB pocketWeatherDB;


    private ProgressDialog progressDialog; //进度条对话框
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginView=inflater.inflate(R.layout.fragment_login,container,false);

        initView();
        addListener();
        pocketWeatherDB=PocketWeatherDB.getInstance(getContext());

        return  loginView;
    }
    /**
     * 初始化控件
     */
    public void initView(){
        loginBack= (ImageView) loginView.findViewById(R.id.login_back);
        loginRegister= (Button) loginView.findViewById(R.id.login_register);
        forgetPassword= (Button) loginView.findViewById(R.id.forget_password);
        user_phone= (EditText) loginView.findViewById(R.id.user_phone);
        user_psd= (EditText) loginView.findViewById(R.id.user_psd);
        login_Button= (Button) loginView.findViewById(R.id.login_button);
        qqLogin= (ImageView) loginView.findViewById(R.id.qq_login);
        weiboLogin= (ImageView) loginView.findViewById(R.id.weibo_login);
        weixinLogin= (ImageView) loginView.findViewById(R.id.weixin_login);
        error= (TextView) loginView.findViewById(R.id.error);
    }
    /**
     * 设置监听
     */
    public void addListener(){
        loginBack.setOnClickListener(this);
        loginRegister.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        login_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_back:
                //返回按钮
                gotoMainFrgment();
                break;
            case R.id.login_register:
                //进入注册界面
                gotoRegisterFragment();
                break;
            case R.id.forget_password:
                //进入找回密码界面 gotoPasswordFindFragment();
                Toast.makeText(getContext(),"该功能尚未开发",Toast.LENGTH_SHORT).show();
                break;

            case R.id.login_button:
                //登陆按钮

                login();
                break;

        }
    }
    /**
     * 回到主界面
     */
    public void gotoMainFrgment(){

       FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
       FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment mainFragment=fragmentManager.findFragmentByTag("mainFragment");
        Fragment loginFragment=fragmentManager.findFragmentByTag("loginFragment");
        fragmentTransaction.remove(loginFragment);
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.commit();
    }
    /**
     * 到注册页面
     */
    public void gotoRegisterFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        RegisterFragment registerFragment=new RegisterFragment();
        Fragment loginFragment=fragmentManager.findFragmentByTag("loginFragment");
        fragmentTransaction.add(R.id.main_activity_frame,registerFragment,"registerFragment");
        fragmentTransaction.show(registerFragment);
        fragmentTransaction.hide(loginFragment);
        fragmentTransaction.commit();
    }
    /**
     * 到密码界面
     */
    public void gotoPasswordFindFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PasswordFindFragment passwordFindFragment=new PasswordFindFragment();
        Fragment loginFragment=fragmentManager.findFragmentByTag("loginFragment");
        fragmentTransaction.add(R.id.main_activity_frame,passwordFindFragment,"passwordFindFragment");
        fragmentTransaction.show(passwordFindFragment);
        fragmentTransaction.hide(loginFragment);
        fragmentTransaction.commit();
    }
    /**
     * 展现ProgressBar
     */
    public void showProgressDialog(){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在登陆...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    /**
     * 登陆
     */
    public void login(){
        String mobile_phone=user_phone.getText().toString().trim();
        String password=user_psd.getText().toString().trim();

        if (mobile_phone.equals("")||password.equals("")){
            error.setText("请将用户信息填写完整！");
        }
        else{

            if (pocketWeatherDB.isLiveUser(mobile_phone,password)){
                //去本地数据库中查找
                error.setText("");
                Toast.makeText(getContext(),"登陆成功",Toast.LENGTH_SHORT).show();

                //发送广播
                userInfo userInfo=pocketWeatherDB.getUserInfo(mobile_phone,password).get(0);
                Intent  intent=new Intent();
                intent.putExtra("user_name",userInfo.getUser_name());
                intent.putExtra("user_sex",userInfo.getUser_sex());
                intent.putExtra("user_head_pic",userInfo.getUser_head_pic());
                intent.setAction(ActionConstants.SQLITE_LOGIN_SUCCESS_ACTION);
                getContext().sendBroadcast(intent);

                gotoMainFrgment();

            }
            else{
                //去云端数据库中查找

                BmobQuery<userInfo> query=new BmobQuery<>();
                query.addWhereEqualTo("mobile_phone",mobile_phone);
                query.addWhereEqualTo("password",password);
                showProgressDialog();
                query.findObjects(getContext(), new FindListener<userInfo>() {
                    @Override
                    public void onSuccess(List<userInfo> list) {
                        Log.i("info",list.size()+"");
                        if (list.size()>0){
                            progressDialog.dismiss();
                            //发送广播

                            Intent  intent=new Intent();
                            intent.putExtra("user_name",list.get(0).getUser_name());
                            intent.putExtra("user_sex",list.get(0).getUser_sex());
                            intent.putExtra("user_head_pic",list.get(0).getUser_head_pic());
                            intent.setAction(ActionConstants.BMOB_LOGIN_SUCCESS_ACTION);
                            getContext().sendBroadcast(intent);

                            //存入数据库
                            for(int i=0;i<list.size();i++)
                                pocketWeatherDB.saveUserInfo(list.get(i));
                            Toast.makeText(getContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                            gotoMainFrgment();
                        }
                        else {
                            progressDialog.dismiss();
                            error.setText("手机号或密码输入有误");
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        progressDialog.dismiss();
                        error.setText("网络连接有误！");
                    }
                });

            }
        }
    }
}
