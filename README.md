# LibTest

主要方便网络请求和日常开发Demo使用

shenLib库的使用方法
```
compile 'com.github.shenjianli.shenlib:shenlib:1.0.3'
```


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




----------------------------------------------------------

## ShenLib简介
在日常开发中我们难免会遇到以下这几种情况：

1.Android前台代码开发完成了，可是后台还没有封装好可以调试的数据或接口

2.即使第1步中后台封装完成了可测试的数据，但是Android前台开发人员想完成边界测试，如一数据有很多文字时，前台显示是否合理，数据异常时界面如何显示等许许多多情况的测试，这时我们总是想去要求后台给配置一些特殊情景的数据，可这时后台开发人员正在开发其他任务，他们根本不想搭理你的，也不想费劲帮你搞那些异常数据，而对于这种情况，我们Android前台开发人员是否可完全自己来搞呢？答案是肯定的！因为我们都相信方法总比问题多！

3.在项目开发中我们一般会有一个内网开发环境，一个外网的环境，还有一个正式上线的环境，此外，对于Android开发人员来说最好有一个不依赖于后台的自测环境！面对不同的开发环境，需要配置不同的参数，如ip地址或域名！对于这个问题，我们Android开发人员需要对不同的环境进行不同的参数配置，来切换到不同的开发环境中，如何可以避免不同的环境下，我们不需要每次手动修改配置参数以及如何可以快速的切换到不同环境的进行开发呢？

4.对于测试人员或开发人员开说，最好能在同一台手机上安装不同环境版本apk来方便我们验证不同环境下的问题，这样可以避免不停的进行客户端的安装替换，节约宝贵的开发时间提高开发效率！经常在我正在开发的得劲时，有人喊到“帮忙打一个什么环境的apk”,而这时你可能恰好不是他所要的环境，这时你可能就需要到程序中一个个的修改程序的配置参数！这是多么麻烦低效的事啊！

争对这几个问题，我用AndroidStudio进行相关的脚本及代码逻辑的编写来提出解决方案，可能我给出的方法并不一定是最好的，但其总归是一种解决方法，希望每位读者看完或多或少有点收获。

## 1.本地MockServer模仿后台进行数据封装
对于上面所说的第1，2个问题，我们可以通过设置本地服务器来进行解决，这样Android开发在进行界面边界测试及显示边界效果时，可以方便的修改本地服务数据来进行实验了，而不必要完全依赖于后台数据！

设置本地测试服务优点：
1. 根据边界测试内容随时修改数据，不需要依赖于后台数据
2. 提高测试质量，便于开发人员进行自测
3. 不需要依赖于服务器，就可正常请求显示数据
4. 减少Android开发人员的bug数
业内一般称本地的服务器为MockServer,故下面简述一下MockServer，在本项目中的设置及简单的使用方法：

由于项目中网络框架采用的是Retrofit + Okhttp3所以为了使用本地MockServer，因此需要使用拦截器来模仿后台返回数据。

MockServer的拦截器代码逻辑如下：

```
package com.shenjianli.shenlib.net.interceptor;

import com.shenjianli.shenlib.net.mock.MockService;
import com.shenjianli.shenlib.net.mock.URLData;
import com.shenjianli.shenlib.net.mock.UrlConfigManager;
import com.shenjianli.shenlib.util.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/** * Created by shenjianli on 2016/7/8.
 */
public class MockServerInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        //拦截到网络请求时，根据UrlConfigManager中的变量MockServiceEnable的值来决定是否使用本地数据封装
        //一般用在程序员自测或是边界性适配性测试时使用
        if(UrlConfigManager.MockServiceEnable) {  //如果MockServiceEnable为true,走下面流程
            //取出请求的url地址
            String key = chain.request().url().uri().getPath();
            //根据配置文件来获取对应的数据
            URLData urlData = UrlConfigManager.getUrlConfigManager().findURL(key);

            if(null != urlData){
               if(null != urlData.getMockClass()){
                   try {
                       //利用反射机制来创建本地的MockService类
                        MockService mockService = (MockService) Class.forName(
                                urlData.getMockClass()).newInstance();
                       //根据MockService对象来获取返回给前台的json字符串
                        String responseString = mockService.getJsonData();
                       //创建请求的返回内容
                        response = new Response.Builder()
                               .code(200)
                               .message(responseString)
                               .request(chain.request())
                               .protocol(Protocol.HTTP_1_0)
                               .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                               .addHeader("content-type", "application/json")
                               .build();
                        LogUtils.d("调用mock service " + urlData.getMockClass());
                       //响应请求
                        return response;
                   } catch (InstantiationException e) {
                        e.printStackTrace();
                   } catch (IllegalAccessException e) {
                        e.printStackTrace();
                   } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                   }
               }
            }
        }
        LogUtils.i("正常调用网络方法");
        //如果MockServiceEnable为fase,继续根据地址发送远程网络请求
        return chain.proceed(chain.request());
    }
}

```
关于MockServier的拦截器中具体的实现逻辑，请读者阅读上面代码或源码，其中有详细的注释，我就不在此赘述了！

