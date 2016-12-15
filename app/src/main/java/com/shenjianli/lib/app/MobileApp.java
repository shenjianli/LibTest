package com.shenjianli.lib.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;

import com.shenjianli.lib.BuildConfig;
import com.shenjianli.lib.R;
import com.shenjianli.shenlib.LibApp;
import com.shenjianli.shenlib.base.ActivityManager;
import com.shenjianli.shenlib.exception.CrashHandler;
import com.shenjianli.shenlib.util.FileUtils;
import com.shenjianli.shenlib.util.LogUtils;


/**
 * Created by shenjianli on 2016/7/14.
 */
public class MobileApp extends MultiDexApplication{

    private static MobileApp mMobileApp;

    private boolean isConnect;

    @Override
    public void onCreate() {
        super.onCreate();
        LibApp.getLibInstance().setMobileContext(this);

        com.wanjian.sak.LayoutManager.init(this);

        //initByRawConfigFile();
        initByGradleFile();

        LibApp.getLibInstance().setBeanFactoryConfig(R.raw.bean);
        //BeanFactory.getBeanFactory().initBeanFactory(R.raw.bean);
        //BeanFactory.getBeanFactory().initBeanFactory("bean");

        if(!LogUtils.isOutPutLog){
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(this);
        }
        mMobileApp = this;
    }

    /*
    根据主项目中的gradle配置文件开初始化不同的开发模式
     */
    private void initByGradleFile() {

        if(Constants.TEST_MODE.equals(BuildConfig.MODE)){
            LibApp.getLibInstance().setLogEnable(true);
            LibApp.getLibInstance().setUrlConfigManager(R.xml.url);
        }
        else if(Constants.DEV_MODE.equals(BuildConfig.MODE))
        {
            LibApp.getLibInstance().setLogEnable(true);
            LibApp.getLibInstance().setServerBaseUrl(BuildConfig.SERVER_URL);
        }
        else if(Constants.RELEASE_MODE.equals(BuildConfig.MODE)){
            LibApp.getLibInstance().setLogEnable(true);
            LibApp.getLibInstance().setServerBaseUrl(BuildConfig.SERVER_URL);
        }
    }

    /*
    根据Raw中的mobile配置文件来初始化开发模式
     */
    private void initByRawConfigFile() {
        if(FileUtils.getProperties(this, R.raw.mode)){
            String mode = FileUtils.getPropertyValueByKey("mode");
            String baseUrl = FileUtils.getPropertyValueByKey("baseUrl");
            LogUtils.i("开发模式为：" + mode);
            if(Constants.TEST_MODE.equals(mode)){
                LibApp.getLibInstance().setLogEnable(true);
                LibApp.getLibInstance().setUrlConfigManager(R.xml.url);
            }
            else if(Constants.DEV_MODE.equals(BuildConfig.MODE))
            {
                LibApp.getLibInstance().setLogEnable(true);
                LibApp.getLibInstance().setServerBaseUrl(baseUrl);
            }
            else if(Constants.RELEASE_MODE.equals(BuildConfig.MODE)){
                LibApp.getLibInstance().setLogEnable(false);
                LibApp.getLibInstance().setServerBaseUrl(baseUrl);
            }
        }
    }

    public static MobileApp getAppInstance(){
        return  mMobileApp;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setConnect(boolean b) {
        isConnect = b;
    }

    public boolean isConnect() {
        return isConnect;
    }

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
     * 获取当前客户端版本信息
     */
    public String getCurrentVersion(){
        String versionName = "mobileVersion_";
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName += info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
            versionName += "1.2.0.9";
        }
        versionName += "_";
        return versionName;
    }


    public void exit(){
        ActivityManager.getInstance().appExit(this);
    }
}
