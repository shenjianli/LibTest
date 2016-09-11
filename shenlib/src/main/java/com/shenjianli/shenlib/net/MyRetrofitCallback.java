package com.shenjianli.shenlib.net;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class MyRetrofitCallback<T> implements Callback<HttpResult<T>> {

	@Override
	public void onFailure(Call<HttpResult<T>> arg0, Throwable arg1) {
		onFail(arg1.getMessage());
	}

	@Override
	public void onResponse(Call<HttpResult<T>> arg0, Response<HttpResult<T>> response) {
		HttpResult<T> model = response.body();
		if(null == model){
			// 404 or the response cannot be converted to User.
			ResponseBody responseBody = response.errorBody();
			if (responseBody != null) {
				try {
					onFail(responseBody.string());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				onFail("responseBody = null");
			}
		}
		else {//200
			if(!model.isSuccess()){
				onFail(model.getErrMsg());
			}
			else {
				onSuccess(model.getData());
			}

		}
	}
	
	public abstract void onSuccess(T t);
	
	public abstract void onFail(String errorMsg);
}
