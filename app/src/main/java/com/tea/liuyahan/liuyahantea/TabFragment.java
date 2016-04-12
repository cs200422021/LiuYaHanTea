package com.tea.liuyahan.liuyahantea;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class TabFragment extends Fragment {
    private String tab;
    private TextView tabTextView;
    private ListView newsListView;

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
