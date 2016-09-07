package com.shenjianli.lib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.shenjianli.lib.R;
import com.shenjianli.lib.data.DemoData;
import com.shenjianli.shenlib.base.BaseAdapter;

import java.util.List;

/**
 * Created by shenjianli on 2016/7/25.
 */
public class RecylerViewAdapter extends BaseAdapter<DemoData,DemoViewHolder> {

    public OnDemoClickListener getOnDemoClickListener() {
        return mOnDemoClickListener;
    }

    public void setOnDemoClickListener(OnDemoClickListener mOnDemoClickListener) {
        this.mOnDemoClickListener = mOnDemoClickListener;
    }

    OnDemoClickListener mOnDemoClickListener;
    public RecylerViewAdapter(Context context) {
        super(context);
    }

    public RecylerViewAdapter(Context context, List<DemoData> list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public DemoViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new DemoViewHolder(parent, R.layout.item_recylerview_layout);
    }

    @Override
    public void bindCustomViewHolder(DemoViewHolder holder, final int position) {
        DemoData demoData = getItem(position);
        holder.imavPic.setImageResource(demoData.getImgId());
        holder.tvUrl.setText(demoData.getName());
        if (mOnDemoClickListener != null) {
            holder.imavPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnDemoClickListener.onClick(position);
                }
            });
            holder.tvUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnDemoClickListener.onClick(position);
                }
            });
        }
    }

    public interface OnDemoClickListener {
        void onClick(int position);
    }
}
