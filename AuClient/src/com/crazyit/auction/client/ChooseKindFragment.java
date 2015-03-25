package com.crazyit.auction.client;

import com.crazyit.auction.client.Callbacks;
import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ChooseKindFragment extends Fragment {
	public static final int CHOOSE_ITEM = 0x1008;
	Callbacks mCallbacks;
	Button bnHome;
	ListView kindList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.choose_kind, container,false);
		bnHome = (Button) rootView.findViewById(R.id.bn_home);
		kindList = (ListView) rootView.findViewById(R.id.kindList);
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		try{
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			kindList.setAdapter(new KindArrayAdapter(jsonArray, getActivity()));
		}catch(Exception e){
			DialogUtil.showDialog(getActivity(), "服务器异常，请稍后再试",false);
			e.printStackTrace();
		}
		kindList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int postion,
					long id) {
				Bundle bundle = new Bundle();
				bundle.putLong("kindId", id);
				mCallbacks.onItemSelected(CHOOSE_ITEM, bundle);
			}
		});
		return rootView;
		
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		super.onAttach(activity);
		// 如果Activity没有实现Callbacks接口，抛出异常
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException(
				"ManageKindFragment所在的Activity必须实现Callbacks接口!");
		}
		// 把该Activity当成Callbacks对象
		mCallbacks = (Callbacks) activity;
	}
	@Override
	public void onDetach() {
		super.onDetach();
		super.onDetach();
		// 将mCallbacks赋为null。
		mCallbacks = null; 
	}

}
