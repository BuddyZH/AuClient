package com.crazyit.auction.client;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.crazyit.app.base.FragmentActivity;

public class ManageItem extends FragmentActivity implements Callbacks{
	@Override
	protected Fragment getFragment() {
		return new ManageItemFragment();
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle) {
		Intent i = new Intent(this , AddItem.class);
		startActivity(i);
	}
}
