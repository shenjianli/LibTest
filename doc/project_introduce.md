## ShenLib简介
在日常开发中我们难免会遇到以下这几种情况：

1.Android前台代码开发完成了，可是后台还没有封装好可以调试的数据或接口

2.即使第1步中后台封装完成了可测试的数据，但是Android前台开发人员想完成边界测试，如一数据有很多文字时，前台显示是否合理，数据异常时界面如何显示等许许多多情况的测试，这时我们总是想要求后台给配置一些特殊情景的数据，可这时后台开发人员正在开发其他任务，他们根本不想搭理你的，也不想费劲帮你搞那些异常数据，而对这种情况，我们Android前台开发人员是否可完成自己来搞呢？答案是肯定的！

3.在项目开发中我们一般会有一个内网开发环境，一个外网的环境，还有一个正式上线的环境，此外，对于Android开发人员来说最好有一个不依赖于后台的自测环境！面对不同的开发环境，需要配置不同的参数，如ip地址或域名！对于这个问题，我们Android开发人员需要对不同的环境进行不同的参数配置，来切换到不同的开发环境中，如何可以避免不同的环境下，我们需要每次手动修改配置参数以及如何可以快速的切换到不同环境的进行开发呢？

4.对于测试人员及开发人员开说，最好能在同一台手机上安装不同环境版本apk来方便我们验证不同环境下的问题，避免不停的进行客户端的安装替换！经常在我正在开发的得劲时，有人喊到“帮忙打一个什么环境的apk”,而这时你可能恰好不是他所要的环境，这时你可能就需要到程序中一个个的修改程序的配置参数！这是多么麻烦低效的事啊！

争对这几个问题，我用AndroidStudio进行相关的脚本及代码逻辑的编写来提出解决方案，可能我给出的方法并不一定是最好的方法，但其总归是一种解决方法，希望每位读者看完或多或少有点收获。

## 1.本地MockServer模仿后台进行数据封装
由于项目中网络框架采用的是Retrofit + Okhttp3所以增加了本地MockServer的拦截器来模仿后台返回数据，MockServerInterceptor拦截器的代码逻辑如下：

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
有了拦截器后，我们需要把拦截器加入到网络请求的请求链中，使其生效，其具体代码如下：

```
/**
 * 设置MockService
 */
 OkHttpClient.Builder builder = new OkHttpClient.Builder();
 //把MockServer拦截器加入到网络请求
 builder.addInterceptor(new MockServerInterceptor());
```

## 实例

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
在这个请求接口是，主要意思是向地址ServerUrl+shenjianli/test发送一个网络Get请求，传入的参数有id!

### 2.编写本地MockService
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
主要是继承MockService类并实现一个返回Json字符串的方法，其中生成的json字符串需要与第一步中类Test相对应，这样才能正确的向返回的Test对象赋值！
### 3.关联请求与MockService
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
在项目中App模块下的res/xml/url.xml文件中增加如上的结点，其中Key与第一步的请求地址相同，MockClass代表的是Key所对应的MockService对应的类所在的路径。
### 4.进行调用请求使用MockService
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
项目主要是争对不同的版本打包及运行进行不同的配置，项目中主要能这两种方式来实现不同环境的打包及运行环境