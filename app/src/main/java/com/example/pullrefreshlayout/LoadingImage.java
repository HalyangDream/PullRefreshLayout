package com.example.pullrefreshlayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

/**
 * 可以加帧动画的 自定义控件
 */
public class LoadingImage extends AppCompatImageView {

    private boolean isLoading;

    private LoadingImageHandler mHandler;

    private AnimationDrawable animLoadingDrawable = null;

    public LoadingImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHandler = new LoadingImageHandler(this);
        // TODO Auto-generated constructor stub
    }

    public LoadingImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new LoadingImageHandler(this);
        // TODO Auto-generated constructor stub
    }

    public LoadingImage(Context context) {
        super(context);
        mHandler = new LoadingImageHandler(this);
        // TODO Auto-generated constructor stub
    }


    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void startLoading() {
        initAnim();
        if (!isLoading) {
            isLoading = true;
            mHandler.postDelayed(AnimationDrawableTask, 300);  //开始绘制动画
        }
    }

    private void initAnim() {
        if (animLoadingDrawable == null)
            animLoadingDrawable = (AnimationDrawable) getDrawable();
    }


    public void stopLoading() {
        initAnim();
        if (isLoading) {
            isLoading = false;
            animLoadingDrawable.stop();
            mHandler.removeCallbacks(AnimationDrawableTask);
        }
    }

    //通过延时控制当前绘制bitmap的位置坐标
    private Runnable AnimationDrawableTask = new Runnable() {
        public void run() {
            if (isLoading) {
                animLoadingDrawable.start();
                mHandler.postDelayed(AnimationDrawableTask, 300);
            }
        }
    };


    private static class LoadingImageHandler extends Handler {

        private WeakReference<LoadingImage> weakReference;

        public LoadingImageHandler(LoadingImage loadingImage) {
            weakReference = new WeakReference<LoadingImage>(loadingImage);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
