package com.shenjianli.lib.engine.retrofit.net.service;

import com.shenjianli.lib.engine.retrofit.data.LoginRequest;
import com.shenjianli.lib.engine.retrofit.data.entity.UserEntity;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created on 16/8/4.
 *
 * @author ice
 */
public interface UserService {

    @POST("/login")
    Observable<Response<UserEntity>> login(@Body LoginRequest loginRequest);

    /* == ↑ 同于上面 . 相当于把LoginRequest 里的参数摆放出来，效果是一样的 */
    @FormUrlEncoded
    @POST("/login")
    Observable<Response<UserEntity>> loginOtherWay(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("macAddress") String macAddress,
            @Field("ip") String ip
    );

    /* == 同于 @GET("/users/username")... */
    @GET("/users/{username}")
    Observable<UserEntity> getInfo(@Path("username") String username);

}
