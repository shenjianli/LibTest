package com.shenjianli.lib;

import com.shenjianli.shenlib.util.LogUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 产生数据实体的Bean工厂
 */
public class BeanFactory {

	private static BeanFactory mBeanFactory;

	private Properties props;

	private BeanFactory(){

	}

	public static BeanFactory getBeanFactory(){
		if(null == mBeanFactory){
			mBeanFactory = new BeanFactory();
		}
		return  mBeanFactory;
	}

	public void initBeanFactory(String configFileName){
		props = new Properties();
		try {
			props.load(BeanFactory.class.getClassLoader().getResourceAsStream(configFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建具体的实现类
	 * @param <T> 接口类型
	 * @param clazz 字节码对象
	 * @return 返回指定的具体实现类,失败返回null
	 */
	public  <T> T getImpl(Class<T> clazz) {
		String key = clazz.getSimpleName();
		if(null == props){
			props = new Properties();
			try {
				props.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));
			} catch (IOException e) {
				e.printStackTrace();
				LogUtils.d("加载配置文件失败");
				return  null;
			}
		}
		String className = props.getProperty(key);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.d("创建对象失败");
		}
		return null;
	}
	
}
