package com.zhanghao.pocketweather.Constants;

/**
 * ACTION类
 * Created by Administrator on 2016/5/19.
 */
public class ActionConstants  {
    public static final String DELET_CITY_ACTION="DELET_CITY_ACTION"; //从AddCityFragment删除数据后，改变WeatherPagerFragmentUI的ACTION
    public static final String HOT_CITY_ADD_ACTION="HOT_CITY_ADD_ACTION"; //添加热门城市之后的ACTION
    public static final String HOT_CITY_CANCEL_ACTION="HOT_CITY_CANCEL_ACTION"; //热门城市取消之后的ACTION

    public static final String SEARCH_CITY_ADD_ACTION="SEARCH_CITY_ADD_ACTION"; //添加城市界面 添加城市动作

    public static final String SQLITE_LOGIN_SUCCESS_ACTION="SQLITE_LOGIN_SUCCESS_ACTION"; //登陆成功，数据库
    public static final String BMOB_LOGIN_SUCCESS_ACTION=" BMOB_LOGIN_SUCCESS_ACTION"; //登陆成功，云服务器
}
