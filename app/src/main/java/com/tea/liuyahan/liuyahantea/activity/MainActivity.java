package com.tea.liuyahan.liuyahantea.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tea.liuyahan.liuyahantea.R;
import com.tea.liuyahan.liuyahantea.fragments.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //声明用来记录两次退出点击时间
    private long exitTime = 0;

    private TabLayout tabLayout;
    private ViewPager newsViewPager;
    //标签数组
    private String[] tabs;
    // 碎片集合
    private List<TabFragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        init();

        
    }

    private void init() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        newsViewPager = (ViewPager) findViewById(R.id.news_view_pager);
        fragments = new ArrayList<>();
        //获取标签内容
        tabs=getResources().getStringArray(R.array.tabs);
        //为tab赋值
        for (int i = 0;i<tabs.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            TabFragment tabFragment = TabFragment.newInstance(tabs[i]);
            fragments.add(tabFragment);
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        newsViewPager.setAdapter(adapter);

        // TabLayout绑定ViewPager
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(newsViewPager));
        // ViewPager绑定TabLayout
        newsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    };


    @Override
    public void onBackPressed() {
        //点击返回键后调用退出方法，进行二次点击判断
        exit();
    }

    //自定义退出方法
    private void exit() {
        //通过当前系统时间与存储的点击时间判断点击间隔
        if ((System.currentTimeMillis() - exitTime)>2000){
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else {
            finish();
            System.exit(0);
        }
    }
}
