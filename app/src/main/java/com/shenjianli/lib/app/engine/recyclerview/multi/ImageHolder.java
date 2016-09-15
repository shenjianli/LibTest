package com.shenjianli.lib.app.engine.recyclerview.multi;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by Asia on 2016/7/18.
 */

public class ImageHolder extends BaseHolder {

    @Bind(R.id.item_image)
    ImageView imageView;

    public ImageHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
       // imageView = getView(R.id.item_image);
    }
}
