package com.example.pullrefreshlayout.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pullrefreshlayout.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<String> datas;
    private LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<String> datas) {
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
        notifyItemInserted(datas.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = this.inflater.inflate(R.layout.item_view, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.appCompatTextView.setText("Data" + position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView appCompatTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            appCompatTextView = itemView.findViewById(R.id.data);
        }
    }

}
