package com.shenjianli.lib.app.engine.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shenjianli.lib.R;
import com.shenjianli.shenlib.ScreenInfo;
import com.shenjianli.shenlib.util.ScreenUtils;

/**
 * Android Developer Document
 * https://developer.android.com/reference/android/util/DisplayMetrics.html
 */
public class ScreenActivity extends AppCompatActivity {

    private TextView mDIPHeight;
    private TextView mDIPWidth;
    private TextView mDPIHeight;
    private TextView mDPIWidth;
    private TextView mDensityDpi;
    private TextView mDesity;
    private TextView mResolutionHeight;
    private TextView mResolutionWidth;
    private TextView mScreenSizeHeight;
    private TextView mScreenSize;
    private TextView mScreenSizeWidth;
    private TextView mSuggestionLayout;
    private TextView mSuggestionLayoutLand;
    private TextView mSuggestionLayoutSimple;
    private TextView mSuggestionValues;
    private TextView mSuggestionValuesLand;
    private TextView mSuggestionValuesSimple;
    private TextView mStatusBaHheight;
    private TextView mNavigationBarHeight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        this.mScreenSizeWidth = (TextView) findViewById(R.id.screen_size_width);
        this.mScreenSizeHeight = (TextView) findViewById(R.id.screen_size_height);
        this.mScreenSize = (TextView) findViewById(R.id.screen_size);
        this.mResolutionWidth = (TextView) findViewById(R.id.resolution_width);
        this.mResolutionHeight = (TextView) findViewById(R.id.resolution_height);
        this.mDPIWidth = (TextView) findViewById(R.id.dpi_width);
        this.mDPIHeight = (TextView) findViewById(R.id.dpi_height);
        this.mDensityDpi = (TextView) findViewById(R.id.density_dpi);
        this.mDesity = (TextView) findViewById(R.id.density);
        this.mDIPWidth = (TextView) findViewById(R.id.dip_width);
        this.mDIPHeight = (TextView) findViewById(R.id.dip_height);
        this.mSuggestionLayout = (TextView) findViewById(R.id.suggestion_layout);
        this.mSuggestionLayoutLand = (TextView) findViewById(R.id.suggestion_layout_land);
        this.mSuggestionLayoutSimple = (TextView) findViewById(R.id.suggestion_layout_simple);
        this.mSuggestionValues = (TextView) findViewById(R.id.suggestion_values);
        this.mSuggestionValuesLand = (TextView) findViewById(R.id.suggestion_values_land);
        this.mSuggestionValuesSimple = (TextView) findViewById(R.id.suggestion_values_simple);
        this.mStatusBaHheight = (TextView) findViewById(R.id.status_bar_height);
        this.mNavigationBarHeight = (TextView) findViewById(R.id.navigation_bar_height);

        /**
         *  A structure describing general information about a display,
         *  such as its size, density, and font scaling.
         */

        ScreenInfo screenInfo = ScreenUtils.getScreenInfo(this);

        //获取屏幕尺寸
        this.mScreenSizeWidth.setText(String.valueOf(screenInfo.screenSizeWidth));
        this.mScreenSizeHeight.setText(String.valueOf(screenInfo.screenSizeHeight));
        this.mScreenSize.setText(String.valueOf(screenInfo.screenSize));
        //获取屏幕分辨率
        this.mResolutionWidth.setText(String.valueOf(screenInfo.widthPixels));
        this.mResolutionHeight.setText(String.valueOf(screenInfo.heightPixels));
        //物理像素
        this.mDPIWidth.setText(String.valueOf(screenInfo.xdpi));
        this.mDPIHeight.setText(String.valueOf(screenInfo.ydpi));
        //The screen density expressed as dots-per-inch.
        this.mDensityDpi.setText(String.valueOf(screenInfo.densityDpi) + screenInfo.drawableStr);

        this.mDesity.setText(String.valueOf(screenInfo.density));

        this.mDIPWidth.setText(String.valueOf(screenInfo.dipW));
        this.mDIPHeight.setText(String.valueOf(screenInfo.dipH));

        this.mSuggestionLayout.setText(screenInfo.suggestionLayout);
        this.mSuggestionLayoutLand.setText(screenInfo.suggestionLayoutLand);
        this.mSuggestionLayoutSimple.setText(screenInfo.suggestionLayoutSimple);
        this.mSuggestionValues.setText(screenInfo.suggestionValues);
        this.mSuggestionValuesLand.setText(screenInfo.suggestionValuesLand);
        this.mSuggestionValuesSimple.setText(screenInfo.suggestionValuesSimple);

        this.mStatusBaHheight.setText(String.valueOf(screenInfo.statusBaHheight));
        this.mNavigationBarHeight.setText(String.valueOf(screenInfo.navigationBarHeight));

    }
}
