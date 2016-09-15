package com.shenjianli.lib.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.net.NetClient;
import com.shenjianli.shenlib.net.RetrofitCallback;
import com.shenjianli.shenlib.util.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class TestActivity extends AppCompatActivity {

    @Bind(R.id.city_tv)
    TextView mCityTv;
    @Bind(R.id.get_btn)
    Button mGetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.get_btn)
    public void onClick() {
        //创建网络请求结口
        TestApi api = NetClient.retrofit().create(TestApi.class);
        //根据网络请求结口获取数据
        Call<Test> data = api.getTestData("001");
        //启动网络请求去请求数据
        data.enqueue(new RetrofitCallback<Test>() {
            @Override
            public void onSuccess(Test data) {
                //成功时返回测试数据
                if(null != data){
                    mCityTv.setText(data.getTestData().getCity());
                }
            }

            @Override
            public void onFail(String errorMsg) {
                //失败时返回错误信息
                LogUtils.i(" 错误信息 " + errorMsg);
            }
        });
    }
}
