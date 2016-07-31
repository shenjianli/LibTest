// (c)2016 Flipboard Inc, All Rights Reserved.

package com.shenjianli.lib.engine.rxjava.network.api;


import com.shenjianli.lib.engine.rxjava.model.GankBeautyResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
