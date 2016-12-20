package com.shenjianli.shenlib.util;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.shenjianli.shenlib.Constants;


public class NetUtils {

	public static boolean checkNet(Context context) {
		boolean isMobile = checkConnection(context,ConnectivityManager.TYPE_MOBILE);
		boolean isWifi = checkConnection(context,ConnectivityManager.TYPE_WIFI);
		
		
		if(isMobile) {
			readAPN(context);
		}

		if (!isMobile && !isWifi) {
			return false;
		}

		return true;
	}

	private static void readAPN(Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri PREFERRED_APN_URI = Uri.parse("content://content://telephony/carriers/preferapn");
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null, null);
		
		if(cursor != null && cursor.isFirst()) {
			Constants.PROXY = cursor.getString(cursor.getColumnIndex("proxy"));
			Constants.PORT = cursor.getInt(cursor.getColumnIndex("port"));
		}
	}

	private static boolean checkConnection(Context context,int netType) {
		ConnectivityManager cm = 
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(netType);
		boolean isConnected = networkInfo.isConnected();

		return isConnected;
	}

	public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;

    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Wifi
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORN_WIFI;
        }

        // 3G
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORN_MOBILE;
        }
        return NETWORN_NONE;
    }

	public static String getWifiDeviceInfo(Context context){
		StringBuilder wifiDeviceId = new StringBuilder();
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		wifiDeviceId.append("SSID=");
		wifiDeviceId.append(wifiInfo.getSSID());
		wifiDeviceId.append("，MacAddress=");
		wifiDeviceId.append(wifiInfo.getMacAddress());
		wifiDeviceId.append("，HiddenSSID=");
		wifiDeviceId.append(wifiInfo.getHiddenSSID());
		wifiDeviceId.append("，NetworkId=");
		wifiDeviceId.append(wifiInfo.getNetworkId());
		wifiDeviceId.append("，Rssi=");
		wifiDeviceId.append(wifiInfo.getRssi());
		wifiDeviceId.append("，BSSID=");
		wifiDeviceId.append(wifiInfo.getBSSID());
		return wifiDeviceId.toString();
	}

}
