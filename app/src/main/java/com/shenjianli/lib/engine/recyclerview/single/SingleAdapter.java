package com.shenjianli.lib.engine.recyclerview.single;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.shenjianli.lib.R;
import com.shenjianli.lib.engine.recyclerview.Person;
import com.shenjianli.shenlib.base.BaseAdapter;

/**
 * 一种item的Adapter
 * Created by zyz on 2016/5/17.
 */
public class SingleAdapter extends BaseAdapter<Person, SingleHolder> {

    private OnSingleItemClickListener clickListener;

    public SingleAdapter(Context context) {
        super(context);
    }

    @Override
    public SingleHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new SingleHolder(parent, R.layout.item_single);
    }

    @Override
    public void bindCustomViewHolder(SingleHolder holder, final int position) {
        Person person = getItem(position);
        holder.nameView.setText(person.getName());
        holder.ageView.setText(String.valueOf(person.getAge()));

        if (clickListener != null) {
            holder.nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onNameClick(position);
                }
            });
            holder.ageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onAgeClick(position);
                }
            });
        }
    }

    public SingleAdapter setClickListener(OnSingleItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public interface OnSingleItemClickListener {
        void onNameClick(int position);

        void onAgeClick(int position);
    }
}