有了拦截器后，我们需要把拦截器加入到网络请求的请求链中，使其生效
设置本地MockServer的具体代码如下：

```
/**
 * 设置MockService
 */
 OkHttpClient.Builder builder = new OkHttpClient.Builder();
 //把MockServer拦截器加入到网络请求
 builder.addInterceptor(new MockServerInterceptor());
```

## 使用实例
当有了上面的MockServer,那如何使用呢？下面将以一个具体的实现来说明一下，具体的使用步骤：

### 1.定义请求的接口
```
package com.shenjianli.lib.test;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by shenjianlis on 2016/8/31.
 * 测试网络请求的接口
 */
public interface TestApi {

    //请求的url地址
    @GET("shenjianli/test")
    //id为请求的参数，可以根据参数不同返回不同的返回结果
    Call<Test> getTestData(@Query("id") String id);

}

```
在这里的请求接口，主要是根据传入的参数id来向服务器地址（https://github.com.cn/shenjianli/test）发送一个Get网络请求，来获取数据!

### 2.编写本地MockService
写好请求数据的接口后，下一步是构造上面接口所需要的返回测试数据，如下，是编写及返回测试数据的具体流程，有兴趣的读者可以阅读下面代码：
```
package com.shenjianli.lib.test;

import com.google.gson.Gson;
import com.shenjianli.shenlib.net.mock.MockService;
import com.shenjianli.shenlib.util.LogUtils;


/**
 * Created by shenjianli on 2016/7/8.
 * 测试使用的本地MockService用来返回请求的json字符串
 */
public class TestMockService extends MockService {
    @Override
    public String getJsonData() {

        //创建测试数据对象
        TestData testData = new TestData();
        //进行想着的赋值
        testData.setCity("taiyuan");
        testData.setCityid("33333");

        //测试数据传测试对象中
        Test test = new Test();
        test.setTestData(testData);

        //使用Gson把对象转化为Json字符串
        String resultStr =  new Gson().toJson(test);
        LogUtils.i("获得的json字符串为：" + resultStr);

        //返回json字符串
        return resultStr;
    }
}

```
TestMockService主要是继承MockService类并实现一个返回Json字符串的方法，其中生成的json字符串需要与第一步中类Test相对应，这样才能正确的向返回的Test对象赋值！

### 3.关联请求与MockService
有了请求的接口，有了返回测试数据的json字符串，下面就是应该把它们进行关联了，其关联的配置文件如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<url>
    <Node
        <!-- 表示请求地址 -->
        Key="/shenjianli/test"

        <!-- 若需要缓存，可以设置缓存时间 -->
        Expires="300"

        <!-- 请求地址对应的MockService类地址  -->
        MockClass="com.shenjianli.lib.test.TestMockService"/>
