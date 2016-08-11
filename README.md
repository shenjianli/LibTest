# LibTest
the first commit of framework use I am in project


RecyclerView 的使用
RxAndroid 的使用
RxJava 的使用

## 第一部分：网络请求封装
-------------------------------------------------------------------------------
网络请求框架主要采用   Retrofit +  Okhttp
网络请求封闭NetClient类，其代码如下：
``` java
public class NetClient {

	public static Retrofit retrofit = null;
    public static Retrofit retrofit() {
        if (retrofit == null) {
	         OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             *设置缓存
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
```

网络请求主要封装的功能有：
### 缓存：
1.缓存拦截器
``` java
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
```
有网络使用网络请求来请求数据；无网络时，强制使用缓存并设置数据缓存超时时间为4周。


2.增加设置缓存拦截器
``` java
 /**
 *设置缓存目录，缓存大小及拦截器
 */
File cacheFile = new File(LibApp.getLibInstance().getMobileContext().getExternalCacheDir(), "MallCache");
Cache cache = new Cache(cacheFile, Constants.CACHE_SIZE);
builder.cache(cache).addInterceptor(new CacheInterceptor());
```
这步主要作用是向OKHttpClient中加入缓存拦截器来进行数据缓存。

