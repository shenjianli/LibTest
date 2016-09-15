package com.shenjianli.lib.app.engine.retrofit.net.api;

import com.shenjianli.lib.app.engine.retrofit.data.LoginRequest;
import com.shenjianli.lib.app.engine.retrofit.data.entity.UserEntity;
import com.shenjianli.lib.app.engine.retrofit.net.service.UserService;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 16/8/2.
 *
 * @author ice
 */
public class UserApi extends BaseApi implements UserService {

    private UserService mService = getRetrofit().create(UserService.class);

    @Override
    public Observable<Response<UserEntity>> login(@Body LoginRequest loginRequest) {
        return mService.login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<UserEntity>> loginOtherWay(
            @Field("phone") String phone, @Field("password") String password,
            @Field("macAddress") String macAddress, @Field("ip") String ip) {
        return mService.loginOtherWay(phone, password, macAddress, ip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserEntity> getInfo(@Path("username") String username) {
        return mService.getInfo(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
