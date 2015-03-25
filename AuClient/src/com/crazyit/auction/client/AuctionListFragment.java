package com.crazyit.auction.client;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

//主页面的listView布局加载
public class AuctionListFragment extends Fragment {
	ListView auctionList;
	private Callbacks mCallbacks;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.au_list, container, false);
		auctionList = (ListView) rootView.findViewById(R.id.au_list); //auction_list
		// 为ListView的列表项的单击事件绑定事件监听器
		auctionList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				mCallbacks.onItemSelected(position, null);
			}
				
			});
		return rootView;
	}
	// 当该Fragment被添加、显示到Activity时，回调该方法
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// 如果Activity没有实现Callbacks接口，抛出异常
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException(
				"AuctionListFragment所在的Activity必须实现Callbacks接口!");
		}
		// 把该Activity当成Callbacks对象
		mCallbacks = (Callbacks) activity;
	}
	// 当该Fragment从它所属的Activity中被删除时回调该方法
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		// 将mCallbacks赋为null。
		mCallbacks = null;
	}
	public void setActivateOnItemClick(boolean activateOnItemClick)
	{
		auctionList.setChoiceMode(activateOnItemClick 
			? ListView.CHOICE_MODE_SINGLE
			: ListView.CHOICE_MODE_NONE);
	}

}
