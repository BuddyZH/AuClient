package com.crazyit.auction.client;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseItemFragment extends Fragment {
	public static final int ADD_BID = 0x1009;
	Button bnHome;
	ListView succList;
	TextView viewTitle;
	Callbacks mCallbacks;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_item
				, container, false);
		bnHome = (Button)rootView.findViewById(R.id.bn_home);
		succList = (ListView)rootView.findViewById(R.id.succList);
		viewTitle = (TextView)rootView.findViewById(R.id.view_title);
		viewTitle.setText(R.string.item_list);
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		long kindId = getArguments().getLong("kindId");
		String url = HttpUtil.BASE_URL + "itemList.jsp?kindId=" + kindId;
		try{
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(),jsonArray,"name",true);
			succList.setAdapter(adapter);
		}catch(Exception e){
			DialogUtil.showDialog(getActivity(), "服务器响应异常，请稍后重试", false);
			e.printStackTrace();
		}
		succList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				JSONObject jsonObj = (JSONObject) succList.getAdapter().getItem(position);
				Bundle bundle = new Bundle();
				try{
					bundle.putInt("itemId", jsonObj.getInt("id"));
				}catch(Exception e){
					e.printStackTrace();
				}
				mCallbacks.onItemSelected(ADD_BID, bundle);
				
			}
		});
		return rootView;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(!(activity instanceof Callbacks)){
			throw new IllegalStateException("ManagerItemFragment所在Activity必须实现Callbacks接口");
		}
		mCallbacks = (Callbacks) activity;
	}
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
}
