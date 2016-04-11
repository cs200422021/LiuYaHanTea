package com.tea.liuyahan.liuyahantea;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class StartViewPagerAdapter extends PagerAdapter {
    // 视图集合
    private List<View> views;

    public StartViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    // 获得ViewPager中视图数量
    @Override
    public int getCount() {
        return views.size();
    }

    // 判断ViewPager中展示的View与instantiateItem初始化返回的对象是否一致
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 实例化item（当前item就是View）
    // item的容器，这里指ViewPager
    // 实例化item对应的位置
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    // 销毁Item
    // item的容器，这里指ViewPager
    // 实例化item对应的位置
    // object：在instantiateItem()方法中实例化的object对象
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 注意：必须删除super()语句！
//            super.destroyItem(container, position, object);
        container.removeView(views.get(position));
    }

}