</url>
```
在项目中App模块下的res/xml/url.xml文件中增加如上的结点，其中Key与第一步的请求地址相同，MockClass代表的是Key所对应的MockService类所在的路径。

### 4.进行调用请求使用MockService
当请求json数据的接口和返回的json相关联以后，下面就是最开心的时间了，开如使用MockService:

```
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
```
在上面的TestActivity中当点击按钮时，会通过网络框架NetClient来进行网络请求，当拦截器拦到请求的url(/shenjianli/test)后，他会去配置文件（res/xml/url.xml）去找看是否有对应的结点，若有则根据Node结点中的MockClass来通过反射机制来创建具体的MockServer对象，来返回用于测试的json字符串！当网络框架接收到json字符串后会怎么通过GsonFactory来转化为具体的测试数据对象，返回到Android的View层来进行具体的显示！这样就达到的模拟服务器返回json数据的服务了！这样以后Android开发人员再也不用担心没有符合心意的测试数据了，再也不用为服务器启动不了或出现异常而烦恼了！

通过上面MockServer的使用，基本上可以解决上面的问题1和问题2了，以后再也不用担心没有网络，没有后台服务器了！Android开发人员终于可以开心的开发了！




在项目中，不同版本的打包及运行需要配置不同的参数，那么我们如何对不同版本进行不同的参数的配置呢？在本项目中主要通过下面两种方式来实现不同版本下的参数配置：

## 1.通过配置文件
这一种方式是通过配置文件来配置不同的开发环境，其不依赖于Gradle脚本，即使在Eclipse下也可使用，首先，在app模块的res/raw目录下创建配置文件mode.properties，复制下面内容到此配置文件中。

```
#进行自测时使用MockService返回数据
#mode=test

#开发模式
mode=dev
#设置网络请求的域名地址
baseUrl=http://m.dev.shen.com.cn/

#正式发版模式
#mode=release
#设置网络请求的域名地址
#baseUrl=http://m.mall.shen.com.cn/
```

mode表示不同的开发模式，其在具体的运行时，可通过配置不同的值来切换不同的开发模式，当配置为test时可以使用本地的MockService来返回数据，而不必要依赖于具体的服务器。
baseUrl表示网络请求的域名地址，可为不同的开发模式配置不同的地址，当然也可以配置成具体的ip地址+端口号，如http://127.0.0.1:8080/


```
/*
 根据Raw中的mobile配置文件来初始化开发模式
 */
private void initByRawConfigFile() {
     if(FileUtils.getProperties(this, R.raw.mode)){
      String mode = FileUtils.getPropertyValueByKey("mode");
      String baseUrl = FileUtils.getPropertyValueByKey("baseUrl");
      LogUtils.i("开发模式为：" + mode);
      if(Constants.TEST_MODE.equals(mode)){
            LibApp.getLibInstance().setLogEnable(true);
            LibApp.getLibInstance().setUrlConfigManager(R.xml.url);
       }
       else if(Constants.DEV_MODE.equals(BuildConfig.MODE)){
            LibApp.getLibInstance().setLogEnable(true);
            LibApp.getLibInstance().setServerBaseUrl(baseUrl);
       }
       else if(Constants.RELEASE_MODE.equals(BuildConfig.MODE)){
             LibApp.getLibInstance().setLogEnable(false);
             LibApp.getLibInstance().setServerBaseUrl(baseUrl);
       }
   }
}

```
在Application中根据上面的配置文件参数来设置不同环境的其他的一些设置选项，若为Test模式，则开启log日志记录，使用本地MockService进行数据返回；若为Dev模式，则开启日志，设置请求的基础地址；若为Release正式版本，则需要关闭日志输出，同时设置为正式上线时使用的基础地址（一般情况下为域名地址）

## 2.通过Gradle脚本程序
根据Android Studio 中的脚本程序Gradle来进行不同环环境下，设置不同的开发模式参数：
在app模块下build.gradle中编写下面代码：

```
//获取时间戳
def getDate() {
    def date = new Date()
    def formattedDate = date.format('yyyy_MMdd_HHmm')
    return formattedDate
}

def releaseTime() {
    return new Date().format("yyyy_MM_dd", TimeZone.getTimeZone("UTC"))
}

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.3"

    signingConfigs {
        config{
            storeFile file('../shen_release_key')
            storePassword 'Password123!'
            keyAlias 'shen_release_key'
            keyPassword 'Password123!'
        }
    }


    defaultConfig {
        applicationId "com.shenjianli.lib"
        minSdkVersion 15
        targetSdkVersion 23
        versionCode 1
        versionName "1.0"

        multiDexEnabled true
    }
    buildTypes {
        release {
            //proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.config
            minifyEnabled false
            zipAlignEnabled true
        }
        debug {
            //proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            //signingConfig signingConfigs.config
            minifyEnabled false
            zipAlignEnabled false
        }
    }

