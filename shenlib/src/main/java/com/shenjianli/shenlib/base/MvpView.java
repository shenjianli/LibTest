package com.shenjianli.shenlib.base;

import android.view.View;

public interface MvpView {
    void startLoading();
    void hideLoading();
    void showError(String msg, View.OnClickListener onClickListener);
}