package com.shenjianli.lib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.shenjianli.lib.api.ApiStores;
import com.shenjianli.lib.bean.WeatherJson;

import com.shenjianli.lib.service.BackgroundMonitorService;
import com.shenjianli.shenlib.net.RetrofitCallback;
import com.shenjianli.shenlib.net.NetClient;
import com.shenjianli.shenlib.receiver.NetBroadcastReceiver;
import com.shenjianli.shenlib.util.CustomToast;
import com.shenjianli.shenlib.widget.CylinderImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NetBroadcastReceiver.NetStateChangeListener {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.cylinderImageView)
    CylinderImageView cylinderImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NetBroadcastReceiver.addNetStateListener(this);
        startService(new Intent(this, BackgroundMonitorService.class));
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
        if(connect){
            CustomToast.show(this,"亲，网络恢复啦！");
        }
        else {
            CustomToast.show(this,"亲，网络断开了！");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, BackgroundMonitorService.class));
    }
}
