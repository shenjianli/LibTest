package com.shenjianli.lib.engine.rxandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
/**
 * Created by shenjianli on 16/7/29.
 */
public interface MovieService {

    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<MovieEntity>  getTopRxAndroidMovie(@Query("start") int start, @Query("count") int count);


    @GET("top250")
    Observable<HttpResultTest<List<MovieEntity.SubjectsBean>>>  getTopRxAndroidMovieHttpResult(@Query("start") int start, @Query("count") int count);
}