//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//    }

    productFlavors {
        Dev{
            applicationId "com.shenjianli.lib"
            manifestPlaceholders = [app_name:"开发版" ,icon: "@mipmap/ic_launcher",CHANNEL_VALUE:"Dev"]
            //在java代码中具体的使用方式为：context.getResources().getString(R.string.strKey);
            resValue("string" , "strKey","dev版本")
            resValue ('string','isDebug','true')
            buildConfigField "boolean", "AUTO_UPDATES", "false"
            buildConfigField("String","MODE","\"dev\"")
            buildConfigField("String","SERVER_URL","\"http://m.dev.shen.com.cn/\"")
        }
        DevTest{
            applicationId "com.shenjianli.lib.test"
            manifestPlaceholders = [app_name:"自测版" ,icon: "@mipmap/ic_launcher",CHANNEL_VALUE:"Test"]
            //在java代码中具体的使用方式为：context.getResources().getString(R.string.strKey);
            resValue("string" , "strKey","beta版本")
            resValue ('string','isDebug','true')
            buildConfigField "boolean", "AUTO_UPDATES", "false"
            buildConfigField("String","MODE","\"test\"")
            //测试版本主要使用本地MockService来返回数据，可以不设置
            buildConfigField("String","SERVER_URL","\"test\"")

        }
        Releases{
            applicationId "com.shenjianli.lib.release"
            manifestPlaceholders = [app_name:"正式版" ,icon: "@mipmap/ic_launcher_releases",CHANNEL_VALUE:"Releases"]
            resValue("string" , "strKey","release版本")
            resValue ('string','isDebug','false')
            buildConfigField "boolean", "AUTO_UPDATES", "false"
            buildConfigField("String","MODE","\"release\"")
            buildConfigField("String","SERVER_URL","\"http://m.mall.shen.com.cn/\"")
        }
    }

    //添加如下代码
//    productFlavors.all { flavors->
//        flavors.manifestPlaceholders=[CHANNEL_VALUE:name]
//    }
    //修改生成的apk名字
    applicationVariants.all{
        variant->
        variant.outputs.each {
            output->
            def oldFile = output.outputFile
            def newName = '';
            if(variant.buildType.name.equals('release')){
//                println(variant.productFlavors[0].name)
                def releaseApkName = 'product_' + defaultConfig.versionName +
                        '_' + variant.productFlavors[0].name + getDate() +'.apk'
                output.outputFile = new File(oldFile.parent, releaseApkName)
            }
            if(variant.buildType.name.equals('Beta')){
                newName = oldFile.name.replace(".apk", "-v" + defaultConfig.versionName
                        + "-build" + getDate() + ".apk")
                output.outputFile = new File(oldFile.parent, newName)
            }
            if(variant.buildType.name.equals('Dev')){
                output.outputFile = new File(output.outputFile.parent, "ShenLibTest_"
                        + defaultConfig.versionCode + "_v" + defaultConfig.versionName +
                        "_"+buildType.name+"_"+ getDate() +".apk");
            }
        }
    }

    //移除lint检测的error
    lintOptions {
        abortOnError false
    }

}

```
在上面的配置文件中的  productFlavors 选项下，Dev为开发环境，DevTest为自测环境，主要是使用上面所说的MockService来调试界面显示，Releases为正式版，主要是上线或是做正式版本。

在productFlavors下不同的开发环境可为应用配置不同的包名，图标，应用名称，这样包名不同，在同一个手机上就可以同时安装不同环境下的app，这样可以方便我们进行测试及使用！这恰好解决了前面所说的第4个问题！

```
 /*
    根据主项目中的gradle配置文件开初始化不同的开发模式
 */
 private void initByGradleFile() {

    if(Constants.TEST_MODE.equals(BuildConfig.MODE)){
         LibApp.getLibInstance().setLogEnable(true);
         LibApp.getLibInstance().setUrlConfigManager(R.xml.url);
     }
     else if(Constants.DEV_MODE.equals(BuildConfig.MODE))
     {
         LibApp.getLibInstance().setLogEnable(true);
         LibApp.getLibInstance().setServerBaseUrl(BuildConfig.SERVER_URL);
     }
     else if(Constants.RELEASE_MODE.equals(BuildConfig.MODE)){
         LibApp.getLibInstance().setLogEnable(true);
         LibApp.getLibInstance().setServerBaseUrl(BuildConfig.SERVER_URL);
     }
   }
