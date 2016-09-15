package com.shenjianli.lib.app.engine.recyclerview.staggered;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by shenjianli on 2016/8/3.
 */
public class StaggeredHolder extends BaseHolder {

    @Bind(R.id.staggered_item_img)
    ImageView staggeredItemImg;
    @Bind(R.id.staggered_item_title)
    TextView staggeredItemTitle;

    public StaggeredHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent,resId);

    }

    public StaggeredHolder(View view) {
        super(view);
    }



}
