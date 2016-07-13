package com.shenjianli.lib.bean;

public class WeatherJson {

	 //weatherinfo需要对应json数据的名称，我之前随便写了个，被坑很久
    private WeatherInfo weatherinfo;

    public WeatherInfo getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherInfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
