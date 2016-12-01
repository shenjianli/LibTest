package com.shenjianli.shenlib.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;

import com.shenjianli.shenlib.ScreenInfo;

/**
 * Created by shenjianli on 16/10/13.
 */
public class ScreenUtils {

    private ScreenUtils(){
    }

    public static ScreenInfo getScreenInfo(Activity activity){

        ScreenInfo screenInfo = new ScreenInfo();
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        screenInfo.density = metrics.density;
        screenInfo.densityDpi = metrics.densityDpi;
        screenInfo.heightPixels = metrics.heightPixels;
        screenInfo.widthPixels = metrics.widthPixels;
        screenInfo.scaledDensity = metrics.scaledDensity;
        screenInfo.xdpi = metrics.xdpi;
        screenInfo.ydpi = metrics.ydpi;


        screenInfo.drawableStr = densityDpiToString(metrics.densityDpi);


        //获取屏幕尺寸
        screenInfo.screenSizeWidth = ((float) metrics.widthPixels) / metrics.xdpi;
        screenInfo.screenSizeHeight = (((float) metrics.heightPixels) / metrics.ydpi);
        double x = Math.pow(metrics.widthPixels / metrics.xdpi, 2);
        double y = Math.pow(metrics.heightPixels / metrics.ydpi, 2);
        screenInfo.screenSize = (float) Math.sqrt(x + y);



        screenInfo.dipW = (((float) metrics.widthPixels) * 160.0f) / ((float) metrics.densityDpi);
        screenInfo.dipH = (((float) metrics.heightPixels) * 160.0f) / ((float) metrics.densityDpi);

        screenInfo.suggestionLayout = getSmallestWidthString((int)  screenInfo.dipW, (int)  screenInfo.dipH) + getResolutionString(metrics.widthPixels, metrics.heightPixels);
        screenInfo.suggestionLayoutLand = ("layout-land" + getSmallestWidthString((int) screenInfo.dipW, (int) screenInfo.dipH) + getResolutionString(metrics.widthPixels, metrics.heightPixels));
        screenInfo.suggestionLayoutSimple = ("layout" + getSmallestWidthString((int) screenInfo.dipW, (int) screenInfo.dipH));
        screenInfo.suggestionValues = ("values" + getSmallestWidthString((int) screenInfo.dipW, (int) screenInfo.dipH) + getResolutionString(metrics.widthPixels, metrics.heightPixels));
        screenInfo.suggestionValuesLand = ("values-land" + getSmallestWidthString((int) screenInfo.dipW, (int) screenInfo.dipH) + getResolutionString(metrics.widthPixels, metrics.heightPixels));
        screenInfo.suggestionValuesSimple = ("values" + getSmallestWidthString((int) screenInfo.dipW, (int) screenInfo.dipH));

        screenInfo.statusBaHheight = getStatusBarHeight(activity);
        screenInfo.navigationBarHeight =  getNavigationBarHeight(activity);

        return screenInfo;
    }


    //获得导航栏的高度
    private static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android"); //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    //获取状态栏的高度
    public static int getStatusBarHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;  //屏幕宽
        int height = dm.heightPixels;  //屏幕高
        Rect frame = new Rect();
       activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;  //状态栏高
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentTop - statusBarHeight; //标题栏高

        return titleBarHeight;
    }


    private static String densityDpiToString(int densityDpi) {
        StringBuilder result = new StringBuilder();
        switch (densityDpi) {
            case DisplayMetrics.DENSITY_HIGH:
                result.append("(hdpi)");
                break;
            case DisplayMetrics.DENSITY_LOW:
                result.append("ldpi");
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                result.append("(mdpi)");
                break;
            case DisplayMetrics.DENSITY_TV:
                result.append("(tvdpi)");
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                result.append("(xhdpi)");
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                result.append("(xxhdpi)");
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                result.append("(xxxhdpi)");
                break;
            case DisplayMetrics.DENSITY_280:
                result.append("(h - xh)");
                break;
            case DisplayMetrics.DENSITY_360:
                result.append("(xh - xxh)");
                break;
            case DisplayMetrics.DENSITY_400:
                result.append("(xh - xxh)");
                break;
            case DisplayMetrics.DENSITY_420:
                result.append("(xh - xxh)");
                break;
            case DisplayMetrics.DENSITY_560:
                result.append("(xxh - xxxh)");
                break;
            default:
                result.append("not support");
            }
        return result.toString();
    }

    private static String getResolutionString(int rw, int rh) {
        return rw > rh ? "-" + rw + "x" + rh : "-" + rh + "x" + rw;
    }

    private static String getSmallestWidthString(int dipWidth, int dipHeight) {
        StringBuilder stringBuilder = new StringBuilder("-sw");
        if (dipWidth >= dipHeight) {
            dipWidth = dipHeight;
        }
        return stringBuilder.append(dipWidth).append("dp").toString();
    }

}
