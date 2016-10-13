package com.shenjianli.shenlib.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by shenjianli on 16/10/13.
 */
public class ScreenUtils {

    public static String getScreenInfo(Activity activity){

        StringBuffer result = new StringBuffer();
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        result.append("本手机的屏幕信息：").append("density=").append(metrics.density)
                .append(",densityDpi").append(metrics.densityDpi)
                .append(",heightPixels").append(metrics.heightPixels)
                .append(",widthPixels").append(metrics.widthPixels)
                .append(",scaledDensity").append(metrics.scaledDensity)
                .append(",xdpi").append(metrics.xdpi)
                .append(",ydpi").append(metrics.ydpi);

        return result.toString();
    }

}
