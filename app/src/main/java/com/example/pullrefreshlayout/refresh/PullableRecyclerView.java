package com.example.pullrefreshlayout.refresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kky on 2017/10/31/0031.
 */

public class PullableRecyclerView extends RecyclerView implements Pullable {

    private final String TAG = "PullableRecy";
    private float lastX, lastY;

    private boolean isCanDown = true;

    private boolean isCanUp = true; //是否可以下拉

    private boolean tempIsCanDown = true;

    public PullableRecyclerView(Context context) {
        super(context);
    }

    public PullableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public PullableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCanUp(boolean canUp) {
        isCanUp = canUp;
    }

    public void setCanDown(boolean canDown) {
        tempIsCanDown = canDown;
    }


    /**
     * 处理了一下滑动的冲突
     *
     * @param ev
     * @return
     */

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
//                getParent().requestDisallowInterceptTouchEvent(true);

                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                Log.i(TAG, "dealtX:=" + dealtX);
                Log.i(TAG, "dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    //捕捉到滑动冲突 对刷新组件进行限制
                    //将冲突事件进行向上传递
                    isCanDown = false;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    Log.i(TAG, "if");
                } else {
                    //没有滑动冲突，自己消费掉滑动事件
                    if (tempIsCanDown) {
                        isCanDown = true;
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否可以下拉，并且对RecyclerView 的集中LayoutManager做了处理
     *
     * @return
     */
    @Override
    public boolean canPullDown() {
        if (!tempIsCanDown) {
            return false;
        }
        if (getChildCount() == 0) {

            return isCanDown;
        } else if (getChildAt(0) != null && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            if (getLayoutManager() instanceof LinearLayoutManager) {
                int firstVisibleItem = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem == 0) {
                    return isCanDown;
                }
            } else if (getLayoutManager() instanceof GridLayoutManager) {
                int firstVisibleItem = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem == 0) {
                    return isCanDown;
                }
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) getLayoutManager();
                int[] firstItems = null;
                firstItems = manager.findFirstCompletelyVisibleItemPositions(firstItems);
                if (firstItems != null) {
                    int firstVisibleItem = firstItems[0];
                    if (firstVisibleItem == 0) {
                        return isCanDown;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 是否可以上拉，并且对RecyclerView 的集中LayoutManager做了处理
     *
     * @return
     */

    @Override
    public boolean canPullUp() {
        if (!isCanUp) {
            return false;
        }
        Adapter adapter = getAdapter();
        if (null == adapter || adapter.getItemCount() == 0) {
//            Log.i("PullableRecyclerView", "因为没有数据控件到底了-->001-->" + adapter.getItemCount());
            return false;
        } else {
            if (getLayoutManager() instanceof GridLayoutManager) {
                final int lastItemPosition = adapter.getItemCount() - 1;
                final int lastVisiblePosition = ((GridLayoutManager) getLayoutManager()).
                        findLastVisibleItemPosition();
                Log.i("PullableRecyclerView", "lastItemPosition-->" + lastItemPosition + "lastVisiblePosition-->" + lastVisiblePosition);
                if (lastVisiblePosition >= lastItemPosition - 1) {
                    final int childIndex = lastVisiblePosition -
                            ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    final View lastVisibleChild = getChildAt(childIndex);
                    if (lastVisibleChild != null) {
                        Log.i("PullableRecyclerView", "因为数据控件到底了-->002");
                        return lastVisibleChild.getBottom() <= getBottom();
                    }
                }
            } else if (getLayoutManager() instanceof LinearLayoutManager) {
                final int lastItemPosition = adapter.getItemCount() - 1;
                final int lastVisiblePosition = ((LinearLayoutManager) getLayoutManager()).
                        findLastVisibleItemPosition();
                Log.i("PullableRecyclerView", "lastItemPosition-->" + lastItemPosition + "lastVisiblePosition-->" + lastVisiblePosition);
                if (lastVisiblePosition >= lastItemPosition - 1) {
                    final int childIndex = lastVisiblePosition -
                            ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    final View lastVisibleChild = getChildAt(childIndex);
                    if (lastVisibleChild != null) {
                        Log.i("PullableRecyclerView", "因为数据控件到底了-->002");
                        return lastVisibleChild.getBottom() <= getBottom();
                    }
                }
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                final int lastItemPosition = adapter.getItemCount() - 1;
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) getLayoutManager();
                int[] lastItems = null;
                int[] firstItems = null;
                firstItems = manager.findFirstCompletelyVisibleItemPositions(firstItems);
                lastItems = manager.findLastCompletelyVisibleItemPositions(lastItems);
                Log.i("PullableRecyclerView", "lastItemPosition-->" + lastItemPosition + "lastVisiblePosition-->" + lastItems);
                if (lastItems != null && firstItems != null) {
                    int lastVisiblePosition = lastItems[lastItems.length - 1];
                    if (lastVisiblePosition >= lastItemPosition - 1) {
                        final int childIndex = lastVisiblePosition - firstItems[0];
                        final View lastVisibleChild = getChildAt(childIndex);
                        if (lastVisibleChild != null) {
                            Log.i("PullableRecyclerView", "因为数据控件到底了-->002");
                            return lastVisibleChild.getBottom() <= getBottom();
                        }
                    }
                }
            }
        }
        Log.i("PullableRecyclerView", "因为数据控件没到底-->003");
        return false;
    }

}
