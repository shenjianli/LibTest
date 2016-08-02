package com.shenjianli.lib.engine.recyclerview.multi;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by Asia on 2016/7/18.
 */

public class TextHolder extends BaseHolder {

    @Bind(R.id.item_text)
    TextView textView;

    public TextHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        //textView = getView(R.id.item_text);
    }
}
