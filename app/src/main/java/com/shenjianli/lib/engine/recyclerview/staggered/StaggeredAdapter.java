package com.shenjianli.lib.engine.recyclerview.staggered;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.shenjianli.lib.R;
import com.shenjianli.lib.engine.rxjava.model.Item;
import com.shenjianli.shenlib.base.BaseAdapter;

import java.util.List;

/**
 * Created by edianzu on 2016/8/3.
 */
public class StaggeredAdapter extends BaseAdapter<Item,StaggeredHolder>{

    public StaggeredAdapter(Context context) {
        super(context);
    }

    public StaggeredAdapter(Context context, List<Item> list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public StaggeredHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new StaggeredHolder(parent, R.layout.item_staggered_layout);
    }

    @Override
    public void bindCustomViewHolder(StaggeredHolder holder, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,position * 50 + 930);
        holder.itemView.setLayoutParams(params);
        Item item = getItem(position);
        Glide.with(holder.itemView.getContext()).load(item.imageUrl).into(holder.staggeredItemImg);
        holder.staggeredItemTitle.setText(item.description);
    }
}
