package com.renren.bannerindicator.listener;

import android.support.v4.view.ViewPager;

import com.renren.bannerindicator.widget.ViewPagerIndicator;


//ViewPager的监听事件
public class MyPagerListener implements ViewPager.OnPageChangeListener {
    private ViewPagerIndicator mIndicator;

    private int count;

    /**
     * 构造器
     *
     * @param indicator 指示器
     * @param length    轮播数据集合长度
     */
    public MyPagerListener(ViewPagerIndicator indicator, int length) {
        mIndicator = indicator;
        count = length;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        position = position % count;  //这里count 是传入的图片集合的长度，取余是为了指示器能够循环往复
        int index = count;  //在这里定义一个变量，用来记录是否是最后一个指示器
        if (position + 1 == count)  //如果是最后的一个指示器
            index = 100;  //随便定义的一个变量，用于约定是否是最后一个
        mIndicator.setOffX(position, positionOffset, index);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
