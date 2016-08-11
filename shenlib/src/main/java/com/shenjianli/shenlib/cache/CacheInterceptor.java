package com.shenjianli.shenlib.cache;

import com.shenjianli.shenlib.LibApp;
import com.shenjianli.shenlib.util.AppUtils;
import com.shenjianli.shenlib.util.LogUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor{

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
        //没有网络时，拦截请求并设置强制使用缓存
        if (!AppUtils.networkIsAvailable(LibApp.getLibInstance().getMobileContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            LogUtils.i(this.getClass().getSimpleName(), "无网络强制使用缓存机制");
        }
        //获取到网络响应
        Response response = chain.proceed(request);
        //在有网络情况下直接使用网络请求回来的数据
        if (AppUtils.networkIsAvailable(LibApp.getLibInstance().getMobileContext())) {
            int maxAge = 0;
            // 有网络时 设置缓存超时时间0个小时
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
            LogUtils.i(this.getClass().getSimpleName(), "有网络使用网络请求");
        } else {//无网络时，设置本地缓存及超时时间
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("")
                    .build();
            LogUtils.i(this.getClass().getSimpleName(), "无网络设置响应网络请求的缓存时间");
        }
        return response;
	}

}
