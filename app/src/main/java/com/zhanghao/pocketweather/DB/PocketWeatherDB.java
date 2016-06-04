package com.zhanghao.pocketweather.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.Model.entity.CitySearchInfo;
import com.zhanghao.pocketweather.Model.entity.userInfo;

import java.util.ArrayList;

/**
 * 封装的一些数据库的操作
 * 使用单例模式
 * Created by Administrator on 2016/5/9.
 */
public class PocketWeatherDB {
    private SqlOpenHelper sqlOpenHelper;
    private static PocketWeatherDB pocketWeatherDB;
    private SQLiteDatabase database;

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;
    private PocketWeatherDB(Context context) {
        sqlOpenHelper=new SqlOpenHelper(context,"pocketWeather.db");
        database=sqlOpenHelper.getWritableDatabase();
    }
    public static PocketWeatherDB getInstance(Context context){
        if (pocketWeatherDB==null){
            synchronized (PocketWeatherDB.class){
                if (pocketWeatherDB==null){
                    pocketWeatherDB=new PocketWeatherDB(context);
                }
            }
        }
        return pocketWeatherDB;
    }
    /**
     * 对CityQueen的操作
     * 存储城市信息
     *  如果是存储的定位城市信息，必须先判断是否有定位城市，如果有，删除，在存储
     *  如果不是定位城市，也要判断是否数据库中已经有了相同的数据，没有，才存储
     */
    public void saveCityQeen(CityInfo cityInfo){
        if (cityInfo.getIsLocated()==1){
            //如果是定位城市
            Cursor c=database.query("cityQueen",null,"isLocated=?",new String[]{"1"},null,null,null);
            if (c.getCount()!=0){
                ContentValues locCitys=new ContentValues();
                locCitys.put("city_name ",cityInfo.getCity_name());
                locCitys.put("city_lon",cityInfo.getCity_lon());
                locCitys.put("city_lat",cityInfo.getCity_lat());
                locCitys.put("isLocated",cityInfo.getIsLocated());
                locCitys.put("date",cityInfo.getDate());
               database.update("cityQueen",locCitys,"isLocated=?",new String[]{"1"});


            }
            else {
                ContentValues locCitys=new ContentValues();
                locCitys.put("city_name ",cityInfo.getCity_name());
                locCitys.put("city_lon",cityInfo.getCity_lon());
                locCitys.put("city_lat",cityInfo.getCity_lat());
                locCitys.put("isLocated",cityInfo.getIsLocated());
                locCitys.put("date",cityInfo.getDate());
                database.insert("cityQueen",null,locCitys);
                Log.i("info","save2");
            }
        }
        else{
            Cursor c=database.query("cityQueen",null,"city_name=? and isLocated=?",new String[]{cityInfo.getCity_name(),"0"},null,null,null);
            if (c.getCount()==0){
                ContentValues contentValues=new ContentValues();
                contentValues.put("city_name ",cityInfo.getCity_name());
                contentValues.put("city_lon",cityInfo.getCity_lon());
                contentValues.put("city_lat",cityInfo.getCity_lat());
                contentValues.put("isLocated",cityInfo.getIsLocated());
                contentValues.put("date",cityInfo.getDate());
                database.insert("cityQueen",null,contentValues);
            }

        }
    }
    /**
     *  对CityQueen的操作
     *  查询城市信息
     */

    public ArrayList<CityInfo> queryCityQueen(){
        ArrayList<CityInfo> list=new ArrayList<>();
        Cursor c=database.query("cityQueen",null,null,null,null,null,null);
        if (c!=null){
            String args[]=c.getColumnNames();
            while (c.moveToNext()){
                CityInfo cityInfo=new CityInfo();
               cityInfo.setCity_name(c.getString(c.getColumnIndex("city_name")));
                cityInfo.setCity_lon(c.getDouble(c.getColumnIndex("city_lon")));
                cityInfo.setCity_lat(c.getDouble(c.getColumnIndex("city_lat")));
                cityInfo.setIsLocated(c.getInt(c.getColumnIndex("isLocated")));
                cityInfo.setDate(c.getString(c.getColumnIndex("date")));
                list.add(cityInfo);
            }
        }
        return list;
    }

