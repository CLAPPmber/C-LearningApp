package com.Type;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;



/*
    NotSlidingViewPage 继承ViewPager,重写了ViewPager，使其不可滑动
 */
public class NotSlidingViewPage extends ViewPager {
    private boolean isCanScroll = true;

    public NotSlidingViewPage(Context context) {
        super(context);
    }

    public NotSlidingViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);

    }



    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return  false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

}
