package com.zhanghao.pocketweather.Model.entity;

/**
 * 搜索城市界面的城市信息
 * Created by Administrator on 2016/5/17.
 */
public class CitySearchInfo {
    private int _cityId;    //城市ID
    private String cityName; //城市姓名
    private int isSelected; //是否选中
    private int isLocated; //是否为定位城市

    public int getIsLocated() {
        return isLocated;
    }

    public void setIsLocated(int isLocated) {
        this.isLocated = isLocated;
    }

    public int get_cityId() {
        return _cityId;
    }

    public void set_cityId(int _cityId) {
        this._cityId = _cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
