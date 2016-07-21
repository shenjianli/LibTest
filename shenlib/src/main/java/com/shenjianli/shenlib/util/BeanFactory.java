package com.shenjianli.shenlib.util;

import android.content.Context;
import android.text.TextUtils;

import com.shenjianli.shenlib.LibApp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class BeanFactory {

	private static BeanFactory mBeanFactory;

	private Map<String, String> mProperty = new HashMap<String, String>();

	private BeanFactory(){
	}

	public static BeanFactory getBeanFactory(){
		if(null == mBeanFactory){
			mBeanFactory = new BeanFactory();
		}
		return  mBeanFactory;
	}

	public void initBeanFactory(int fileId){
		getProperties(LibApp.getLibInstance().getMobileContext(),fileId);
	}

	/**
	 * 创建具体的实现类
	 * @param <T> 接口类型
	 * @param clazz 字节码对象
	 * @return 返回指定的具体实现类,失败返回null
	 */
	public  <T> T getImpl(Class<T> clazz) {
		String key = clazz.getSimpleName();
		String className = getPropertyValueByKey(key);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.d("创建对象失败");
		}
		return null;
	}



	private String getPropertyValueByKey(String key) {
		if (null != mProperty && mProperty.size() > 0) {
			String result = mProperty.get(key);
			return result;
		} else {
			return null;
		}
	}


	private boolean getProperties(Context context, int fileId) {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			// 读取属性文件a.properties
			in = context.getResources().openRawResource(fileId);
			prop.load(new InputStreamReader(in, "UTF-8")); // /加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = prop.getProperty(key);
				if (!TextUtils.isEmpty(key)) {
					if(null == mProperty){
						mProperty = new HashMap<String, String>();
					}
					mProperty.put(key, value);
				}
				LogUtils.i( key + ":" + prop.getProperty(key));
			}
			return true;
		} catch (Exception e) {
			LogUtils.i("读取配置文件出错了！" + e.toString());
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
