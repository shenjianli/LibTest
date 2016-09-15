package com.shenjianli.lib.test;

import com.shenjianli.lib.MobileApp;
import com.shenjianli.shenlib.net.mock.MockService;
import com.shenjianli.shenlib.util.FileUtils;
import com.shenjianli.shenlib.util.LogUtils;


/**
 * Created by shenjianli on 2016/7/8.
 * 测试使用的本地MockService用来返回请求的json字符串
 */
public class TestFileMockService extends MockService {
    @Override
    public String getJsonData() {

        //使用Gson把对象转化为Json字符串
        String resultStr = FileUtils.read(MobileApp.getAppInstance(), "");
        LogUtils.i("获得的json字符串为：" + resultStr);

        //返回json字符串
        return resultStr;
    }
}
