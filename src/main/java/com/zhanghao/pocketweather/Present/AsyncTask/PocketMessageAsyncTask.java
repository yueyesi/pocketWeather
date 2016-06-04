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
import com.zhanghao.pocketweather.Model.entity.Pocket_Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 口袋信息获取
 * Created by Administrator on 2016/5/31.
 */
public class PocketMessageAsyncTask extends AsyncTask<String,Integer,ArrayList<Pocket_Message>> {
    private Handler handler; //PocketMessage的Handler
    public PocketMessageAsyncTask(Handler handler) {
            this.handler=handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Pocket_Message> doInBackground(String... params) {
        String path=params[0];
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(path).build();
        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            if(response.isSuccessful()){
                ArrayList<Pocket_Message> list=getPocketMessage(response.body().string());

                return  list;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * json字符串解析
     * @param
     */
    public ArrayList<Pocket_Message>  getPocketMessage(String str){
        ArrayList<Pocket_Message> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(str);
            JSONObject listObject= (JSONObject) jsonObject.get("result");
            JSONArray jsonArray=listObject.getJSONArray("list");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object= (JSONObject) jsonArray.get(i);
                Pocket_Message message=new Pocket_Message();
                message.setFirstImg(object.getString("firstImg"));
                message.setId(object.getString("id"));

                message.setMark(object.getString("mark"));
                message.setSource(object.getString("source"));
                message.setTitle(object.getString("title"));
                message.setUrl(object.getString("url"));
                list.add(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    protected void onPostExecute(ArrayList<Pocket_Message> pocketMessages) {

        if (pocketMessages!=null||pocketMessages.size()!=0){
            Message message=new Message();
            message.what=1;
            Bundle bundle=new Bundle();
            bundle.putSerializable("pocket_Messages",pocketMessages);

            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

}
