package com.shenjianli.shenlib.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.shenjianli.shenlib.util.LogUtils;


public class BaseFragmentActivity extends FragmentActivity {

	private final String TAG = getClass().getSimpleName();

	private int param = 1;

	/**
	 * 启动Activity：系统会先调用onCreate方法，然后调用onStart方法，最后调用onResume，Activity进入运行状态。
	 * 当前Activity处于被覆盖状态或者后台不可见状态
	 * ，即onpause和第onstop步，系统内存不足，杀死当前Activity，而后用户退回当前Activity
	 * ：再次调用onCreate方法、onStart方法、onResume方法，进入运行状态。
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		LogUtils.d(TAG, "onCreate");
		ActivityManager.getInstance().addActivity(this);
	}

	/**
	 * Activity创建或者从后台重新回到前台时被调用  
	 */
	@Override
	protected void onStart() {
		super.onStart();
		LogUtils.d(TAG, "onStart");
	}

	/**
	 * Activity被系统杀死后再重建时被调用.
	 * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity.
	 * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		param = savedInstanceState.getInt("param");
		LogUtils.i(TAG, "onRestoreInstanceState called. get param: " + param);
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 用户后退回到此Activity：系统会先调用onRestart方法，然后调用onStart方法，最后调用onResume方法，再次进入运行状态。
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		LogUtils.d(TAG, "onRestart");
	}

	/**
	 * 当前Activity由被覆盖状态回到前台或解锁屏：系统会调用onResume方法，再次进入运行状态。
	 */
	@Override
	protected void onResume() {
		super.onResume();
		LogUtils.d(TAG, "onResume");
	}

	/**
	 * Activity被系统杀死时被调用. 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死.
	 * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态. 在onPause之前被调用.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("param", param);
		LogUtils.i(TAG, "onSaveInstanceState called. put param: " + param);
		super.onSaveInstanceState(outState);
	}

	/**
	 * 当前Activity被其他Activity覆盖其上或被锁屏：系统会调用onPause方法，暂停当前Activity的执行。
	 */
	@Override
	protected void onPause() {
		super.onPause();
		LogUtils.d(TAG, "onPause");
	}

	/**
	 * 当前Activity转到新的Activity界面或按Home键回到主屏，自身退居后台：系统会先调用onPause方法，然后调用onStop方法，
	 * 进入停滞状态。
	 */
	@Override
	protected void onStop() {
		super.onStop();
		LogUtils.d(TAG, "onStop");
	}

	/**
	 * 用户退出当前Activity：系统先调用onPause方法，然后调用onStop方法，最后调用onDestory方法，结束当前Activity。
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtils.d(TAG, "onDestroy");
		ActivityManager.getInstance().removeActivityFromStack(this);
	}

	/**
	 *  Activity窗口获得或失去焦点时被调用,在onResume之后或onPause之后
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		LogUtils.i(TAG, "onWindowFocusChanged called.");
	}

	/**
	 *  当指定了android:configChanges="orientation"后,方向改变时onConfigurationChanged被调用
	 *  为了避免这样销毁重建的过程，我们需要在AndroidMainfest.xml中对OrientationActivity对应的<activity>配置android:configChanges="orientation"
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		switch (newConfig.orientation) {
		case Configuration.ORIENTATION_PORTRAIT:
			LogUtils.i(TAG, "onConfigurationChanged called. ORIENTATION_PORTRAIT");
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			LogUtils.i(TAG, "onConfigurationChanged called. ORIENTATION_LANDSCAPE");
			break;
		}
	}

}
