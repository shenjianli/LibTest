package com.shenjianli.lib.engine.home;

import com.shenjianli.lib.engine.home.bean.MallBean;
import com.shenjianli.lib.engine.rxandroid.ApiException;

import rx.functions.Func1;

/**
 * Created by shenjianli on 2016/8/15.
 */
public class HttpResultFunc<T> implements Func1<MallBean<T>,T>{

    @Override
    public T call(MallBean<T> httpResult) {
        if (httpResult.getStatus() != 0) {
            throw new ApiException(httpResult.getMessage());
        }
        return httpResult.getData();
    }

}
