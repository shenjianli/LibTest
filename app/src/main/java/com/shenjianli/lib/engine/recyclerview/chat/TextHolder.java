package com.shenjianli.lib.engine.recyclerview.chat;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenjianli.lib.R;

import butterknife.Bind;

/**
 * 文本消息的Holder
 * Created by zyz on 2016/5/18.
 */
public class TextHolder extends ChatHolder {

    @Bind(R.id.content_tv)
    TextView contentTv;

    public TextHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);

        //contentTv = getView(R.id.content_tv);
    }
}
