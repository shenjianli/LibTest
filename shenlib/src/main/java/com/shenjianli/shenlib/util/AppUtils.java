package com.shenjianli.shenlib.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 跟App相关的辅助类
 * 
 * @author zhy
 * 
 */
public class AppUtils
{
	private AppUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static boolean networkIsAvailable(Context context) {

		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMobileConnected(context);
		if (wifiConnected == false && mobileConnected == false) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public static boolean checkNet(Context context) {
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMobileConnected(context);
		if (wifiConnected == false && mobileConnected == false) {
			return false;
		}
		return true;
	}

	/**
	 * @param context
	 * @return
	 */
	public static boolean isWIFIConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * @param context
	 * @return 手机上每个App的内存限制大小
	 */
	public static String getMemoryLimited(Context context) {
		StringBuffer result = new StringBuffer();
		ActivityManager activityManager =(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		result.append("最大内存限制：");
		result.append(activityManager.getMemoryClass());
		result.append(",");

		result.append("最大内存限制：" );
		result.append(activityManager.getMemoryClass());
		result.append(",");


		result.append("运行时最大内存限制：");
		result.append(Runtime.getRuntime().maxMemory()/(1024*1024));

		return result.toString();
	}

	public static String printMem(String when) {
		//程序可用的最大内存
		float maxMem = Runtime.getRuntime().maxMemory() / 1024 / 1024;
		//程序当前占用的内存
		float totalMem = Runtime.getRuntime().totalMemory() / 1024 / 1024;
		//freeMem != maxMem - totalMem
		//我理解 freeMem应该是 当前分配给该程序的内存 - totalMem， 当前分配给程序的内存时动态的(在小于maxMem范围内)
		//同virtualbox安装的ubuntu虚拟机占用内存类似，设置个最大内存，但实际占用内存时动态分配的
		float freeMem = Runtime.getRuntime().freeMemory() / 1024 / 1024;

		return (when + ": maxMem | totalMem | freeMem : " + maxMem + "M|" + totalMem + "M|" + freeMem + "M");
	}
}
