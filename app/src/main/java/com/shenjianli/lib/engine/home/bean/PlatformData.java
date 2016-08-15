package com.shenjianli.lib.engine.home.bean;

import java.util.List;

public class PlatformData {
	
	private List<ProductBean> Platform;
	private String moreImgUrl;
	private String gesturePwdOpenFlag;

	public List<ProductBean> getPlatform() {
		return Platform;
	}

	public void setPlatform(List<ProductBean> platform) {
		Platform = platform;
	}

	public String getMoreImgUrl() {
		return moreImgUrl;
	}

	public void setMoreImgUrl(String moreImgUrl) {
		this.moreImgUrl = moreImgUrl;
	}

	public String getGesturePwdOpenFlag() {
		return gesturePwdOpenFlag;
	}

	public void setGesturePwdOpenFlag(String gesturePwdOpenFlag) {
		this.gesturePwdOpenFlag = gesturePwdOpenFlag;
	}
}
