package com.shenjianli.lib.engine.home;

import com.shenjianli.lib.engine.home.bean.ChoicenessRes;
import com.shenjianli.lib.engine.home.bean.ItemRes;
import com.shenjianli.lib.engine.home.bean.PlatformRes;
import com.shenjianli.lib.engine.home.bean.ProjectRes;
import com.shenjianli.lib.engine.home.bean.SecKillRes;
import com.shenjianli.lib.engine.home.bean.SlideRes;
import com.shenjianli.shenlib.net.NetClient;
import com.shenjianli.shenlib.net.RetrofitCallback;
import com.shenjianli.shenlib.util.LogUtils;

import retrofit2.Call;


public class PreHomeDataManager {

	private final String TAG = this.getClass().getSimpleName();
	
	private Call<SlideRes> slideData;
	private Call<PlatformRes> platformData;
	private Call<SecKillRes> secKillData;
	private Call<ProjectRes> themeData;
	private Call<ItemRes> itemData;
	private Call<ChoicenessRes> choicenessData;

	private SlideRes mSlideRes;
	private PlatformRes mPlatformRes;
	private SecKillRes mSecKillRes;
	private ProjectRes mProjectRes;
	private ItemRes mItemRes;
	private ChoicenessRes mChoicenessRes;
	
	private PreHomeDataManager() {

	}

	private static PreHomeDataManager mPreHomeDataManager = new PreHomeDataManager();

	public static synchronized PreHomeDataManager getPreHomeDataManager() {
		if (null == mPreHomeDataManager) {
			mPreHomeDataManager = new PreHomeDataManager();
		}
		return mPreHomeDataManager;
	}

	public void startPreLoadDataOfHome() {
		getHomeData();
	}
	
	private void getHomeData() {
		
		clear();
		LogUtils.i( TAG + "开始预加载首页数据");
		HomeService homeService = NetClient.retrofit().create(HomeService.class);

		slideData = homeService.getSlideData();
		platformData = homeService.getPlatformData();
		secKillData = homeService.getSecKillData();
		themeData = homeService.getThemeData();
		itemData = homeService.getItemData();
		choicenessData = homeService.getChoicenessData("1");
		
		slideData.enqueue(new RetrofitCallback<SlideRes>() {

			@Override
			public void onSuccess(SlideRes responseData) {
				setSlideRes(responseData);
				LogUtils.i( TAG + " 预加载---轮播图----请求成功");
			}

			@Override
			public void onFail(String errorMsg) {
				LogUtils.i( TAG + " 预加载---轮播图----请求失败:" + errorMsg);
				
			}
		});

		platformData.enqueue(new RetrofitCallback<PlatformRes>() {

			@Override
			public void onSuccess(PlatformRes responseData) {
				setmPlatformRes(responseData);
				LogUtils.i( TAG + " 预加载---快速入口----请求成功");
				
			}

			@Override
			public void onFail(String errorMsg) {
				LogUtils.i( TAG + " 预加载---快速入口----快速入口:" + errorMsg);
				
			}
		});

		secKillData.enqueue(new RetrofitCallback<SecKillRes>() {

			@Override
			public void onSuccess(SecKillRes responseData) {
				setSecKillRes(responseData);
				LogUtils.i( TAG + " 预加载---秒杀---请求成功");

			}

			@Override
			public void onFail(String errorMsg) {
				LogUtils.i( TAG + " 预加载---秒杀---请求失败:" + errorMsg);

			}
		});
		themeData.enqueue(new RetrofitCallback<ProjectRes>() {

			@Override
			public void onSuccess(ProjectRes responseData) {
				setProjectRes(responseData);
				LogUtils.i( TAG + " 预加载---专题---请求成功");

			}

			@Override
			public void onFail(String errorMsg) {

				LogUtils.i( TAG + " 预加载---专题---请求失败:" + errorMsg);
			}
		});

		itemData.enqueue(new RetrofitCallback<ItemRes>() {

			@Override
			public void onSuccess(ItemRes responseData) {
				setItemRes(responseData);
				LogUtils.i( TAG + " 预加载---行业精选---请求成功");

			}

			@Override
			public void onFail(String errorMsg) {

				LogUtils.i( TAG + " 预加载---行业精选---请求失败:" + errorMsg);
			}
		});

		choicenessData.enqueue(new RetrofitCallback<ChoicenessRes>() {

			@Override
			public void onSuccess(ChoicenessRes responseData) {
				setChoicenessRes(responseData);
				LogUtils.i( TAG + " 预加载---热卖商品---请求成功");
			}

			@Override
			public void onFail(String errorMsg) {

				LogUtils.i( TAG + " 预加载---热卖商品---请求失败:" + errorMsg);
			}
		});
	}

	public SlideRes getSlideRes() {
		return mSlideRes;
	}

	public void setSlideRes(SlideRes mSlideRes) {
		this.mSlideRes = mSlideRes;
	}

	public PlatformRes getPlatformRes() {
		return mPlatformRes;
	}

	public void setmPlatformRes(PlatformRes mPlatformRes) {
		this.mPlatformRes = mPlatformRes;
	}

	public SecKillRes getSecKillRes() {
		return mSecKillRes;
	}

	public void setSecKillRes(SecKillRes mSecKillRes) {
		this.mSecKillRes = mSecKillRes;
	}

	public ProjectRes getProjectRes() {
		return mProjectRes;
	}

	public void setProjectRes(ProjectRes mProjectRes) {
		this.mProjectRes = mProjectRes;
	}

	public ItemRes getItemRes() {
		return mItemRes;
	}

	public void setItemRes(ItemRes mItemRes) {
		this.mItemRes = mItemRes;
	}

	public ChoicenessRes getChoicenessRes() {
		return mChoicenessRes;
	}

	public void setChoicenessRes(ChoicenessRes mChoicenessRes) {
		this.mChoicenessRes = mChoicenessRes;
	}

	public void clear(){
		LogUtils.i( TAG + " 预加载  取消请求   清空数据");
		cancelSlide();
		cancelPlatform();
		cancelSecKill();
		cancelTheme();
		cancelItem();
		cancelChoiceness();
		mSlideRes = null;
		mPlatformRes = null;
		mSecKillRes = null;
		mProjectRes = null;
		mItemRes = null;
		mChoicenessRes = null;
	}

	public void cancelChoiceness() {
		if(null != choicenessData){
			choicenessData.cancel();
			choicenessData = null;
		}
	}

	public void cancelItem() {
		if(null != itemData){
			itemData.cancel();
			itemData = null;
		}
	}

	public void cancelTheme() {
		if(null != themeData){
			themeData.cancel();
			themeData = null;
		}
	}

	public void cancelSecKill() {
		if(null != secKillData){
			secKillData.cancel();
			secKillData = null;
		}
	}

	public void cancelPlatform() {
		if(null != platformData){
			platformData.cancel();
			platformData = null;
		}
	}

	public void cancelSlide() {
		if(null != slideData){
			slideData.cancel();
		}
	}
}
