package com.zhanghao.pocketweather.Model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/5/26.
 */
public class userInfo extends BmobObject{
        int user_id; //用户id
        String user_name; //用户名
        String mobile_phone; //用户手机号
        String email; //用户邮件
        String password; //用户密码
        String user_sex; //性别
        String user_head_pic; //头像

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_head_pic() {
        return user_head_pic;
    }

    public void setUser_head_pic(String user_head_pic) {
        this.user_head_pic = user_head_pic;
    }
}
