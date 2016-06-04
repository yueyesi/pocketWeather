package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhanghao.pocketweather.R;

/**
 * Created by Administrator on 2016/5/5.
 */
public class CityFragment extends Fragment {
    private View cityView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cityView=inflater.inflate(R.layout.fragment_city,container,false);
        return cityView;
    }
}
