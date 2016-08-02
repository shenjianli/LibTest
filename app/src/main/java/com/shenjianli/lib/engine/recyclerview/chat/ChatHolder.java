package com.shenjianli.lib.engine.recyclerview.chat;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseHolder;

import butterknife.Bind;

/**
 * 聊天界面的ViewHolder
 * Created by zyz on 2016/5/18.
 */
public class ChatHolder extends BaseHolder {

    @Bind(R.id.name_tv)
    TextView senderNameTv;
    @Bind(R.id.create_time_tv)
    TextView createTimeTv;

    public ChatHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);

//        senderNameTv = getView(R.id.name_tv);
//        createTimeTv = getView(R.id.create_time_tv);
    }
}
