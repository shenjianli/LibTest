package com.shenjianli.lib.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;

/**
 * Created by edianzu on 2016/7/27.
 */
public class DemoViewHolder extends BaseHolder{
    // 大图
    ImageView imavPic;
    // 图片url
    TextView tvUrl;

    public DemoViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        imavPic = getView(R.id.imavPic);
        tvUrl = getView(R.id.tvUrl);
    }

    public DemoViewHolder(View view) {
        super(view);
        imavPic = getView(R.id.imavPic);
        tvUrl = getView(R.id.tvUrl);
    }
}
