package com.shenjianli.shenlib.util;

import android.content.Context;
import android.widget.Toast;

public class CustomToast 
{
	public static void show(Context context,String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void show(Context context,int resId)
	{
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}
}
