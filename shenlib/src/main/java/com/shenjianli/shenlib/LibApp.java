package com.shenjianli.shenlib;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.shenjianli.shenlib.net.mock.UrlConfigManager;
import com.shenjianli.shenlib.util.BeanFactory;
import com.shenjianli.shenlib.util.LogUtils;

/**
 * Created by edianzu on 2016/7/7.
 */
public class LibApp extends Application{

    private static LibApp  mLibApp;

    private Context mMobileContext;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    private boolean isConnect;


    public Context getMobileContext() {
        return mMobileContext;
    }

    public void setMobileContext(Context mMobileContext) {
        this.mMobileContext = mMobileContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // 私有化构造函数
    private LibApp(){

    }

    // 懒汉模式获取单实例
    public synchronized static LibApp getLibInstance(){
       if(null == mLibApp){
           mLibApp = new LibApp();
       }
        return mLibApp;
    }

    public void setLogEnable(boolean enable){
        LogUtils.isOutPutLog = enable;
    }

    public void setUrlConfigManager(int configFileId){
        if(-1 == configFileId){
           UrlConfigManager.MockServiceEnable  = false;
        }
        else{
            UrlConfigManager.getUrlConfigManager().initUrlConfigManager(configFileId);
        }
    }

    public void setBeanFactoryConfig(int fileId){
        if( -1 != fileId){
            BeanFactory.getBeanFactory().initBeanFactory(fileId);
        }
        else{
            LogUtils.w("BeanFactory 配置文件为空");
        }
    }

}
