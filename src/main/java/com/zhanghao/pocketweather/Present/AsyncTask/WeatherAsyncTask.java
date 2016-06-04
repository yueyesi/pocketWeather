package com.zhanghao.pocketweather.Present.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.spdy.FrameReader;
import com.zhanghao.pocketweather.DB.PocketWeatherDB;
import com.zhanghao.pocketweather.DB.SqlOpenHelper;
import com.zhanghao.pocketweather.Model.entity.CityInfo;
import com.zhanghao.pocketweather.Model.entity.CurrentWeather;
import com.zhanghao.pocketweather.Model.entity.FutureItemWeather;
import com.zhanghao.pocketweather.Model.entity.FutureWeather;
import com.zhanghao.pocketweather.Model.entity.TodayWeather;
import com.zhanghao.pocketweather.Model.entity.WeatherInfor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 根据GPS查询天气
 * Created by Administrator on 2016/5/8.
 */
public class WeatherAsyncTask extends AsyncTask<String,Integer,WeatherInfor> {
    private String currentUrl; //当前访问路径
    private ProgressBar progressBar;//进度条
    private Handler handler;
    private WeatherInfor weatherInfor;//城市信息
    private PocketWeatherDB pocketWeatherDB; //数据库
    private Context context;
    private int isLocated;//是否为定位城市
    public WeatherAsyncTask(Handler handler,Context context,int  isLocated) {

        this.handler=handler;
        pocketWeatherDB=PocketWeatherDB.getInstance(context);
        this.isLocated=isLocated;
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }
    @Override
    protected void onPostExecute(WeatherInfor weatherInfor) {
        super.onPostExecute(weatherInfor);


        //传递数据用来更新主界面里面的UI
        Message msg=new Message();
        msg.what=1;
        Bundle bundle=new Bundle();
        bundle.putSerializable("weatherInfor", weatherInfor);
        msg.setData(bundle);
        handler.sendMessage(msg);

    }
    @Override
    protected WeatherInfor doInBackground(String... params) {

         weatherInfor=new WeatherInfor();
        currentUrl=params[0];
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(currentUrl).build();
        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            if (response.isSuccessful()){
                String sj=response.body().string();
                weatherInfor=getWeatherInfo(sj);
                //更新城市名称
                if (isLocated==1){
                    pocketWeatherDB.updateCityName(weatherInfor.getTodayWeather().getCity());
                    pocketWeatherDB.updateHotCityName(weatherInfor.getTodayWeather().getCity());

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherInfor;
    }

    /**
     * 解析json数据
     * @param
     */
    public WeatherInfor getWeatherInfo(String sj){
        WeatherInfor weatherInfor=new WeatherInfor();
        try {
            CurrentWeather currentWeather=new CurrentWeather();
            TodayWeather todayWeather=new TodayWeather();
            FutureWeather futureWeather=new FutureWeather();
            ArrayList<FutureItemWeather> futureItemWeathers=new ArrayList<>();

            JSONObject jsonObject=new JSONObject(sj);
            JSONObject resultObject=jsonObject.getJSONObject("result");
            JSONObject skObject=resultObject.getJSONObject("sk");
            JSONObject todayObject=resultObject.getJSONObject("today");
            JSONArray futureArray=resultObject.getJSONArray("future");
            for (int i=0;i<futureArray.length();i++){
                JSONObject futureItem=futureArray.getJSONObject(i);
                FutureItemWeather futureItemWeather=new FutureItemWeather();
                //温度数据的处理
                String temp=futureItem.getString("temperature").trim();
                futureItemWeather.setMinTemp( temp.split("~")[0]);
                futureItemWeather.setMaxTemp( temp.split("~")[1]);

                futureItemWeather.setWind(futureItem.getString("wind"));
                futureItemWeather.setWeather(futureItem.getString("weather"));
                futureItemWeather.setFa(futureItem.getString("fa"));
                futureItemWeather.setFb(futureItem.getString("fb"));

                futureItemWeather.setWeek(futureItem.getString("week"));
                //日期处理
                String mdate=futureItem.getString("date").toString().trim();
                String date="";
                date=date+mdate.substring(0,4)+"/"+mdate.substring(4,6)+"/"+mdate.substring(6,8);
                futureItemWeather.setDate(date);

                futureItemWeathers.add(futureItemWeather);
            }
            futureWeather.setFuture(futureItemWeathers);

            currentWeather.setCurrTemp(skObject.getString("temp"));
            currentWeather.setWindDirection(skObject.getString("wind_direction"));
            currentWeather.setWindStrength(skObject.getString("wind_strength"));
            currentWeather.setHumidity(skObject.getString("humidity"));
            currentWeather.setRefreshTime(skObject.getString("time"));

            todayWeather.setCity(todayObject.getString("city"));
            //日期处理
            String mdate=todayObject.getString("date_y");
            String date;
            date=mdate.substring(0,4)+"/"+mdate.substring(5,7)+"/"+mdate.substring(8,10);
            todayWeather.setDate(date);

            todayWeather.setWeek(todayObject.getString("week"));
            //今日温度处理
            String temp=todayObject.getString("temperature").trim();
            todayWeather.setMaxTemp( temp.split("~")[1]);
            todayWeather.setMinTemp( temp.split("~")[0]);

            todayWeather.setWeather(todayObject.getString("weather"));
            todayWeather.setWind(todayObject.getString("wind"));
            todayWeather.setDressing_index(todayObject.getString("dressing_index"));
            todayWeather.setDressing_advice(todayObject.getString("dressing_advice"));
            todayWeather.setUv_index(todayObject.getString("uv_index"));
            todayWeather.setComfort_index(todayObject.getString("comfort_index"));
            todayWeather.setWash_index(todayObject.getString("wash_index"));
            todayWeather.setTravel_index(todayObject.getString("travel_index"));
            todayWeather.setExercise_index(todayObject.getString("exercise_index"));
            todayWeather.setDrying_index(todayObject.getString("drying_index"));

            weatherInfor.setCurrentWeather(currentWeather);
            weatherInfor.setTodayWeather(todayWeather);
            weatherInfor.setFutureWeather(futureWeather);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherInfor;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }
}
