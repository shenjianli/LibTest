package com.shenjianli.lib.bean;

import java.io.Serializable;

/**
 * 实现Serializable接口，以支持序列化，这样这个对象可以使用Intent来进行传递
 */
public class WeatherJson implements Serializable{

	 //weatherinfo需要对应json数据的名称，我之前随便写了个，被坑很久
    private WeatherInfo weatherinfo;

    public WeatherInfo getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherInfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
