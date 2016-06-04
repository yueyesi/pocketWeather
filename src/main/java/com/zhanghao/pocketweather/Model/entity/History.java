package com.zhanghao.pocketweather.Model.entity;

import java.io.Serializable;

/**
 * 历史
 * Created by Administrator on 2016/5/31.
 */
public class History implements Serializable{
        String day=""; //日
        String des=""; //事迹
        String lunar=""; //农历
        String month=""; //月份
        String year="";   //年
        String title="";  //事件标题
        String pic=""; //图片路径

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
