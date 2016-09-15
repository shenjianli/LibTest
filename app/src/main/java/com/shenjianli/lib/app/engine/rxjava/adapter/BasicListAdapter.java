// (c)2016 Flipboard Inc, All Rights Reserved.

package com.shenjianli.lib.app.engine.rxjava.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shenjianli.lib.R;
import com.shenjianli.lib.app.engine.rxjava.model.ZhuangbiImage;
import com.shenjianli.shenlib.base.BaseAdapter;

import java.util.List;

public class BasicListAdapter extends BaseAdapter<ZhuangbiImage,DebounceViewHolder> {

    public BasicListAdapter(Context context) {
        super(context);
    }

    public BasicListAdapter(Context context, List<ZhuangbiImage> list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public DebounceViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new DebounceViewHolder(parent, R.layout.grid_item);
    }

    @Override
    public void bindCustomViewHolder(DebounceViewHolder holder, int position) {
        ZhuangbiImage item = getItem(position);
        Glide.with(holder.itemView.getContext()).load(item.image_url).into(holder.imageIv);
        holder.descriptionTv.setText(item.description);
    }
}
