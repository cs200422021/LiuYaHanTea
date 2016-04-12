package com.tea.liuyahan.liuyahantea.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tea.liuyahan.liuyahantea.R;


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
        return rootView;
    }
}
