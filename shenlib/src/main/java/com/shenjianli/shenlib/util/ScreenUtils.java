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
                .append(",densityDpi=").append(metrics.densityDpi)
                .append(",heightPixels=").append(metrics.heightPixels)
                .append(",widthPixels=").append(metrics.widthPixels)
                .append(",scaledDensity=").append(metrics.scaledDensity)
                .append(",xdpi=").append(metrics.xdpi)
                .append(",ydpi=").append(metrics.ydpi);

        result.append("，适配图片=");


        switch (metrics.densityDpi){
            case DisplayMetrics.DENSITY_HIGH:
                result.append("high");
                break;
            case DisplayMetrics.DENSITY_LOW:
                result.append("low");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                result.append("medium");
                break;
            case DisplayMetrics.DENSITY_TV:
                result.append("tv");
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                result.append("xhigh");
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                result.append("xxhigh");
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                result.append("xxxhigh");
                break;
            case DisplayMetrics.DENSITY_280:
                result.append("h-xh");
                break;
            case DisplayMetrics.DENSITY_360:
                result.append("xh-xxh");
                break;
            case DisplayMetrics.DENSITY_400:
                result.append("xh-xxh");
                break;
            case DisplayMetrics.DENSITY_420:
                result.append("xh-xxh");
                break;
            case DisplayMetrics.DENSITY_560:
                result.append("xxh-xxxh");
                break;
            default:
                result.append("not support");
        }
        return result.toString();
    }

}
