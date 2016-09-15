package com.shenjianli.lib.app.engine.recyclerview.chat;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shenjianli.lib.R;

import butterknife.Bind;


/**
 * 表情消息的Holder
 * Created by zyz on 2016/5/18.
 */
public class ImageHolder extends ChatHolder {

    @Bind(R.id.content_iv)
    ImageView contentIv;

    public ImageHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        //contentIv = getView(R.id.content_iv);
    }
}
