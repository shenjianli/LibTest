package com.shenjianli.lib.engine.recyclerview.refresh;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseAdapter;
import com.shenjianli.shenlib.base.BaseHolder;

import java.util.List;

import butterknife.Bind;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MyAdapter extends BaseAdapter<String,MyAdapter.ViewHolder> {

    public MyAdapter(Context context) {
        super(context);
    }

    public MyAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent,R.layout.item);
    }

    @Override
    public void bindCustomViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(getItem(position));
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends BaseHolder {

        @Bind(R.id.text)
        public TextView mTextView;

        public ViewHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
        }

        public ViewHolder(View view) {
            super(view);
        }
    }
}
