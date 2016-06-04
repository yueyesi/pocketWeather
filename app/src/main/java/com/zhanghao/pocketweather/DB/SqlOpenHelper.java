package com.zhanghao.pocketweather.DB;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.params.StreamConfigurationMap;

/**
 *  SQLOpenHelper类，用来创建和更新数据库
 * Created by Administrator on 2016/5/9.
 */
public class SqlOpenHelper extends SQLiteOpenHelper {
    private static  final String createCityQeen="create table if not exists cityQueen(_id integer primary key autoincrement,city_name text,city_lon real,city_lat real,isLocated integer not null,date text)";
    private static final String createHotCity="create table if not exists hotCityQueen(_cityId integer primary key autoincrement,city_name text not null,isSelected integer not null,isLocated integer not null)";

    private static final String createUserTable="create table if not exists userInfo(user_id integer primary key autoincrement,user_name text,mobile_phone text not null,email text,password text not null,user_sex text,user_head_pic text)";
    public SqlOpenHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建热门城市数据表
        db.execSQL(createHotCity);
        //创建城市列表数据表
        db.execSQL(createCityQeen);
        //创建用户表
        db.execSQL(createUserTable);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
