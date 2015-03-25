package com.crazyit.auction.client;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.crazyit.app.base.FragmentActivity;

public class ManageKind extends FragmentActivity
	implements Callbacks
{
	@Override
	public Fragment getFragment() {
		return new ManageKindFragment();
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle) {
		Intent i = new Intent(this,AddKind.class);
		startActivity(i);
	}

}
