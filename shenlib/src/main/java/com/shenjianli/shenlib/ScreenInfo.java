package com.shenjianli.shenlib;

/**
 * Created by edianzu on 2016/12/1.
 */
public class ScreenInfo {

    /**
    屏幕宽（像素 px）
     */
    public int widthPixels;
    /**
     屏幕高（像素 px）
     */
    public int heightPixels;

    /**
     屏幕（倍数） 与标准 160dpi相比
     */
    public float density;

    /**
     屏幕（dpi） 每英寸像素点
     */
    public int densityDpi;

    public float scaledDensity;

    /**
     屏幕宽（dpi）
     */
    public float xdpi;
    /**
     屏幕高（dpi）
     */
    public float ydpi;


    public String drawableStr;


    /**
     * 表示屏幕宽尺寸（inch）
     * 单位 英寸
     */
    public float screenSizeWidth;
    /**
     * 表示屏幕高尺寸（inch）
     * 单位 英寸
     */
    public float screenSizeHeight;
    /**
     * 表示屏幕大小尺寸（inch）
     * 单位 英寸
     */
    public  float screenSize;
    //物理像素

    public  float dipW;
    public float dipH ;
    public float dipWidth;
    public float dipHeigh;

    public String suggestionLayout;
    public String suggestionLayoutLand;
    public String suggestionLayoutSimple;
    public String suggestionValues;
    public String suggestionValuesLand;
    public String suggestionValuesSimple;

    public int statusBaHheight;
    public int navigationBarHeight;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("本手机的屏幕信息：").append("density=").append(density)
                .append(",densityDpi=").append(densityDpi)
                .append(",heightPixels=").append(heightPixels)
                .append(",widthPixels=").append(widthPixels)
                .append(",scaledDensity=").append(scaledDensity)
                .append(",xdpi=").append(xdpi)
                .append(",ydpi=").append(ydpi)
                .append(",drawalbe=").append(drawableStr);
        result.append("，drawable=");
        return super.toString();
    }
}
