package com.example.pullrefreshlayout.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable {

    public PullableListView(Context context) {
        super(context);
    }

    public PullableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean isCanPullDown = true;
    private boolean isCanPullUp = true;

    public boolean isCanPullDown() {
        return isCanPullDown;
    }

    public void setCanPullDown(boolean canPullDown) {
        isCanPullDown = canPullDown;
    }

    public boolean isCanPullUp() {
        return isCanPullUp;
    }

    public void setCanPullUp(boolean canPullUp) {
        isCanPullUp = canPullUp;
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            if (!isCanPullDown) {
                return false;
            }
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0) != null && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            if (!isCanPullDown) {
                return false;
            }
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            if (!isCanPullUp) {
                return false;
            }
            return false;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight()) {
                if (!isCanPullUp) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

}
