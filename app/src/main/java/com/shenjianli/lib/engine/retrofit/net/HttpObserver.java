package com.shenjianli.lib.engine.retrofit.net;


import com.google.gson.Gson;
import com.shenjianli.lib.engine.retrofit.App;
import com.shenjianli.lib.engine.retrofit.data.entity.ErrorBodyEntity;
import com.shenjianli.shenlib.util.LogUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * 网络请求返回需要的模型
 * Created by ice on 3/3/16.
 */
public abstract class HttpObserver<T> implements Observer<T>, INetResult<T> {

    /**
     * 请求失败, 对错误信息进行处理,
     * 默认显示一个Toast提醒用户.
     */
    @Override
    public void onFail(int errorCode, String msg) {
        App.get().showToast(msg);
    }


    @Override
    public void onCompleted() {
        onComplete();
    }

    @Override
    public void onError(Throwable e) {
        onComplete();
        LogUtils.e(e.toString());
        if (e instanceof SocketTimeoutException) {
            App.get().showToast("连接超时");
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            LogUtils.e(httpException.code() + "");
            LogUtils.e(httpException.message() + "");
            if (httpException.response() != null && httpException.response().errorBody() != null) {
                try {
                    LogUtils.e(httpException.response().message());
                    String bodyStr = httpException.response().errorBody().string();
                    LogUtils.e(bodyStr);
                    ErrorBodyEntity errorBodyEntity = new Gson().fromJson(bodyStr, ErrorBodyEntity.class);
                    onFail(httpException.code(), errorBodyEntity.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onNext(T model) {
        LogUtils.i(new Gson().toJson(model));
        onSuccess(model);
    }

}
