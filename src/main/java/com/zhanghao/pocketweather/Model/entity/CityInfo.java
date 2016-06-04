package com.zhanghao.pocketweather.Model.entity;

import java.io.Serializable;

/**
 * 城市信息
 * Created by Administrator on 2016/5/9.
 */
public class CityInfo implements Serializable {

    private String city_name;   //城市名
    private double city_lon;    //经度
    private double city_lat;     //纬度
    private int isLocated;     //是否是定位城市
    private String date;        //日期


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public double getCity_lon() {
        return city_lon;
    }

    public void setCity_lon(double city_lon) {
        this.city_lon = city_lon;
    }

    public double getCity_lat() {
        return city_lat;
    }

    public void setCity_lat(double city_lat) {
        this.city_lat = city_lat;
    }

    public int getIsLocated() {
        return isLocated;
    }

    public void setIsLocated(int isLocated) {
        this.isLocated = isLocated;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
