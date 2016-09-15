package com.shenjianli.lib.test;

import android.content.Context;

import com.shenjianli.shenlib.base.BasePresenter;
import com.shenjianli.shenlib.net.NetClient;
import com.shenjianli.shenlib.net.RetrofitCallback;

import retrofit2.Call;

/**
 * Created by edianzu on 2016/9/12.
 */
public class TestPresenter extends BasePresenter<DataView> {

    Context context;

    Test test;

    public TestPresenter(Context context){
        this.context = context;
    }
    @Override    public void attachView(DataView mvpView) {
        super.attachView(mvpView);
    }
    @Override    public void detachView() {
        super.detachView();
    }

    public void loadData(){
        //在这里写网络请求的代码，从Server中请求到JavaBena UsBoxEntity
        //调用MvpView中的loadData方法，将请求到的数据传回View层，让View层更新数据；
       getMvpView().startLoading();
        TestApi api = NetClient.retrofit().create(TestApi.class);
        Call<Test> data = api.getTestData("001");
        data.enqueue(new RetrofitCallback<Test>() {
            @Override
            public void onSuccess(Test data) {
                getMvpView().loadData(test);
                getMvpView().hideLoading();
            }

            @Override
            public void onFail(String errorMsg) {
                getMvpView().showError(errorMsg);
                getMvpView().hideLoading();
            }
        });

    }

}
