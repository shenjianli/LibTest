package com.shenjianli.lib.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.lib.adapter.RecylerViewAdapter;
import com.shenjianli.lib.api.ApiStores;
import com.shenjianli.lib.bean.WeatherJson;
import com.shenjianli.lib.data.DemoData;
import com.shenjianli.lib.service.BackgroundMonitorService;
import com.shenjianli.shenlib.base.BaseActivity;
import com.shenjianli.shenlib.base.DividerDecoration;
import com.shenjianli.shenlib.net.NetClient;
import com.shenjianli.shenlib.net.RetrofitCallback;
import com.shenjianli.shenlib.receiver.NetBroadcastReceiver;
import com.shenjianli.shenlib.util.CustomToast;
import com.shenjianli.shenlib.widget.CylinderImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NetBroadcastReceiver.NetStateChangeListener {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.cylinderImageView)
    CylinderImageView cylinderImageView;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    RecylerViewAdapter adapter;
    List<DemoData> mDemoDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NetBroadcastReceiver.addNetStateListener(this);
        startService(new Intent(this, BackgroundMonitorService.class));

        initData();
        adapter = new RecylerViewAdapter(this,mDemoDatas);
        adapter.setOnDemoClickListener(new RecylerViewAdapter.OnDemoClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(MainActivity.this,RecyclerViewMainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this,RxAndroidActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this,RxJavaActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        initRecylerView();
    }

    private void initData() {
        mDemoDatas = new ArrayList<>();

        DemoData demodata = new DemoData();
        demodata.setImgId(R.drawable.ic_launcher);
        demodata.setName("RecyclerView Demo");
        mDemoDatas.add(demodata);

        demodata = new DemoData();
        demodata.setImgId(R.drawable.ic_launcher);
        demodata.setName("RxAndroid");
        mDemoDatas.add(demodata);

        demodata = new DemoData();
        demodata.setImgId(R.drawable.ic_launcher);
        demodata.setName("RxJava");
        mDemoDatas.add(demodata);

        for (int i = 0; i < 5; i++) {
            demodata = new DemoData();
            demodata.setImgId(R.drawable.ic_launcher);
            demodata.setName("demo" + i);
            mDemoDatas.add(demodata);
        }
    }




    /*
    * // 网格布局管理器
GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
// 设置布局管理器
recyclerView.setLayoutManager(gridLayoutManager);
 改为

// 交错网格布局管理器
StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
// 设置布局管理器
recyclerView.setLayoutManager(staggeredGridLayoutManager);
item布局

 改为

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="5dp"
    android:orientation="vertical">


    <ImageView
        android:id="@+id/imavPic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:scaleType="centerCrop" />

    <TextView
        android:id="@+id/tvUrl"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
    * */

    private void initRecylerView() {
        // 线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // 设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerDecoration decoration = new DividerDecoration(this, DividerDecoration.VERTICAL_LIST);
        Drawable drawable = getResources().getDrawable(R.drawable.divider_single);
        decoration.setDivider(drawable);

//        decoration.getItemOffsets();
        recyclerView.addItemDecoration(decoration);
        //recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        // recyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);

        // 模拟下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }


    @OnClick(R.id.button)
    public void onClick() {
        getWeather();
    }

    private void getWeather() {
        ApiStores apiStores = NetClient.retrofit().create(ApiStores.class);
        Call<WeatherJson> call = apiStores.getWeather("101010100");
        call.enqueue(new Callback<WeatherJson>() {

            @Override
            public void onFailure(Call<WeatherJson> arg0, Throwable arg1) {
                Log.i(this.getClass().getSimpleName(), arg1.getStackTrace() + "");
            }

            @Override
            public void onResponse(Call<WeatherJson> arg0,
                                   Response<WeatherJson> arg1) {
                Log.i("wxl", "getWeatherinfo=" + arg1.body().getWeatherinfo().getCity());
                text.setText(arg1.body().getWeatherinfo().getCity());
            }

        });
    }

    private void getNewWeather() {
        ApiStores apiStores = NetClient.retrofit().create(ApiStores.class);
        Call<WeatherJson> call = apiStores.getWeather("101010100");
        call.enqueue(new RetrofitCallback<WeatherJson>() {

            @Override
            public void onSuccess(WeatherJson t) {
                if (null != t) {
                    Log.i(this.getClass().getSimpleName(), t.getWeatherinfo().getCity());
                }
            }

            @Override
            public void onFail(String errorMsg) {
                Log.e(this.getClass().getSimpleName(), errorMsg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cylinderImageView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cylinderImageView.pause();
    }

    @Override
    public void onNetChange(boolean connect) {
        if (connect) {
            CustomToast.show(this, "亲，网络恢复啦！");
        } else {
            CustomToast.show(this, "亲，网络断开了！");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, BackgroundMonitorService.class));
    }
}
