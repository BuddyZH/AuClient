package com.crazyit.auction.client;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ViewBidFragment extends Fragment {
	Button bnHome;
	ListView bidList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.view_bid, container,false);
		bnHome = (Button) rootView.findViewById(R.id.bn_home);
		bidList = (ListView) rootView.findViewById(R.id.bidList);
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		String url = HttpUtil.BASE_URL + "viewBid.jsp";
		try{
			//向指定URL发送请求，并把服务器响应包装成JSONArray对象
			JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			//将JSONArray包装成adapter
			JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity(), jsonArray, "item", true);
			bidList.setAdapter(adapter);
			
		}catch(Exception e){
			DialogUtil.showDialog(getActivity(),"服务器响应异常，请稍后再试", true);
			e.printStackTrace();
		}
		bidList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				viewBidDetail(position);
			}
		});
		return rootView;
	}
	private void viewBidDetail(int position){
		View detailView = getActivity().getLayoutInflater()
				.inflate(R.layout.bid_detail, null);
		TextView itemName = (TextView) detailView.findViewById(R.id.itemName);
		TextView bidPrice = (TextView) detailView.findViewById(R.id.bidPrice);
		TextView bidTime = (TextView) detailView.findViewById(R.id.bidTime);
		TextView bidUser = (TextView) detailView.findViewById(R.id.bidUser);
		
		JSONObject jsonObj = (JSONObject) bidList.getAdapter().getItem(position);
		try{
			itemName.setText(jsonObj.getString("item"));
			bidPrice.setText(jsonObj.getString("price"));
			bidTime.setText(jsonObj.getString("bidDate"));
			bidUser.setText(jsonObj.getString("user"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		DialogUtil.showDialog(getActivity(), detailView);
	}

}
