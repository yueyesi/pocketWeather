package com.zhanghao.pocketweather.Model.entity;

import java.util.ArrayList;

/**
 * 未来几天天气状况
 * Created by Administrator on 2016/5/8.
 */
public class FutureWeather {
    private ArrayList<FutureItemWeather>  future;

    public ArrayList<FutureItemWeather> getFuture() {
        return future;
    }

    public void setFuture(ArrayList<FutureItemWeather> future) {
        this.future = future;
    }
}
