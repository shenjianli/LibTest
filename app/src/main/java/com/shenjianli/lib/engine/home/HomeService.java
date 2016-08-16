package com.shenjianli.lib.engine.home;

import com.shenjianli.lib.engine.home.bean.ChoicenessData;
import com.shenjianli.lib.engine.home.bean.MallBean;
import com.shenjianli.lib.engine.home.bean.PlatformData;
import com.shenjianli.lib.engine.home.bean.ProjectData;
import com.shenjianli.lib.engine.home.bean.SecKillData;
import com.shenjianli.lib.engine.home.bean.SlideData;
import com.shenjianli.lib.engine.rxjava.model.Item;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface HomeService {

	@GET("mobile/indexSlide.jhtml")
	Observable<MallBean<SlideData>> getSlideData();
	
	@GET("mobile/indexPlatformNew.jhtml?flag=1")
	Observable<MallBean<PlatformData>> getPlatformData();
	
	@Headers("Cache-Control: no-store, max-age=0")
	@GET("mobile/indexSeckill.jhtml")
	Observable<MallBean<SecKillData>> getSecKillData();
	
	@GET("/mobile/indexTheme.jhtml")
	Observable<MallBean<ProjectData>> getThemeData();
	
	@GET("/mobile/indexIndustry.jhtml")
	Observable<MallBean<Item>> getItemData();
	
	@GET("/mobile/indexChoicenessNew.jhtml")
	Observable<MallBean<ChoicenessData>> getChoicenessData(@Query("indexpage") String indexpage);
	
	@GET("/recmd/prods.jhtml")
	Observable<ResponseBody> getRmdData(@Query(value = "param", encoded = true) String param, @QueryMap Map<String, String> options);


}
