package com.shenjianli.shenlib.base;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

public class ActivityManager {

	/*
	 * 由于静态单例对象没有作为Singleton的成员变量直接实例化，因此类加载时不会实例化Singleton，第一次调用getInstance()
	 * 时将加载内部类HolderClass
	 * ，在该内部类中定义了一个static类型的变量instance，此时会首先初始化这个成员变量，由Java虚拟机来保证其线程安全性
	 * ，确保该成员变量只能初始化一次。由于getInstance()方法没有任何线程锁定，因此其性能不会造成任何影响。
	 * 通过使用IoDH，我们既可以实现延迟加载
	 * ，又可以保证线程安全，不影响系统性能，不失为一种最好的Java语言单例模式实现方式（其缺点是与编程语言本身的特性相关
	 * ，很多面向对象语言不支持IoDH）。
	 */
	private static class HolderClass {
		private static ActivityManager instance = new ActivityManager();
	}


	private static Stack<Activity> activityStack;

	private ActivityManager() {

	}

	public static ActivityManager getInstance() {
		return HolderClass.instance;
	}

	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	public void removeActivityFromStack(Activity activity) {
		if (null != activityStack) {
			activityStack.remove(activity);
		}
	}

	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	public void finishActivity(Activity activity) {
		if (activity != null) {
			if(null != activityStack){
				activityStack.remove(activity);
			}
			activity.finish();
			activity = null;
		}
	}

	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	public void appExit(Context context) {
		try {
			finishAllActivity();
//			ActivityManager activityMgr = (ActivityManager) context
//					.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}
