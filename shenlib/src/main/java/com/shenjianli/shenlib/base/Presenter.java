package com.shenjianli.shenlib.base;

/**
 * Created by toryang on 16/4/27.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

}
