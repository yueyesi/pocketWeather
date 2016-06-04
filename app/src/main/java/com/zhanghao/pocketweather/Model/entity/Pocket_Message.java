package com.zhanghao.pocketweather.Model.entity;

import java.io.Serializable;

/**
 * 口袋信息
 * Created by Administrator on 2016/5/31.
 */
public class Pocket_Message implements Serializable {
        private String firstImg="";
        private String id="";
        private String source="";
        private String title="";
        private String url="";
        private String mark="";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
