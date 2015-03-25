package com.crazyit.auction.client;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.crazyit.app.base.FragmentActivity;

public class ChooseKind extends FragmentActivity
	implements Callbacks
{
	@Override
	protected Fragment getFragment() {
		return new ChooseKindFragment();
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle) {
		Intent intent = new Intent(this, ChooseItem.class);
		intent.putExtra("kindId", bundle.getLong("kindId"));
		startActivity(intent);
	}

}
