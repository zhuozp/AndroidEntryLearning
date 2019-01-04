package com.example.zhipengzhuo.entrytask.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class UnScrollViewPager  extends ViewPager {

    private boolean mCanScroll = false;

    public UnScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public UnScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        mCanScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mCanScroll;
    }
}
