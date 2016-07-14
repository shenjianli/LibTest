package com.shenjianli.lib.mock;

import com.alibaba.fastjson.JSON;
import com.shenjianli.lib.bean.WeatherInfo;
import com.shenjianli.lib.bean.WeatherJson;
import com.shenjianli.shenlib.net.mock.MockService;
/**
 * Created by edianzu on 2016/7/8.
 */
public class WeatherMockService extends MockService{
    @Override
    public String getJsonData() {
        WeatherInfo weatherinfo = new WeatherInfo();
        weatherinfo.setCity("taiyuan");
        weatherinfo.setCityid("33333");
        WeatherJson weatherJson = new WeatherJson();
        weatherJson.setWeatherinfo(weatherinfo);
        return JSON.toJSONString(weatherJson);
    }
}
