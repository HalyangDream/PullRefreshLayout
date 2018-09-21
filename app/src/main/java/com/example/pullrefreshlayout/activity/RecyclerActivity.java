package com.example.pullrefreshlayout.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.pullrefreshlayout.R;
import com.example.pullrefreshlayout.adapter.RecyclerAdapter;
import com.example.pullrefreshlayout.refresh.PullToRefreshLayout;
import com.example.pullrefreshlayout.refresh.PullableRecyclerView;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity implements
        PullToRefreshLayout.OnRefreshListener, Handler.Callback {

    private PullToRefreshLayout refreshLayout;

    private RecyclerAdapter recyclerAdapter;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        refreshLayout = findViewById(R.id.refres_layout);
        PullableRecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerAdapter = new RecyclerAdapter(this, new ArrayList<String>());
        recyclerAdapter.setDatas();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
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
                recyclerAdapter.setDatas();
                break;
            case 1:
                refreshLayout.loadmoreFinish(0);
                recyclerAdapter.addDatas();
                break;
            default:
                break;
        }
        return false;
    }
}
