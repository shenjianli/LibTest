package com.shenjianli.lib.app.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shenjianli on 2016/7/26.
 */
public class RecyclerViewActivity extends BaseActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {

    }
}
