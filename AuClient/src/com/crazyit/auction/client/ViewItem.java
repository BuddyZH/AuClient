package com.crazyit.auction.client;

import android.app.Fragment;
import android.os.Bundle;

import com.crazyit.app.base.FragmentActivity;

public class ViewItem extends FragmentActivity{
	@Override
	protected Fragment getFragment() {
		ViewItemFragment fragment = new ViewItemFragment();
		Bundle arguments = new Bundle();
		arguments.putString("action",getIntent().getStringExtra("action"));
		fragment.setArguments(arguments);
		return fragment;
	}
}
