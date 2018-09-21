package com.example.pullrefreshlayout.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;


/**
 * Created by Gaofp on 2016/12/6.
 */
public class PullableScrollview extends ScrollView implements Pullable {

    private boolean isCanUp;

    public void setCanUp(boolean canUp) {
        isCanUp = canUp;
    }

    public PullableScrollview(Context context) {
        super(context);
    }


    public PullableScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() <= 0) {
            if (getScrollY() <= 0 && Math.abs(getScrollY()) > (Math.abs(getScrollX()) * 2)) {
                //Log.e("tl", "Y====>true");
                return false;
            } else {
                // Log.e("tl", "Y====>false");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        //return isCanUp;
        View scrollViewChild = getChildAt(0);
        if (null != scrollViewChild) {
            return getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }
}
