package com.tea.liuyahan.liuyahantea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.tea.liuyahan.liuyahantea.R;
import com.tea.liuyahan.liuyahantea.entity.NewsEntity;
import com.tea.liuyahan.liuyahantea.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class News_Web_ViewActivity extends AppCompatActivity {
    private WebView newsWebView;
    //接收点击时通过intent传过来的url；
    private String newsUrl;

    //接收数据的
    private List<NewsEntity.DataEntity> datas;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<NewsEntity.DataEntity> tempDatas = (List<NewsEntity.DataEntity>) msg.obj;
            datas.addAll(tempDatas);
            // 加载HTML5代码
            newsWebView.loadDataWithBaseURL(null, datas.get(0).getWap_content(), "text/html", "UTF-8", null);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__web__view);
        newsWebView = (WebView) findViewById(R.id.news_web_view);

        // 设置内嵌app中WebView客户端
        newsWebView.setWebViewClient(new WebViewClient());
        newsWebView.setWebChromeClient(new WebChromeClient());
        newsWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        Intent intent = getIntent();
        newsUrl = intent.getStringExtra("newsUrl");
        datas = new ArrayList<>();
        downloadJson(newsUrl);

    }

    // 开启子线程下载数据
    private void downloadJson(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = MyHttpUtils.getTextFromUrl(url);
                Gson gson = new Gson();
                NewsEntity newsEntity = gson.fromJson(json, NewsEntity.class);
                List<NewsEntity.DataEntity> datas = new ArrayList<NewsEntity.DataEntity>();
                datas.add(newsEntity.getData());

                // 将数据发送至主线程并处理
                Message message = Message.obtain();
                message.obj = datas;
                handler.sendMessage(message);
            }
        }).start();
    }
}
