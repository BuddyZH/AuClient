package com.crazyit.auction.client;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

public class MyAdapter extends AuthorizeAdapter {
	public void onCreate() {
		// ���ر������Ҳ���ShareSDK Logo
		hideShareSDKLogo();
	}
}
