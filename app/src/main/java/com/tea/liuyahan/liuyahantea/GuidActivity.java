package com.tea.liuyahan.liuyahantea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GuidActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager startViewPager;
    // 视图集合
    private List<View> startViews;
    // 圆点集合
    private List<ImageView> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);

        //初始化
        init();
    }

    private void init() {
        startViewPager = (ViewPager) findViewById(R.id.start_view_pager);
        //创建视图
        View view01 = getLayoutInflater().inflate(R.layout.view_guid01,null);
        View view02 = getLayoutInflater().inflate(R.layout.view_guid02,null);
        View view03 = getLayoutInflater().inflate(R.layout.view_guid03,null);
        //初始化圆点
        ImageView point01ImageView = (ImageView) findViewById(R.id.point01_iv);
        ImageView point02ImageView = (ImageView) findViewById(R.id.point02_iv);
        ImageView point03ImageView = (ImageView) findViewById(R.id.point03_iv);
        //初始化第一个圆点选中
        point01ImageView.setSelected(true);
        //将视图添加到views中
        startViews = new ArrayList<>();
        startViews.add(view01);
        startViews.add(view02);
        startViews.add(view03);
        // 初始化圆点
        points = new ArrayList<>();
        points.add(point01ImageView);
        points.add(point02ImageView);
        points.add(point03ImageView);
        //初始化点击按钮
        Button start_btn = (Button) view03.findViewById(R.id.start_btn);
        //绑定点击监听
        start_btn.setOnClickListener(GuidActivity.this);
        //绑定适配器
        StartViewPagerAdapter adapter = new StartViewPagerAdapter(startViews);
        startViewPager.setAdapter(adapter);
        //绑定页面变化监听
        startViewPager.addOnPageChangeListener(GuidActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(GuidActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < points.size(); i++) {
            points.get(i).setSelected(i == position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startViewPager.removeOnPageChangeListener(GuidActivity.this);
    }
}
