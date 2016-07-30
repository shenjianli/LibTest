package com.shenjianli.lib.engine.rxandroid;

/**
 * Created by shenjianli on 16/7/30.
 *
 Http服务返回一个固定格式的数据的问题。 例如：
 {
 "resultCode": 0,
 "resultMessage": "成功",
 "data": {}
 }
 如果data是一个User对象的话。那么在定义Service方法的返回值就可以写为
 Observable<HttpResult<User>>

 */
public class HttpResult<T> {
    private int resultCode;
    private String resultMessage;

    private T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
