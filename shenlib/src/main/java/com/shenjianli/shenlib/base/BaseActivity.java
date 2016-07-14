package com.shenjianli.shenlib.base;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initVariables();
		initViews(savedInstanceState);
		loadData();
	}

	protected abstract void initVariables();

	protected abstract void initViews(Bundle savedInstanceState);

	protected abstract void loadData();

	@Override
	protected void onDestroy() {
		/**
		 * 在activity销毁的时候同时设置停止请求，停止线程请求回调
		 */

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}