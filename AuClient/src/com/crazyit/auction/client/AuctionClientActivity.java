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
		//���������Ʒ
		case 0:
			intent = new Intent(this,ChooseKind.class);
			startActivity(intent);
			break;
	    //���������Ʒ
		case 1:
			intent = new Intent(this,ViewItem.class);
			intent.putExtra("action", "viewFail.jsp");//action����Ϊ�����Servlet��URL��ʹViewItem.java֪����������Ŀ��ת
			startActivity(intent);
			break;
	    //������Ʒ����
		case 2:
			intent = new Intent(this,ManageKind.class);
			startActivity(intent);
			break;
		//������Ʒ
		case 3:
			intent = new Intent(this,ManageItem.class);
			startActivity(intent);
			break;
		//�鿴������Ʒ
		case 4:
			intent = new Intent(this,ViewItem.class);
			intent.putExtra("action", "viewSucc.jsp");
			startActivity(intent);
			break;
		//�鿴�Լ��ľ���
		case 5:
			intent = new Intent(this,ViewBid.class);
			startActivity(intent);
			break;
		}
			
		
	}

}
