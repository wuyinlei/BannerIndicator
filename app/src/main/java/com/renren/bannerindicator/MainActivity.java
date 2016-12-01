package com.renren.bannerindicator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.renren.bannerindicator.banner.BannerFragment;
import com.renren.bannerindicator.listener.MyPagerListener;
import com.renren.bannerindicator.widget.ViewPagerIndicator;

public class MainActivity extends FragmentActivity {
    //广告条的图片资源
    private int[] imgResID = new int[]{R.drawable.banner01,R.drawable.banner02,R.drawable.banner03};
    private Handler mHandler = new Handler();
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mViewPager.setOnPageChangeListener(new MyPagerListener(mIndicator,imgResID.length));
        autoScroll();
    }

    //自动滚动
    private void autoScroll() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取当前的页面下标
                int currentItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(currentItem+1);
                mHandler.postDelayed(this,2000);
            }
        }, 2000);


    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //返回fragment的视图
        @Override
        public Fragment getItem(int position) {
            position %= imgResID.length;

            BannerFragment fragment = new BannerFragment();
            fragment.setImage(imgResID[position]);
            return fragment;
        }

        @Override
        public int getCount() {
            return 100000;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(this);  //防止内存泄露
    }
}
