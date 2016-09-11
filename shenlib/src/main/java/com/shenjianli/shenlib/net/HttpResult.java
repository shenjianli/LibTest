package com.shenjianli.shenlib.net;

import android.text.TextUtils;

public class HttpResult<T> {


    //0表示失败，非0  其他表示成功
    private String status;

    //错误编号
	private String errNo;

    //表示错误信息
    private String errMsg;

    //表示数据
    private T data;

    public String getErrNo() {
        return errNo;
    }

    public void setErrNo(String errNo) {
        this.errNo = errNo;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess(){
        if(!TextUtils.isEmpty(status) && !"0".equals(status)){
            return true;
        }
        return false;
    }
}
