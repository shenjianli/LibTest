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
