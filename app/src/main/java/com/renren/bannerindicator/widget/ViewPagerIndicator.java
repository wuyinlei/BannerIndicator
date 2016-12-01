package com.renren.bannerindicator.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.renren.bannerindicator.R;


/**
 * ViewPager指示器
 */
public class ViewPagerIndicator extends View {
    private Paint mForePaint;
    private Paint mBackground;
    /* 圆圈半径 */
    private int radius = 12;
    /* 前景 * 坐标 */
    private float foreOffX;
    /* ○ 与 ○ 之间的间距 */
    private int mNumbers;       /* 数量 */
    private float mGapWidth;
    private int mForeColor;
    private int mBackgroundColor;

    public ViewPagerIndicator(Context context) {
        super(context);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mForeColor = attributes.getColor(R.styleable.ViewPagerIndicator_foreColor, Color.RED);
        mBackgroundColor = attributes.getColor(R.styleable.ViewPagerIndicator_backgroundColor, Color.GRAY);
        mNumbers = attributes.getInt(R.styleable.ViewPagerIndicator_numbers, 3);
        /* 初始化画笔 */
        initPaint();
    }

    /** 初始化画笔 */
    private void initPaint(){
        /* 前景○ */
        mForePaint = new Paint();
        mForePaint.setColor(mForeColor);
        mForePaint.setStyle(Paint.Style.FILL);
        /* 背景○ */
        mBackground = new Paint();
        mBackground.setColor(mBackgroundColor);
        mBackground.setStyle(Paint.Style.FILL);

        /* 间距 */
        mGapWidth = (float) (4.0 * radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mNumbers; i++) {
            /* 起始坐标 + i * 间隔 */
            canvas.drawCircle(16 + i * mGapWidth,16,radius,mBackground);
        }
        canvas.drawCircle(16 + foreOffX,16,radius,mForePaint);
    }

    /**
     *
     * @param position  当前指示器
     * @param pec  滑动的百分比
     * @param index  约定的
     */
    public void setOffX(int position,float pec,int index){
        /* 当前页X坐标 + 起始坐标 */
        if (index == 100)  //如果和约定好的数对应，证明是最后一个
            pec = 0.0f;  //把这个百分比清零，如果不清零 会有一个向右滑动的距离，
        // 造成一个类似后面还有一个指示器的现象
        float pagerX = (float) position * mGapWidth;
        /* 计算出偏移 */
        foreOffX = pagerX + mGapWidth * pec;
        /* 重绘 */
        invalidate();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize= MeasureSpec.getSize(widthMeasureSpec);
        int heightSpectMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);
        //这里就是对wrap_content的支持
        if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpectMode==MeasureSpec.AT_MOST){
            //这里设定的根据你自己自定义View的情况而定
            setMeasuredDimension(2 * radius + 16 + (mNumbers-1) * 3 * radius,16 + 2 * radius);
        }else if(widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(2 * radius + 16 +(mNumbers-1) * 3 * radius,heightSpecSize);

        }else if (heightSpectMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,16 + 2 * radius);
        }

    }
}
