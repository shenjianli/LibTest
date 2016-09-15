package com.shenjianli.lib.mock;

import com.google.gson.Gson;
import com.shenjianli.lib.model.WeatherInfo;
import com.shenjianli.lib.model.WeatherJson;
import com.shenjianli.shenlib.net.mock.MockService;
import com.shenjianli.shenlib.util.LogUtils;

/**
 * Created by shenjianli on 2016/7/8.
 */
public class WeatherMockService extends MockService{
    @Override
    public String getJsonData() {
        WeatherInfo weatherinfo = new WeatherInfo();
        weatherinfo.setCity("taiyuan");
        weatherinfo.setCityid("33333");
        WeatherJson weatherJson = new WeatherJson();
        weatherJson.setWeatherinfo(weatherinfo);
        String result =  new Gson().toJson(weatherJson);
        LogUtils.i("获得的json字符串为：" + result);
        return result;
    }
}
