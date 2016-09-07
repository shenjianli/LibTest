package com.shenjianli.shenlib.net;

public class BaseCallModel<T> {
    //0表示失败，非0  其他表示成功
	public int errno;
    //表示错误信息
    public String errMsg;
    //表示数据
    public T data;

}
