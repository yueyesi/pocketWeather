package com.zhanghao.pocketweather.Present.AsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhanghao.pocketweather.Model.entity.History;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 用来获取历史信息
 * Created by Administrator on 2016/5/31.
 */
public class HistoryAsyncTask extends AsyncTask<String,Integer,ArrayList<History>> {
    private Handler handler; //HistoryFragment的ProgressBar
    public HistoryAsyncTask(Handler handler) {
        this.handler=handler;
    }

    @Override

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<History> doInBackground(String... params) {
        String path=params[0];
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(path).build();
        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            if(response.isSuccessful()){
                ArrayList<History> list=getHistory(response.body().string());
                return  list;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public ArrayList<History> getHistory(String str){
        ArrayList<History> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(str);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object= (JSONObject) jsonArray.get(i);
                History history=new History();
                history.setDay(object.getString("day"));
                history.setDes(object.getString("des"));
                history.setMonth(object.getString("month"));
                history.setPic(object.getString("pic"));
                history.setTitle(object.getString("title"));
                history.setLunar(object.getString("lunar"));
                history.setYear(object.getString("year"));
                list.add(history);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    protected void onPostExecute(ArrayList<History> histories) {
        if (histories!=null||histories.size()!=0){
            Message message=new Message();
            message.what=1;
            Bundle bundle=new Bundle();
            bundle.putSerializable("historys",histories);
            message.setData(bundle);
            handler.sendMessage(message);

        }
    }
}
