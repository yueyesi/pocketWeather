package com.zhanghao.pocketweather.Present.AsyncTask;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.spdy.FrameReader;
import com.zhanghao.pocketweather.UI.Adapter.SearchCityListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 获取城市信息
 * Created by Administrator on 2016/5/19.
 */
public class CityAsyncTask extends AsyncTask<String,Integer,ArrayList<String>>{
    private String currentUrl;

    private ProgressBar progressBar;//进度条
    private ListView listView;
    private Context context; //上下文对象
    private ArrayList<String> cities=null;//城市信息
    private Handler handler;

    public ArrayList<String> getCities() {
        return cities;
    }

    public CityAsyncTask(ProgressBar progressBar, ListView listView, Context context,Handler handler) {
        this.progressBar=progressBar;
        this.listView=listView;
        this.context=context;
        this.handler=handler;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        //将数据发送到SearchCityFragment
        Message msg=new Message();
        msg.what=1;
        Bundle bundle=new Bundle();
        bundle.putSerializable("city_list",strings);
        msg.setData(bundle);
        handler.sendMessage(msg);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {

        currentUrl=params[0];
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(currentUrl).build();
        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            if (response.isSuccessful()){
                String cityString=response.body().string();
                cities=getCities(cityString);
                Log.i("info",cities.get(0));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }
    public ArrayList<String> getCities(String cityString){
        ArrayList<String> cities=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(cityString);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            for (int i=0;i<jsonArray.length();i++){
               JSONObject cityName= (JSONObject) jsonArray.get(i);
               cities.add(cityName.getString("CityName"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
