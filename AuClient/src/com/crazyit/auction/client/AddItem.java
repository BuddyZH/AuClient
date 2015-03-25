package com.crazyit.auction.client;

import android.app.Fragment;

import com.crazyit.app.base.FragmentActivity;

public class AddItem extends FragmentActivity {

	@Override
	protected Fragment getFragment() {
		return new AddItemFragment();
	}

}
