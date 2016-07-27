package com.shenjianli.lib.engine.recyclerview.single;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;


/**
 * 一种View的Holder
 * Created by zyz on 2016/5/17.
 */
public class SingleHolder extends BaseHolder {

    TextView nameView;
    TextView ageView;

    public SingleHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        nameView = getView(R.id.name_tv);
        ageView = getView(R.id.age_tv);
    }

    public SingleHolder(View view) {
        super(view);
        nameView = getView(R.id.name_tv);
        ageView = getView(R.id.age_tv);
    }
}
