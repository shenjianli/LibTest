package com.shenjianli.shenlib;

import android.app.Application;

/**
 * Created by edianzu on 2016/7/7.
 */
public class LibApp extends Application{

    private static LibApp  mLibApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mLibApp = this;
    }

    public static LibApp getLibInstance(){
        return mLibApp;
    }

}
