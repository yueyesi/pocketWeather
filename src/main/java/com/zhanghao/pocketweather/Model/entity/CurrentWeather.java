package com.zhanghao.pocketweather.Model.entity;

/**
 * 当前天气状况
 * Created by Administrator on 2016/5/8.
 */
public class CurrentWeather {
    private String currTemp;      //当前温度
    private String windDirection; //当前风向
    private String windStrength;  //当前风力
    private String humidity;      //当前湿度
    private String refreshTime="";   //更新时间

    public String getCurrTemp() {
        return currTemp;
    }

    public void setCurrTemp(String currTemp) {
        this.currTemp = currTemp;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(String windStrength) {
        this.windStrength = windStrength;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(String refreshTime) {
        this.refreshTime = refreshTime;
    }
}
