package com.shenjianli.lib;

import android.app.Application;

import com.shenjianli.shenlib.LibApp;
import com.shenjianli.shenlib.exception.CrashHandler;
import com.shenjianli.shenlib.util.LogUtils;

/**
 * Created by edianzu on 2016/7/14.
 */
public class MobileApp extends Application{

    private static MobileApp mMobileApp;
    @Override
    public void onCreate() {
        super.onCreate();
        LibApp.getLibInstance();
        if(!LogUtils.isOutPutLog){
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(this);
        }
        mMobileApp = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
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
}
