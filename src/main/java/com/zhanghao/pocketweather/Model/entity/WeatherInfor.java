package com.zhanghao.pocketweather.Model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/8.
 */
public class WeatherInfor implements Serializable {
        private CurrentWeather currentWeather;
        private TodayWeather todayWeather;
        private FutureWeather futureWeather;

    public TodayWeather getTodayWeather() {
        return todayWeather;
    }

    public void setTodayWeather(TodayWeather todayWeather) {
        this.todayWeather = todayWeather;
    }

    public FutureWeather getFutureWeather() {
        return futureWeather;
    }

    public void setFutureWeather(FutureWeather futureWeather) {
        this.futureWeather = futureWeather;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
