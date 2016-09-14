package com.shenjianli.lib.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.net.NetClient;
import com.shenjianli.shenlib.net.RetrofitCallback;

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
        TestApi api = NetClient.retrofit().create(TestApi.class);
        Call<Test> data = api.getTestData("001");
        data.enqueue(new RetrofitCallback<Test>() {
            @Override
            public void onSuccess(Test data) {
                if(null != data){
                    mCityTv.setText(data.getTestData().getCity());
                }
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
    }
}
