package com.shenjianli.lib.test;

import com.google.gson.Gson;
import com.shenjianli.shenlib.net.mock.MockService;
import com.shenjianli.shenlib.util.LogUtils;


/**
 * Created by shenjianli on 2016/7/8.
 */
public class TestMockService extends MockService {
    @Override
    public String getJsonData() {

        TestData testData = new TestData();

        testData.setCity("taiyuan");
        testData.setCityid("33333");

        //no use
        Test test = new Test();
        test.setTestData(testData);

        String resultStr =  new Gson().toJson(test);
        LogUtils.i("获得的json字符串为：" + resultStr);
        return resultStr;
    }
}
