package com.shenjianli.lib.engine.recyclerview.staggered;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shenjianli.lib.R;
import com.shenjianli.lib.engine.rxjava.GankBeautyResultToItemsMapper;
import com.shenjianli.lib.engine.rxjava.model.Item;
import com.shenjianli.lib.engine.rxjava.network.Network;
import com.shenjianli.shenlib.base.BaseActivity;
import com.shenjianli.shenlib.base.SpacesItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shenjianli on 2016/8/3.
 */
public class StaggeredActivity extends BaseActivity {

    @Bind(R.id.staggered_rv)
    RecyclerView staggeredRv;


    StaggeredAdapter adapter;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.staggered_title)
    TextView staggeredTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_layout);
        ButterKnife.bind(this);

        //设置layoutManager
        staggeredRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        adapter = new StaggeredAdapter(this);
        staggeredRv.setAdapter(adapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        staggeredRv.addItemDecoration(decoration);

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        requsetData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void requsetData() {
        swipeRefreshLayout.setRefreshing(true);
        Network.getGankApi()
                .getBeauties(10, 3)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Item>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(StaggeredActivity.this, R.string.loading_failed, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.fillList(items);
                    }
                });
    }

    @OnClick(R.id.staggered_title)
    public void onClick() {
        requsetData();
    }
}
