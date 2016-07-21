package com.shenjianli.shenlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shenjianli.shenlib.LibApp;
import com.shenjianli.shenlib.util.NetUtils;

import java.util.ArrayList;

public class NetBroadcastReceiver extends BroadcastReceiver {

	private static ArrayList<NetStateChangeListener> mListeners = new ArrayList<NetStateChangeListener>();
	private String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(NET_CHANGE_ACTION)) {
			boolean connect = false;
			if (NetUtils.NETWORN_NONE == NetUtils.getNetworkState(context)) {
				LibApp.getLibInstance().setConnect(false);
				connect = false;
			}
			else{
				connect = true;
			}
			if(null != mListeners){
				if (mListeners.size() > 0)//通知接口完成加载
				{
					for (NetStateChangeListener handler:mListeners){
						handler.onNetChange(connect);
					}
				}
			}
			else{
				mListeners = new ArrayList<NetStateChangeListener>();
			}
		}
	}

	public interface NetStateChangeListener {
		public abstract void onNetChange(boolean connect);
	}

	public static void addNetStateListener(NetStateChangeListener listener) {
		if (null != mListeners) {
			mListeners.add(listener);
		}
		else{
			mListeners = new ArrayList<NetStateChangeListener>();
		}
	}

	public static void clearNetStateListener() {
		if (null != mListeners) {
			mListeners.clear();
		}
	}

}
