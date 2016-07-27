package com.shenjianli.lib.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;

import com.shenjianli.shenlib.R;
import com.shenjianli.shenlib.util.LogUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundMonitorService extends Service {

	private Timer mCheckIsOnBackGroundTimer = null;
	private TimerTask mCheckIsOnBackGroundTimerTask = null;
	private Timer mCheckIsOnForeGroundTimer = null;
	private TimerTask mCheckIsOnForeGroundTimerTask = null;
	private Timer mBackGroundTimeOutTimer = null;
	private TimerTask mBackGroundTimeOutTimerTask = null;
	private long mLogonAndEnterBackgroundTime;

	private static String packageName = "";

	private enum AppRunState {
		RunForeGround, RunBackGround, NotRun
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			packageName = info.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		LogUtils.i(packageName);
		runBackGroundTask();
	}

	private void runBackGroundTask() {
		if (findTask()) {
			mCheckIsOnBackGroundTimer = new Timer();
			mCheckIsOnBackGroundTimerTask = new TimerTask() {
				public void run() {
					checkIsOnBackGround();
				}
			};
			mCheckIsOnBackGroundTimer.schedule(mCheckIsOnBackGroundTimerTask,
					0, 1000);
		}
	}

	private void runForeGroundTask() {
		if (findTask()) {
			mCheckIsOnForeGroundTimer = new Timer();
			mCheckIsOnForeGroundTimerTask = new TimerTask() {
				public void run() {
					checkIsOnForeGround();
				}
			};
			mCheckIsOnForeGroundTimer.schedule(mCheckIsOnForeGroundTimerTask,
					0, 1000);
		}
	}

	// private void runBackGroundTimeOutTask() {
	// if (findTask()) {
	// if (!"0".equals(UserSessionInfo.getInstance().getLoginType())) {
	// // ÈôÊÇµÇÂ¼×´Ì¬¿ªÊ¼¼ÆÊ±
	// mLogonAndEnterBackgroundTime = System.currentTimeMillis();
	// }
	// mBackGroundTimeOutTimer = new Timer();
	// mBackGroundTimeOutTimerTask = new TimerTask() {
	// public void run() {
	// checkBackGroundTimeOut();
	// }
	// };
	// mBackGroundTimeOutTimer.schedule(mBackGroundTimeOutTimerTask, 0, 1000);
	// }
	// }

	@Override
	public void onDestroy() {
		super.onDestroy();
		destroyBackTask();
		destroyForeTask();
		// destroyBackGroundTimeOutTask();
	}

	private void checkIsOnBackGround() {
		if (getAppRunState().equals(AppRunState.RunBackGround)) {
			showNormalNotification(R.drawable.ic_launcher);
			destroyBackTask();
			runForeGroundTask();
			// runBackGroundTimeOutTask();
		}
	}

	private void checkIsOnForeGround() {
		if (getAppRunState().equals(AppRunState.RunForeGround)) {
			closeNormalNotification(R.drawable.ic_launcher);
			// closeTimeOutNotification(R.drawable.dock);
			destroyForeTask();
			// destroyBackGroundTimeOutTask();
			runBackGroundTask();
		}
	}

	@SuppressWarnings("unused")
	private RunningAppProcessInfo getAppInfo() {
		SystemClock.sleep(100);
		RunningAppProcessInfo rap = null;
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		if (activityManager == null)
			return null;
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null || appProcesses.size() <= 0)
			return null;
		int len = appProcesses.size();
		for (int i = 0; i < len; i++) {
			RunningAppProcessInfo pi = appProcesses.get(i);
			if (pi == null)
				continue;
			if (!TextUtils.isEmpty(pi.processName)
					&& pi.processName.equals(packageName)) {
				rap = pi;
				break;
			}
		}
		return rap;
	}

	private AppRunState getAppRunState() {
		SystemClock.sleep(100);
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		if (activityManager == null)
			return AppRunState.NotRun;
		List<RunningTaskInfo> appTask = activityManager.getRunningTasks(100);
		if (appTask == null || appTask.size() <= 0)
			return AppRunState.NotRun;
		int len = appTask.size();
		if (appTask.get(0).baseActivity.getPackageName().equals(packageName)
				&& appTask.get(0).topActivity.getPackageName().equals(
						packageName)) {
			return AppRunState.RunForeGround;
		}
		for (int i = 1; i < len; i++) {
			RunningTaskInfo info = appTask.get(i);
			if (info.topActivity.getPackageName().equals(
					packageName)
					&& info.baseActivity.getPackageName().equals(
							packageName)) {
				return AppRunState.RunBackGround;
			}
		}
		return AppRunState.NotRun;
	}

	@SuppressWarnings("deprecation")
	private void showNormalNotification(int id) {
		new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				Toast.makeText(getApplicationContext(), "温馨提示：好e购已经切换到后台", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			}
		}).start();
		SystemClock.sleep(300);
//		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		Notification notification = new Notification(R.drawable.ic_launcher,
//				"好e购", System.currentTimeMillis());
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
//		Intent intent = new Intent(this, MainActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//				intent, 0);
//		notification.setLatestEventInfo(this, "好e购", "好e购已经切换到后台",
//				pendingIntent);
//		notification.contentIntent = pendingIntent;
//
//		notificationManager.notify(id, notification);
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentTitle("好e购");
		builder.setContentText("好e购已经切换到后台");
		builder.setSmallIcon(R.drawable.ic_launcher);
		Notification notification = builder.getNotification();
		notificationManager.notify(id, notification);
	}

	private void closeNormalNotification(int id) {
		try {
			NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.cancel(id);
		} catch (Exception e) {
		}
	}

	private boolean findTask() {
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		if (activityManager == null)
			return false;
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null || appProcesses.size() <= 0)
			return false;
		int len = appProcesses.size();
		boolean find = false;
		for (int i = 0; i < len; i++) {
			RunningAppProcessInfo pi = appProcesses.get(i);
			if (pi == null)
				continue;
			if (!TextUtils.isEmpty(pi.processName)
					&& pi.processName.equals(packageName)) {
				find = true;
				break;
			}
		}
		return find;
	}

	private void destroyBackTask() {
		if (mCheckIsOnBackGroundTimer != null) {
			mCheckIsOnBackGroundTimer.cancel();
			mCheckIsOnBackGroundTimer = null;
		}
		if (mCheckIsOnBackGroundTimerTask != null) {
			mCheckIsOnBackGroundTimerTask.cancel();
			mCheckIsOnBackGroundTimerTask = null;
		}
	}

	private void destroyForeTask() {
		if (mCheckIsOnForeGroundTimer != null) {
			mCheckIsOnForeGroundTimer.cancel();
			mCheckIsOnForeGroundTimer = null;
		}
		if (mCheckIsOnForeGroundTimerTask != null) {
			mCheckIsOnForeGroundTimerTask.cancel();
			mCheckIsOnForeGroundTimerTask = null;
		}
	}
}