```

在Application中根据上面的Gradle参数来生成的BuildConfig中的值来设置不同环境的其他的设置选项，若为Test模式，则开启log日志记录，使用本地MockService进行数据返回；若为Dev模式，则开启日志，设置请求的基础地址；若为Release正式版本，则需要关闭日志输出，同时设置为正式上线时使用的基础地址（一般情况下为域名地址）



## 自动打包

gradlew 是什么东西呢，和gradle貌似不大一样，肯定有关系。没错，他就是 gradle wrapper，意思是gradle的一个包装，大家可以理解为在这个项目本地就封装了gradle，比如我的项目是HelloWord, 在HelloWord/gradle/wrapper/gralde-wrapper.properties文件中声明了它指向的目录和版本，比如我的内容是：

```
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-2.10-all.zip

```

如果你项目没有该文件的话，那么gradlew会到~/.gradle/wrapper/dists/gradle-2.10-all下寻找，或者你新建给文件，内容复制到里面。其实你会发现是同一个目录咯。里面会有个随机数的文件夹，里面就是gradle，只要下载成功即可用grdlew wrapper的命令代替全局的gradle命令。

常用命令如下：(linux下是./gradlew，该脚本在项目下，windows直接gradlew即可)

./gradlew -v 版本号，首次运行，没有gradle的要下载的哦。
/gradlew clean 删除HelloWord/app目录下的build文件夹

./gradlew build 检查依赖并编译打包

这里注意的是 ./gradlew build 命令把debug、release环境的包都打出来，生成的包在目录HelloWord/app/build/outputs/apk/下。如果正式发布只需要打release的包，该怎么办呢，下面介绍一个很有用的命令 assemble, 如

./gradlew assembleDebug 编译并打Debug包

./gradlew assemblexiaomiDebug 编译并打xiaomi的debug包，其他类似

./gradlew assembleRelease 编译并打Release的包

./gradlew assemblexiaomiRelease 编译并打xiaomi的Release包，其他类似

./gradlew installRelease Release模式打包并安装

./gradlew uninstallRelease 卸载Release模式包

补充

1 gradlew build 和 gradle build 有区别吗?

使用gradle wrapper是gradle官方推荐的build方式，而gradlew正是运行了wrapper task之后生成的（运行wrapper task是Android Studio自动做的）。使用gralde wrapper的一个好处就是每个项目可以依赖不同版本的gradle，构建的时候gradle wrapper会帮你自动下载所依赖的版本的gradle。而如果你使用gradle build的话，同时你又有多个项目使用不同版本的gradle，那就需要你手动在自己的机器上配置多个版本的gradle，稍微麻烦一些

2 自定义apk包名

gradle脚本大法好：

def releaseTime() {
    return new Date().format("yyyy-MM-dd", TimeZone.getTimeZone("UTC"))
}

def gitVersionCode() {
    def cmd = 'git rev-list HEAD --first-parent --count'
    cmd.execute().text.trim().toInteger()
}

def gitVersionTag() {
    def cmd = 'git describe --tags'
    def version = cmd.execute().text.trim()

    def pattern = "-(\\d+)-g"
    def matcher = version =~ pattern

    if (matcher) {
        version = version.substring(0, matcher.start()) + "." + matcher[0][1]
    } else {
        version = version + ".0"
    }

    return version
}



//自定义apk安装包名
applicationVariants.all { variant ->
    variant.mergedFlavor.versionCode = gitVersionCode()
    variant.mergedFlavor.versionName = gitVersionTag()
    variant.outputs.each { output ->
        output.outputFile = new File(
                output.outputFile.parent + "/${variant.buildType.name}",
                "HelloWord-${variant.buildType.name}-v${variant.versionName}-${variant.productFlavors[0].name}-${releaseTime()}.apk".toLowerCase())
    }
}



缓存使用

1.在请求接口中增加

    @Headers("Cache-Control: public, max-age=600")
    @GET("adat/sk/{cityId}.html")
    Call<WeatherJson> getWeather(@Path("cityId") String cityId);
2.max-age=600表示是缓存时间为10分钟

