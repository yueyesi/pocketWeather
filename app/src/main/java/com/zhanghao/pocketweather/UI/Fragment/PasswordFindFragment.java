package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhanghao.pocketweather.R;

/**
 * 密码找回界面
 * Created by Administrator on 2016/5/25.
 */
public class PasswordFindFragment extends Fragment{
    private View passwordFind;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        passwordFind=inflater.inflate(R.layout.fragment_password_find,null);
        return  passwordFind;
    }
}
