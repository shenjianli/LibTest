package com.shenjianli.shenlib.net.interceptor;

import com.shenjianli.shenlib.BuildConfig;
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
        if(UrlConfigManager.MockServiceEnable) {
            String key = chain.request().url().uri().getPath();
            URLData urlData = UrlConfigManager.getUrlConfigManager().findURL(key);
            if(null != urlData){
               if(null != urlData.getMockClass()){
                   try {
                        MockService mockService = (MockService) Class.forName(
                                urlData.getMockClass()).newInstance();
                        String responseString = mockService.getJsonData();
                        response = new Response.Builder()
                               .code(200)
                               .message(responseString)
                               .request(chain.request())
                               .protocol(Protocol.HTTP_1_0)
                               .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                               .addHeader("content-type", "application/json")
                               .build();
                        LogUtils.d("调用mock service " + urlData.getMockClass());
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
        return chain.proceed(chain.request());
    }
}
