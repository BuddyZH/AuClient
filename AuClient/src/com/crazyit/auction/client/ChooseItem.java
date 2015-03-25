package com.crazyit.auction.client;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.crazyit.app.base.FragmentActivity;

public class ChooseItem extends FragmentActivity 
	implements Callbacks{
	@Override
	protected Fragment getFragment() {
		ChooseItemFragment fragment = new ChooseItemFragment();
		Bundle args = new Bundle();
		args.putLong("kindId", getIntent().getLongExtra("kindId",-1));
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public void onItemSelected(Integer id, Bundle bundle) {
		Intent intent = new Intent(this, AddBid.class);
		intent.putExtra("itemId", bundle.getInt("itemId"));
		startActivity(intent);
		
	}

}
