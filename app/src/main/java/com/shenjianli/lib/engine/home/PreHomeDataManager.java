package com.shenjianli.lib.engine.home;

import com.shenjianli.lib.engine.home.bean.ChoicenessData;
import com.shenjianli.lib.engine.home.bean.PlatformData;
import com.shenjianli.lib.engine.home.bean.ProjectData;
import com.shenjianli.lib.engine.home.bean.SecKillData;
import com.shenjianli.lib.engine.home.bean.SlideData;
import com.shenjianli.lib.engine.rxjava.model.Item;
import com.shenjianli.shenlib.net.NetClient;
import com.shenjianli.shenlib.util.LogUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PreHomeDataManager {

	private final String TAG = this.getClass().getSimpleName();
	
	private SlideData mSlideData;
	private PlatformData mPlatformData;
	private SecKillData mSecKillData;
	private ProjectData mProjectData;
	private Item mItem;
	private ChoicenessData mChoicenessData;
	
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

		homeService.getSlideData().map(new HttpResultFunc<SlideData>())
					.subscribeOn(Schedulers.io())
					.unsubscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<SlideData>() {
						@Override
						public void onCompleted() {

						}

						@Override
						public void onError(Throwable e) {

						}

						@Override
						public void onNext(SlideData slideData) {

						}
					});


		homeService.getPlatformData().map(new HttpResultFunc<PlatformData>())
		.subscribeOn(Schedulers.io())
		.unsubscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(new Subscriber<PlatformData>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(PlatformData platformData) {

			}
		});
		homeService.getSecKillData().map(new HttpResultFunc<SecKillData>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<SecKillData>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(SecKillData secKillData) {

					}
				});


		homeService.getThemeData()
				.map(new HttpResultFunc<ProjectData>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<ProjectData>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(ProjectData projectData) {

					}
			});


		homeService.getItemData()
				.map(new HttpResultFunc<Item>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<Item>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(Item item) {

					}
				});
		homeService.getChoicenessData("1")
				.map(new HttpResultFunc<ChoicenessData>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<ChoicenessData>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(ChoicenessData choicenessData) {

					}
				});

	}

	public void clear(){
		LogUtils.i( TAG + " 预加载  取消请求   清空数据");
		cancelSlide();
		cancelPlatform();
		cancelSecKill();
		cancelTheme();
		cancelItem();
		cancelChoiceness();
		mSlideData = null;
		mPlatformData = null;
		mSecKillData = null;
		mItem = null;
		mProjectData = null;
		mChoicenessData = null;

	}

	public void cancelChoiceness() {
//		if(null != choicenessData){
//			choicenessData.cancel();
//			choicenessData = null;
//		}
	}

	public void cancelItem() {
//		if(null != itemData){
//			itemData.cancel();
//			itemData = null;
//		}
	}

	public void cancelTheme() {
//		if(null != themeData){
//			themeData.cancel();
//			themeData = null;
//		}
	}

	public void cancelSecKill() {
//		if(null != secKillData){
//			secKillData.cancel();
//			secKillData = null;
//		}
	}

	public void cancelPlatform() {
//		if(null != platformData){
//			platformData.cancel();
//			platformData = null;
//		}
	}

	public void cancelSlide() {
//		if(null != slideData){
//			slideData.cancel();
//		}
	}

	public SlideData getSlideData() {
		return mSlideData;
	}

	public void setSlideData(SlideData mSlideData) {
		this.mSlideData = mSlideData;
	}

	public PlatformData getPlatformData() {
		return mPlatformData;
	}

	public void setPlatformData(PlatformData mPlatformData) {
		this.mPlatformData = mPlatformData;
	}

	public SecKillData getmSecKillData() {
		return mSecKillData;
	}

	public void setSecKillData(SecKillData mSecKillData) {
		this.mSecKillData = mSecKillData;
	}

	public ProjectData getProjectData() {
		return mProjectData;
	}

	public void setProjectData(ProjectData mProjectData) {
		this.mProjectData = mProjectData;
	}

	public Item getItem() {
		return mItem;
	}

	public void setItem(Item mItem) {
		this.mItem = mItem;
	}

	public ChoicenessData getChoicenessData() {
		return mChoicenessData;
	}

	public void setChoicenessData(ChoicenessData mChoicenessData) {
		this.mChoicenessData = mChoicenessData;
	}

}
