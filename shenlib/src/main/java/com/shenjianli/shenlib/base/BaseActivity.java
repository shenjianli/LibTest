package com.shenjianli.shenlib.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;

import com.shenjianli.shenlib.util.LogUtils;

public class BaseActivity extends Activity {

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
	 * Activity窗口获得或失去焦点时被调用,在onResume之后或onPause之后
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		LogUtils.i(TAG, "onWindowFocusChanged called.");
	}

	/**
	 * 当指定了android:configChanges="orientation"后,方向改变时onConfigurationChanged被调用
	 * 为了避免这样销毁重建的过程
	 * ，我们需要在AndroidMainfest.xml中对OrientationActivity对应的<activity>配置android
	 * :configChanges="orientation"
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		switch (newConfig.orientation) {
		case Configuration.ORIENTATION_PORTRAIT:
			LogUtils.i(TAG,
					"onConfigurationChanged called. ORIENTATION_PORTRAIT");
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			LogUtils.i(TAG,
					"onConfigurationChanged called. ORIENTATION_LANDSCAPE");
			break;
		}
	}

	/**
	 * TRIM_MEMORY_RUNNING_MODERATE
	 * 表示应用程序正常运行，并且不会被杀掉。但是目前手机的内存已经有点低了，系统可能会开始根据LRU缓存规则来去杀死进程了。
	 * TRIM_MEMORY_RUNNING_LOW
	 * 表示应用程序正常运行，并且不会被杀掉。但是目前手机的内存已经非常低了，我们应该去释放掉一些不必要的资源以提升系统的性能
	 * ，同时这也会直接影响到我们应用程序的性能。 TRIM_MEMORY_RUNNING_CRITICAL
	 * 表示应用程序仍然正常运行，但是系统已经根据LRU缓存规则杀掉了大部分缓存的进程了
	 * 。这个时候我们应当尽可能地去释放任何不必要的资源，不然的话系统可能会继续杀掉所有缓存中的进程
	 * ，并且开始杀掉一些本来应当保持运行的进程，比如说后台运行的服务。
	 * 
	 * 
	 * 以上是当我们的应用程序正在运行时的回调，那么如果我们的程序目前是被缓存的，则会收到以下几种类型的回调：
	 * 
	 * 
	 * TRIM_MEMORY_BACKGROUND
	 * 表示手机目前内存已经很低了，系统准备开始根据LRU缓存来清理进程。这个时候我们的程序在LRU缓存列表的最近位置
	 * ，是不太可能被清理掉的，但这时去释放掉一些比较容易恢复的资源能够让手机的内存变得比较充足
	 * ，从而让我们的程序更长时间地保留在缓存当中，这样当用户返回我们的程序时会感觉非常顺畅，而不是经历了一次重新启动的过程。
	 * TRIM_MEMORY_MODERATE
	 * 表示手机目前内存已经很低了，并且我们的程序处于LRU缓存列表的中间位置，如果手机内存还得不到进一步释放的话，那么我们的程序就有被系统杀掉的风险了。
	 * TRIM_MEMORY_COMPLETE
	 * 表示手机目前内存已经很低了，并且我们的程序处于LRU缓存列表的最边缘位置，系统会最优先考虑杀掉我们的应用程序
	 * ，在这个时候应当尽可能地把一切可以释放的东西都进行释放。
	 */
	@SuppressLint("NewApi")
	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		switch (level) {
		case TRIM_MEMORY_UI_HIDDEN:
			break;
		default:
			break;
		}
	}

	/**
	 * 设置字体不随系统设置变化而改变
	 * @return
     */
	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		Configuration config=new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config,res.getDisplayMetrics() );
		return res;
	}
}
