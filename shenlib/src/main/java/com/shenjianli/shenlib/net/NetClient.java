package com.shenjianli.shenlib.net;

import com.shenjianli.shenlib.Constants;
import com.shenjianli.shenlib.LibApp;
import com.shenjianli.shenlib.net.cookie.PersistentCookieStore;
import com.shenjianli.shenlib.net.cookie.WebkitCookieManagerProxy;
import com.shenjianli.shenlib.cache.CacheInterceptor;
import com.shenjianli.shenlib.net.interceptor.HeaderInterceptor;
import com.shenjianli.shenlib.net.interceptor.MockServerInterceptor;
import com.shenjianli.shenlib.net.interceptor.QueryParameterInterceptor;
import com.shenjianli.shenlib.util.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetClient {

	public static Retrofit retrofit = null;
    public static Retrofit retrofit() {
        if (retrofit == null) {
	         OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             *设置缓存，代码略
             */
	         File cacheFile = new File(LibApp.getLibInstance().getMobileContext().getExternalCacheDir(), "MallCache");
	         Cache cache = new Cache(cacheFile, Constants.CACHE_SIZE);
             builder.cache(cache).addInterceptor(new CacheInterceptor());
            /**
             *  公共参数，代码略
             */
             
            //公共参数
            builder.addInterceptor(new QueryParameterInterceptor());
            /**
             * 设置头，代码略
             */           
            //设置头
            builder.addInterceptor(new HeaderInterceptor());
			 /**
             * Log信息拦截器，代码略
             */
             if (LogUtils.isOutPutLog) {
            	    // Log信息拦截器
            	    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            	    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            	    //设置 Debug Log 模式
            	    builder.addInterceptor(loggingInterceptor);
            	}
			 /**
             * 设置cookie，代码略
             */
             //CookieManager cookieManager = new CookieManager();
             //cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
             //builder.cookieJar(new JavaNetCookieJar(cookieManager));
             
             PersistentCookieStore persistentCookieStore = new PersistentCookieStore(LibApp.getLibInstance().getMobileContext());
             WebkitCookieManagerProxy coreCookieManager = new WebkitCookieManagerProxy(persistentCookieStore, java.net.CookiePolicy.ACCEPT_ALL);
             //java.net.CookieHandler.setDefault(coreCookieManager);
             builder.cookieJar(new JavaNetCookieJar(coreCookieManager));

            /**
             * 设置MockService
             */
            builder.addInterceptor(new MockServerInterceptor());

            /**
            * 设置超时和重连，代码略
            */
           //设置超时
             builder.connectTimeout(15, TimeUnit.SECONDS);
             builder.readTimeout(20, TimeUnit.SECONDS);
             builder.writeTimeout(20, TimeUnit.SECONDS);
             //错误重连
             builder.retryOnConnectionFailure(true);

            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = builder.build();
            
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVER_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
