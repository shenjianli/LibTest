package com.shenjianli.lib.test;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by shenjianlis on 2016/8/31.
 */
public interface TestApi {

    @GET("shenjianli/test")
    Call<Test> getTestData(@Query("id") String id);

}
