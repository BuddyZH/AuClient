package com.crazyit.auction.client;

import android.app.Fragment;
import android.os.Bundle;

import com.crazyit.app.base.FragmentActivity;

public class AddBid extends FragmentActivity {

	@Override
	protected Fragment getFragment() {
		AddBidFragment fragment = new AddBidFragment();
		Bundle args = new Bundle();
		args.putInt("itemId", getIntent().getIntExtra("itemId", -1));
		fragment.setArguments(args);
		return fragment;
	}

}
