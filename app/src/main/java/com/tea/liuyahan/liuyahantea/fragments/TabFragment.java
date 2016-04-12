package com.tea.liuyahan.liuyahantea.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tea.liuyahan.liuyahantea.R;
import com.tea.liuyahan.liuyahantea.adapters.NewsFragmentAdapter;
import com.tea.liuyahan.liuyahantea.entity.TeaInfoEntity;
import com.tea.liuyahan.liuyahantea.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;


public class TabFragment extends Fragment {
    private String tab;
    private TextView tabTextView;
    private ListView newsListView;
    //地址相关
    private static final String BASE_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=";
    // 分页
    public static final String PAGE = "&page=";
    //首页幻灯片数据路径
    public static final String HOME_URL = BASE_URL + "news.getSlideshow";
    //头条数据
    public static final String HEADLINE_URL = BASE_URL + "news.getHeadlines";
    //接收数据的
    private List<TeaInfoEntity.DataEntity> datas;
    private NewsFragmentAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<TeaInfoEntity.DataEntity> tempDatas = (List<TeaInfoEntity.DataEntity>) msg.obj;
            datas.addAll(tempDatas);
            adapter.notifyDataSetChanged();
        }
    };


    public static TabFragment newInstance(String tab) {
        Bundle args = new Bundle();
        args.putString("tab", tab);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab = getArguments().getString("tab");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        tabTextView = (TextView) rootView.findViewById(R.id.tab_tv);
        newsListView = (ListView) rootView.findViewById(R.id.news_list_view);
        newsListView.setEmptyView(tabTextView);
        tabTextView.setText(tab);
        // 绑定适配器
        datas = new ArrayList<>();
        adapter = new NewsFragmentAdapter(getActivity(), datas, handler);
        newsListView.setAdapter(adapter);

        // 下载网络数据
        downloadJson(HEADLINE_URL+PAGE+"1");
        return rootView;
    }

    // 开启子线程下载数据
    private void downloadJson(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = MyHttpUtils.getTextFromUrl(url);
                Gson gson = new Gson();
                TeaInfoEntity teaInfoEntity = gson.fromJson(json, TeaInfoEntity.class);
                List<TeaInfoEntity.DataEntity> datas = teaInfoEntity.getData();

                // 将数据发送至主线程并处理
                Message message = Message.obtain();
                message.obj = datas;
                handler.sendMessage(message);
            }
        }).start();
    }
}
