package com.crazyit.auction.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AuctionClientActivity extends Activity 
	implements Callbacks 
{
//	private boolean mTwoPane;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle) {
		Intent intent = null;
		switch((int) id){
		//浏览拍卖物品
		case 0:
			intent = new Intent(this,ChooseKind.class);
			startActivity(intent);
			break;
	    //浏览流拍物品
		case 1:
			intent = new Intent(this,ViewItem.class);
			intent.putExtra("action", "viewFail.jsp");//action属性为请求的Servlet的URL，使ViewItem.java知道是哪条项目跳转
			startActivity(intent);
			break;
	    //管理物品种类
		case 2:
			intent = new Intent(this,ManageKind.class);
			startActivity(intent);
			break;
		//管理物品
		case 3:
			intent = new Intent(this,ManageItem.class);
			startActivity(intent);
			break;
		//查看竞得物品
		case 4:
			intent = new Intent(this,ViewItem.class);
			intent.putExtra("action", "viewSucc.jsp");
			startActivity(intent);
			break;
		//查看自己的竞标
		case 5:
			intent = new Intent(this,ViewBid.class);
			startActivity(intent);
			break;
		}
			
		
	}

}
