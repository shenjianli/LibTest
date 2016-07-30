package com.shenjianli.lib.engine.rxandroid;

/**
 * Created by shenjianli on 16/7/30.
 * <p>
 * Http服务返回一个固定格式的数据的问题。 例如：
 * {
 * "resultCode": 0,
 * "resultMessage": "成功",
 * "data": {}
 * }
 * 如果data是一个User对象的话。那么在定义Service方法的返回值就可以写为
 * Observable<HttpResult<User>>
 */
public class HttpResultTest<T> {
    private int resultCode;
    private String resultMessage;

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

    //用来模仿resultCode和resultMessage
    private int count;
    private int start;
    private int total;
    private String title;

    //用来模仿Data
    private T subjects;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }
}
