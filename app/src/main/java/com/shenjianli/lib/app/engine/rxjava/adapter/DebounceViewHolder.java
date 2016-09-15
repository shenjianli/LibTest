package com.shenjianli.lib.app.engine.rxjava.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by shenjianli on 2016/8/1.
 */
public class DebounceViewHolder extends BaseHolder{

    @Bind(R.id.imageIv)
    ImageView imageIv;
    @Bind(R.id.descriptionTv)
    TextView descriptionTv;

    public DebounceViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public DebounceViewHolder(View view) {
        super(view);
    }
}
