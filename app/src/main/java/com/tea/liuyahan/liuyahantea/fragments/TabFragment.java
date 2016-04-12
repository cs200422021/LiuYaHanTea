package com.tea.liuyahan.liuyahantea.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tea.liuyahan.liuyahantea.R;
import com.tea.liuyahan.liuyahantea.adapters.NewsFragmentAdapter;
import com.tea.liuyahan.liuyahantea.entity.TeaInfoEntity;
import com.tea.liuyahan.liuyahantea.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;


public class TabFragment extends Fragment implements AbsListView.OnScrollListener {
    private String tab;
    private TextView tabTextView;
    private ListView newsListView;
    //地址相关
    private static final String BASE_URL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=";
    // 分页
    public static final String PAGE = "&page=";
    private static int pageOneNum = 1;
    private static int pageTwoNum = 1;
    private static int pageThreeNum = 1;
    private static int pageFourNum = 1;
    private static int pageFiveNum = 1;
    //首页幻灯片数据路径
    public static final String HOME_URL = BASE_URL + "news.getSlideshow";
    //头条数据
    public static final String HEADLINE_URL = BASE_URL + "news.getHeadlines";
    // 频道类型
    public static final int TYPE_ENCYCLOPEDIA = 16;// 百科
    public static final int TYPE_INFOMATION = 52;// 资讯
    public static final int TYPE_MANAGEMENT = 53;// 经营
    public static final int TYPE_DATA = 54;// 数据
    // 频道接口
    public static final String CHANNEL_URL = BASE_URL + "news.getListByType&row=15&type=";
    public static String URL;
    private boolean isScrollToBottom;


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
        // 绑定适配器
        datas = new ArrayList<>();
        adapter = new NewsFragmentAdapter(getActivity(), datas, handler);
        newsListView.setAdapter(adapter);
        newsListView.setOnScrollListener(this);
        // 下载网络数据
        if ("头条".equals(tab)) {
            URL = HEADLINE_URL + PAGE;
            downloadJson(URL + pageOneNum);
        } else if ("百科".equals(tab)) {
            URL = CHANNEL_URL + TYPE_ENCYCLOPEDIA + PAGE;
            downloadJson(URL + pageTwoNum);
        } else if ("资讯".equals(tab)) {
            URL = CHANNEL_URL + TYPE_INFOMATION + PAGE;
            downloadJson(URL + pageThreeNum);
        } else if ("经营".equals(tab)) {
            URL = CHANNEL_URL + TYPE_MANAGEMENT + PAGE;
            downloadJson(URL + pageFourNum);
        } else if ("数据".equals(tab)) {
            URL = CHANNEL_URL + TYPE_DATA + PAGE;
            downloadJson(URL + pageFiveNum);
        }
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isScrollToBottom && scrollState == SCROLL_STATE_IDLE) {
            loadMore();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        isScrollToBottom = firstVisibleItem + visibleItemCount == totalItemCount;
    }

    private void loadMore() {
        String url = null;
        if ("头条".equals(tab)) {
            pageOneNum++;
            url = HEADLINE_URL + PAGE + pageOneNum;
        } else if ("百科".equals(tab)) {
            pageTwoNum++;
            url = CHANNEL_URL + TYPE_ENCYCLOPEDIA + PAGE + pageTwoNum;
        } else if ("资讯".equals(tab)) {
            pageThreeNum++;
            url = CHANNEL_URL + TYPE_INFOMATION + PAGE + pageThreeNum;
        } else if ("经营".equals(tab)) {
            pageFourNum++;
            url = CHANNEL_URL + TYPE_MANAGEMENT + PAGE + pageFourNum;
        } else if ("数据".equals(tab)) {
            pageFiveNum++;
            url = CHANNEL_URL + TYPE_DATA + PAGE + pageFiveNum;
        }

        downloadJson(url);
    }
}