    /**
     * 对CityQueen的操作
     * 更新定位城市位置
     * @param cityName
     */
    public void updateCityName(String cityName){
        ContentValues Values=new ContentValues();
        Values.put("city_name",cityName);
        database.update("cityQueen",Values,"isLocated=?",new String[]{"1"});
    }

    /**
     * 对CityQueen的操作
     * 根据城市名称删除城市
     */
    public void removeCity(String cityName){
        database.delete("cityQueen","city_name=? and isLocated=?",new String[]{cityName,"0"});
    }
    /**
     * 对CityQueen的操作
     * 判断CityQueen中是否已存在此城市
     * 定位城市不存在此操作
     */
    public boolean isLived(String cityName){
        Cursor c=database.query("cityQueen",null,"city_name=? and isLocated=?",new String[]{cityName,"0"},null,null,null);
        if (c.getCount()!=0){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * 对hotCityQueen的操作
     * 查询选中的城市城市信息
     * 1表示选中，0表示未选中
     */
    public ArrayList<CitySearchInfo> querySelectedHotCity(){
        ArrayList<CitySearchInfo> list=new ArrayList<>();
        Cursor c=database.query("hotCityQueen",null,"isSelected=?",new String[]{"1"},null,null,null);
        if (c.getCount()!=0){

            while (c.moveToNext()){
                CitySearchInfo citySearchInfo=new CitySearchInfo();
                citySearchInfo.set_cityId(c.getInt(c.getColumnIndex("_cityId")));
                citySearchInfo.setCityName(c.getString(c.getColumnIndex("city_name")));
                citySearchInfo.setIsSelected(c.getInt(c.getColumnIndex("isSelected")));
                list.add(citySearchInfo);
            }
        }
        return list;
    }
    /**
     * 对hotCityQueen的操作
     *查询所有的热门城市信息
     */
    public ArrayList<CitySearchInfo> queryAllHotCity(){
        ArrayList<CitySearchInfo> list=new ArrayList<>();
        Cursor c=database.query("hotCityQueen",null,null,null,null,null,null);
        if (c.getCount()!=0){

            while (c.moveToNext()){
                CitySearchInfo citySearchInfo=new CitySearchInfo();
                citySearchInfo.set_cityId(c.getInt(c.getColumnIndex("_cityId")));
                citySearchInfo.setCityName(c.getString(c.getColumnIndex("city_name")));
                citySearchInfo.setIsSelected(c.getInt(c.getColumnIndex("isSelected")));
                citySearchInfo.setIsLocated(c.getInt(c.getColumnIndex("isLocated")));
                list.add(citySearchInfo);
            }
        }
        return list;
    }
    /**
     * 对hotCityQueen的操作
     * 保存热门城市
     */
    public void saveHotCity(CitySearchInfo citySearchInfo){
            if (citySearchInfo.getIsLocated()==1){
                //如果是定位城市
                Cursor c=database.query("hotCityQueen",null,"isLocated=?",new String[]{"1"},null,null,null);
                if (c.getCount()!=0){
                    ContentValues values=new ContentValues();
                    values.put("city_name",citySearchInfo.getCityName());
                    values.put("isSelected",citySearchInfo.getIsSelected());
                    values.put("isLocated",citySearchInfo.getIsLocated());
                    database.update("hotCityQueen",values,"isLocated=?",new String[]{"1"});
                }
                else{
                    ContentValues values=new ContentValues();
                    values.put("city_name",citySearchInfo.getCityName());
                    values.put("isSelected",citySearchInfo.getIsSelected());
                    values.put("isLocated",citySearchInfo.getIsLocated());
                    database.insert("hotCityQueen",null,values);
                }

            }
            //如果不是定位城市
           else{



                ContentValues values=new ContentValues();
                values.put("city_name",citySearchInfo.getCityName());
                values.put("isSelected",citySearchInfo.getIsSelected());
                values.put("isLocated",citySearchInfo.getIsLocated());
                database.insert("hotCityQueen",null,values);
            }
    }
    /**
     * hotCityQueen
     * 更新城市名
     */
    public void updateHotCityName(String cityName){
        ContentValues Values=new ContentValues();
        Values.put("city_name",cityName);
        database.update("hotCityQueen",Values,"isLocated=?",new String[]{"1"});
    }
    /**
     * hotCityQueen
     * 改变状态
     */
    public void saveStateToDB(int state,String cityName){
        ContentValues values=new ContentValues();
        values.put("isSelected",state);
        database.update("hotCityQueen",values,"city_name=?",new String[]{cityName});

    }

    /**
     * 对user_Info数据库的操作
     *
     */

    //是否存在用户
    public boolean isLiveUser(String user_name,String password){
        Cursor c=database.query("userInfo",null,"mobile_phone=? and password=?",new String[]{user_name,password},null,null,null);
        if (c.getCount()!=0){
            return true;
        }
        else{
            return  false;
        }
    }
    //返回存在用户的信息
    public ArrayList<userInfo> getUserInfo(String user_name,String password){
        ArrayList<userInfo> list=new ArrayList<>();
        Cursor c=database.query("userInfo",null,"mobile_phone=? and password=?",new String[]{user_name,password},null,null,null);
        if (c.getCount()!=0){
            while(c.moveToNext()){
                userInfo info=new userInfo();
                info.setUser_id(c.getInt(c.getColumnIndex("user_id")));
                info.setUser_name(c.getString(c.getColumnIndex("user_name")));
                info.setMobile_phone(c.getString(c.getColumnIndex("mobile_phone")));
                info.setEmail(c.getString(c.getColumnIndex("email")));
                info.setPassword(c.getString(c.getColumnIndex("password")));
                info.setUser_sex(c.getString(c.getColumnIndex("user_sex")));
                info.setUser_head_pic(c.getString(c.getColumnIndex("user_head_pic")));
                list.add(info);
            }
        }
        return  list;
    }
    //存入数据库数据
    public void saveUserInfo(userInfo userInfo){
        Cursor c=database.query("userInfo",null,"mobile_phone=? and password=?",new String[]{userInfo.getMobile_phone(),userInfo.getPassword()},null,null,null);
        if (c.getCount()==0) {

                ContentValues values = new ContentValues();
                values.put("user_name",userInfo.getUser_name());
                values.put("mobile_phone",userInfo.getMobile_phone());
                values.put("email",userInfo.getEmail());
                values.put("password",userInfo.getPassword());
                values.put("user_sex",userInfo.getUser_sex());
                values.put("user_head_pic",userInfo.getUser_head_pic());
                database.insert("userInfo",null,values);


        }
    }

    //查找所有用户信息
    public ArrayList<userInfo> getAllUser(){
        ArrayList<userInfo> userInfos=new ArrayList<>();
        Cursor c=database.query("userInfo",null,null,null,null,null,null);
        if (c.getCount()!=0){
            while(c.moveToNext()){
                userInfo info=new userInfo();
                info.setUser_id(c.getInt(c.getColumnIndex("user_id")));
                info.setUser_name(c.getString(c.getColumnIndex("user_name")));
                info.setMobile_phone(c.getString(c.getColumnIndex("mobile_phone")));
                info.setEmail(c.getString(c.getColumnIndex("email")));
                info.setPassword(c.getString(c.getColumnIndex("password")));
                info.setUser_sex(c.getString(c.getColumnIndex("user_sex")));
                info.setUser_head_pic(c.getString(c.getColumnIndex("user_head_pic")));
                userInfos.add(info);
            }
        }
        return  userInfos;
    }
    //清空所有用户信息
    public void deleeAllUserInfo(){
        database.delete("userInfo",null,null);
    }
}
