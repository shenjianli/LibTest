package com.shenjianli.shenlib.base;

public interface MvpView {
    void startLoading();
    void hideLoading();
    void showError(String msg);
}