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
