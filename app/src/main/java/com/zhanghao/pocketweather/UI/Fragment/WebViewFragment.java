package com.zhanghao.pocketweather.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zhanghao.pocketweather.R;

/**
 * Created by Administrator on 2016/5/31.
 */
public class WebViewFragment extends Fragment implements View.OnClickListener {
    private WebView webView;
    private View view;
    private ImageView webView_back;

    private String url;

    private ProgressBar progressBar; //进度条
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_webview,container,false);
        url=getArguments().getString("url");

        initView();

        setUrl();
        addListener();
        return view;
    }
    /**
     * 初始化控件
     */
    public void initView(){
        webView= (WebView) view.findViewById(R.id.webView);
        webView_back= (ImageView) view.findViewById(R.id.webView_back);
        progressBar= (ProgressBar) view.findViewById(R.id.web_View_progress);
    }
    /**
     * 设置URL
     */
    public void setUrl(){
        webView.requestFocus();
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                view.loadUrl(url);
                return  true;
            }
        });
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    //加载完毕
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    /**
     * 监听事件
     */
    public void addListener(){
        webView_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.webView_back:
                gotoMainFragment();
                break;
        }
    }
    /**
     * 回到MainFragment
     */
    public void gotoMainFragment(){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment webViewFragment=fragmentManager.findFragmentByTag("webViewFragment");
        Fragment mainFragment=fragmentManager.findFragmentByTag("mainFragment");
        transaction.remove(webViewFragment);
        transaction.show(mainFragment);
        transaction.commit();
    }
}
