package com.example.pullrefreshlayout.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pullrefreshlayout.R;

import java.util.List;

public class ListOrGridAdapter extends BaseAdapter {

    private List<String> datas;
    private LayoutInflater inflater;

    public ListOrGridAdapter(Context context, List<String> datas) {
        this.inflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    public void setDatas() {
        this.datas.clear();
        this.datas.add("Data");
        this.datas.add("Data");
        this.datas.add("Data");
        this.datas.add("Data");
        this.datas.add("Data");
        notifyDataSetChanged();
    }

    public void addDatas() {
        this.datas.add("data");
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View convertView = this.inflater.inflate(R.layout.item_view, viewGroup, false);
        AppCompatTextView textView = convertView.findViewById(R.id.data);
        textView.setText("Data" + i);
        return convertView;
    }
}
