package com.zhanghao.pocketweather.Model.entity;

/**
 * 未来每天的天气状况
 * Created by Administrator on 2016/5/8.
 */
public class FutureItemWeather  {
    private String maxTemp;//最高温度
    private String minTemp; //最低温度
    private String weather; //天气
    private String fa;//天气标志00
    private String fb;//天气标志53
    private String wind; //风力和风向
    private String week; //星期
    private String date; //日期
    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }



    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }







    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }



    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
