package com.zhanghao.pocketweather.UI.Fragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhanghao.pocketweather.Model.entity.History;
import com.zhanghao.pocketweather.Present.AsyncTask.HistoryAsyncTask;
import com.zhanghao.pocketweather.R;
import com.zhanghao.pocketweather.UI.Adapter.HistoryListAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 历史上的今天界面
 * Created by Administrator on 2016/5/31.
 */
public class HistoryFragment extends Fragment {
    private View historyView;
    private ListView history_list;
    private ArrayList<History> histories;

    private ProgressBar historyProgress;
    private String url="http://api.juheapi.com/japi/toh?key=8592f4e59b8a4ffa22f4054cf5e5b968";
    private String currentUrl;//当前路径

    private Calendar calendar; //日历

    private HistoryListAdapter listAdapter; //适配器

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                Bundle bundle=msg.getData();
                histories= (ArrayList<History>) bundle.getSerializable("historys");
                historyProgress.setVisibility(View.INVISIBLE);
                listAdapter=new HistoryListAdapter(getContext(),histories);
                history_list.setAdapter(listAdapter);

            }
        }
    };
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        historyView=inflater.inflate(R.layout.fragment_histroy,container,false);
        initView();
        setData();
        addListener();
        return historyView;
    }

    /**
     * 初始化控件
     */
    public void initView(){
        history_list= (ListView) historyView.findViewById(R.id.history_list);
        historyProgress= (ProgressBar) historyView.findViewById(R.id.history_progress);
        calendar=Calendar.getInstance();
    }
    /**
     * 设置数据
     */
    public void setData(){
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        currentUrl=url+"&v=1.0&month="+month+"&day="+day;
        new HistoryAsyncTask(handler).execute(currentUrl);


    }
    /**
     * 监听器
     */
    public void addListener(){
        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(position);
            }
        });
    }
    /**
     * 对话框
     */
    public void showDialog(int position){
        View view=LayoutInflater.from(getContext()).inflate(R.layout.dialog_item,null);
        TextView textView= (TextView) view.findViewById(R.id.dialog_item_text);

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setCancelable(false);
        textView.setText(histories.get(position).getDes());
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
