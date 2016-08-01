// (c)2016 Flipboard Inc, All Rights Reserved.

package com.shenjianli.lib.engine.rxjava.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shenjianli.lib.R;
import com.shenjianli.lib.engine.rxjava.model.Item;
import com.shenjianli.shenlib.base.BaseAdapter;

import java.util.List;


public class ItemListAdapter extends BaseAdapter<Item,DebounceViewHolder>{

    public ItemListAdapter(Context context) {
        super(context);
    }

    public ItemListAdapter(Context context, List<Item> list) {
        super(context, list);
    }

    @Override
    public DebounceViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new DebounceViewHolder(parent,R.layout.grid_item);
    }

    @Override
    public void bindCustomViewHolder(DebounceViewHolder holder, int position) {
        Item image = getItem(position);
        Glide.with(holder.itemView.getContext()).load(image.imageUrl).into(holder.imageIv);
        holder.descriptionTv.setText(image.description);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }
}
