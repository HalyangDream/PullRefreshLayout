package com.example.pullrefreshlayout.refresh;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kky on 2017/11/2/0002.
 */

public class PullableNestedScrollView extends NestedScrollView implements Pullable {

    private final String TAG = "PullableNestedScroll";

    public PullableNestedScrollView(Context context) {
        super(context);
    }

    public PullableNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float lastX, lastY;
    private boolean isCanDown;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                Log.i(TAG, "dealtX:=" + dealtX);
                Log.i(TAG, "dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    isCanDown = false;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    isCanDown = true;
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() <= 0) {

            if (!isCanDown) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        View scrollViewChild = getChildAt(0);
        if (null != scrollViewChild) {
            return getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }
}
