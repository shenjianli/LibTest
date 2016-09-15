package com.shenjianli.lib.app.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by shenjianli on 2016/7/27.
 */
public class DemoViewHolder extends BaseHolder{
    // 大图
    @Bind(R.id.imavPic)
    ImageView imavPic;
    // 图片url
    @Bind(R.id.tvUrl)
    TextView tvUrl;

    public DemoViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public DemoViewHolder(View view) {
        super(view);
    }
}
