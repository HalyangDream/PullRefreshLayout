package com.example.pullrefreshlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pullrefreshlayout.activity.GridViewActivity;
import com.example.pullrefreshlayout.activity.ListViewActivity;
import com.example.pullrefreshlayout.activity.RecyclerActivity;
import com.example.pullrefreshlayout.activity.ScorllViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button recyclerBtn, listViewBtn, gridViewBtn, scorllViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerBtn = findViewById(R.id.recycler);
        listViewBtn = findViewById(R.id.listview);
        gridViewBtn = findViewById(R.id.gridview);
        scorllViewBtn = findViewById(R.id.scrollview);

        recyclerBtn.setOnClickListener(this);
        listViewBtn.setOnClickListener(this);
        gridViewBtn.setOnClickListener(this);
        scorllViewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recycler:
                Intent intent = new Intent(this, RecyclerActivity.class);
                startActivity(intent);
                break;
            case R.id.listview:
                Intent listviewIntent = new Intent(this, ListViewActivity.class);
                startActivity(listviewIntent);
                break;
            case R.id.gridview:
                Intent gridviewIntent = new Intent(this, GridViewActivity.class);
                startActivity(gridviewIntent);
                break;
            case R.id.scrollview:
                Intent scrollviewIntent = new Intent(this, ScorllViewActivity.class);
                startActivity(scrollviewIntent);
                break;
            default:
                break;
        }
    }
}
