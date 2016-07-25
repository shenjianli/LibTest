package com.shenjianli.lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.lib.data.DemoData;

import java.util.List;

/**
 * Created by edianzu on 2016/7/25.
 */
public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.DemoViewHolder>{

    private List<DemoData> demoDatas;
    public RecylerViewAdapter(List<DemoData> demoDatas){
        this.demoDatas = demoDatas;
    }
    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载数据item的布局，生成VH返回
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recylerview_layout, parent, false);
        return new DemoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        // 数据绑定
        //ImageLoader.getInstance().displayImage(picUrls[i], demoViewHolder.imavPic);
        holder.imavPic.setImageResource(demoDatas.get(position).getImgId());
        holder.tvUrl.setText(demoDatas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        // 返回数据有多少条
        if (null == demoDatas) {
            return 0;
        }
        return demoDatas.size();
    }

    // 可复用的VH
    public static class DemoViewHolder extends RecyclerView.ViewHolder {
        // 大图
        public ImageView imavPic;
        // 图片url
        public TextView tvUrl;

        public DemoViewHolder(View itemView) {
            super(itemView);
            imavPic = (ImageView) itemView.findViewById(R.id.imavPic);
            tvUrl = (TextView) itemView.findViewById(R.id.tvUrl);
        }
    }
}
