// (c)2016 Flipboard Inc, All Rights Reserved.

package com.shenjianli.lib.engine.rxjava.network.api;

import com.shenjianli.lib.engine.rxjava.model.ZhuangbiImage;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ZhuangbiApi {
    @GET("search")
    Observable<List<ZhuangbiImage>> search(@Query("q") String query);
}
