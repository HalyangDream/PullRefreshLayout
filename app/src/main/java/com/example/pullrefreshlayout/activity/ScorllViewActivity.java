package com.example.pullrefreshlayout.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.pullrefreshlayout.R;
import com.example.pullrefreshlayout.adapter.ListOrGridAdapter;
import com.example.pullrefreshlayout.refresh.PullToRefreshLayout;
import com.example.pullrefreshlayout.refresh.PullableGridView;
import com.example.pullrefreshlayout.refresh.PullableScrollview;

import java.util.ArrayList;

public class ScorllViewActivity extends AppCompatActivity implements
        PullToRefreshLayout.OnRefreshListener, Handler.Callback {

    private PullToRefreshLayout refreshLayout;


    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorllview);
        refreshLayout = findViewById(R.id.refres_layout);
        PullableScrollview scrollview = findViewById(R.id.scorll_view);
        refreshLayout.setOnRefreshListener(this);
        handler = new Handler(this);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                refreshLayout.refreshFinish(0);
                break;
            case 1:
                refreshLayout.loadmoreFinish(0);
                break;
            default:
                break;
        }
        return false;
    }
}
