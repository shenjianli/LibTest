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
	
	
	private Subscriber<ChoicenessData> choicenessSubscriber;
	private Subscriber<Item> itemSubscriber;
	private Subscriber<ProjectData> projectSubscriber;
	private Subscriber<SecKillData> secKillSubscriber;
	private Subscriber<PlatformData> platformSubscriber;
	private Subscriber<SlideData> slideSubscriber;

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
		initSubscriber();
		LogUtils.i( TAG + "开始预加载首页数据");
		HomeService homeService = NetClient.retrofit().create(HomeService.class);


		homeService.getSlideData().map(new HttpResultFunc<SlideData>())
					.subscribeOn(Schedulers.io())
					.unsubscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(slideSubscriber);


		homeService.getPlatformData().map(new HttpResultFunc<PlatformData>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(platformSubscriber);


		homeService.getSecKillData().map(new HttpResultFunc<SecKillData>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(secKillSubscriber);


		homeService.getThemeData()
				.map(new HttpResultFunc<ProjectData>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(projectSubscriber);


		homeService.getItemData()
				.map(new HttpResultFunc<Item>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(itemSubscriber);


		homeService.getChoicenessData("1")
				.map(new HttpResultFunc<ChoicenessData>())
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(choicenessSubscriber);

	}


	private void initSubscriber() {

		slideSubscriber = new Subscriber<SlideData>() {
			@Override
			public void onCompleted() {
				LogUtils.i("表求完成");
			}

			@Override
			public void onError(Throwable e) {
				LogUtils.i(e.getStackTrace().toString());
			}

			@Override
			public void onNext(SlideData slideData) {
				LogUtils.i("获取轮播区数据成功");
				setSlideData(slideData);
			}
		};

		platformSubscriber = new Subscriber<PlatformData>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(PlatformData platformData) {
				LogUtils.i("获取快速入口数据成功");
				setPlatformData(platformData);
			}
		};

		secKillSubscriber = new Subscriber<SecKillData>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(SecKillData secKillData) {
				LogUtils.i("获取秒杀数据成功");
				setSecKillData(secKillData);
			}
		};

		projectSubscriber = new Subscriber<ProjectData>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(ProjectData projectData) {
				LogUtils.i("获取专题数据成功");
				setProjectData(projectData);
			}
		};

		itemSubscriber = new Subscriber<Item>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Item item) {
				LogUtils.i("获取精品推荐数据成功");
				setItem(item);
			}
		};
		choicenessSubscriber = new Subscriber<ChoicenessData>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(ChoicenessData choicenessData) {
				LogUtils.i("获取热卖商品数据成功");
				setChoicenessData(choicenessData);
			}
		};
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
		if(null != choicenessSubscriber){
			choicenessSubscriber.unsubscribe();
			choicenessSubscriber = null;
		}
	}

	public void cancelItem() {
		if(null != itemSubscriber){
			itemSubscriber.unsubscribe();
			itemSubscriber = null;
		}
	}

	public void cancelTheme() {
		if(null != projectSubscriber){
			projectSubscriber.unsubscribe();
			projectSubscriber = null;
		}
	}

	public void cancelSecKill() {
		if(null != secKillSubscriber){
			secKillSubscriber.unsubscribe();
			secKillSubscriber = null;
		}
	}

	public void cancelPlatform() {
		if(null != platformSubscriber){
			platformSubscriber.unsubscribe();
			platformSubscriber = null;
		}
	}

	public void cancelSlide() {
		if(null != slideSubscriber){
			slideSubscriber.unsubscribe();
			slideSubscriber = null;
		}
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
