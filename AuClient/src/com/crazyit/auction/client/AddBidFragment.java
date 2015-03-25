package com.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddBidFragment extends Fragment {
	TextView itemName, itemDesc, itemRemark, itemKind,initPrice,maxPrice,endTime;
	EditText bidPrice;
	Button bnAdd,bnCancel;
	JSONObject jsonObj;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_bid, container, false);
		itemName = (TextView) rootView.findViewById(R.id.itemName);
		itemDesc = (TextView) rootView.findViewById(R.id.itemDesc);
		itemRemark = (TextView) rootView.findViewById(R.id.itemRemark);
		itemKind = (TextView) rootView.findViewById(R.id.itemKind);
		initPrice = (TextView) rootView.findViewById(R.id.initPrice);
		maxPrice = (TextView) rootView.findViewById(R.id.maxPrice);
		endTime = (TextView) rootView.findViewById(R.id.endTime);
		bidPrice = (EditText) rootView.findViewById(R.id.bidPrice);
		bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
		bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
		bnCancel.setOnClickListener(new HomeListener(getActivity()));
		
		String url = HttpUtil.BASE_URL + "getItem.jsp?itemId=" + getArguments().getInt("itemId");
		try{
			jsonObj = new JSONObject(HttpUtil.getRequest(url));
			itemName.setText(jsonObj.getString("name"));
			itemDesc.setText(jsonObj.getString("desc"));
			itemRemark.setText(jsonObj.getString("remark"));
			itemKind.setText(jsonObj.getString("kind"));
			initPrice.setText(jsonObj.getString("initPrice"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			endTime.setText(jsonObj.getString("endTime"));
		}catch(Exception e){
			DialogUtil.showDialog(getActivity(), "服务器响应出现异常", false);
			e.printStackTrace();
		}
		bnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					//数据类型转换
					double curPrice = Double.parseDouble(bidPrice.getText().toString());
					//输入校验
					if(curPrice < jsonObj.getDouble("maxPrice")){
						DialogUtil.showDialog(getActivity(), "您输入的竞价必须高于当前竞价", false);
					}else{
						String result = addBid(jsonObj.getString("id"),curPrice + "");
						DialogUtil.showDialog(getActivity(), result, true);
					}
				}catch(NumberFormatException ne){
					DialogUtil.showDialog(getActivity(), "您输入的竞价必须是数值", false);
				}
				catch(Exception e){
					e.printStackTrace();
					DialogUtil.showDialog(getActivity(), "服务器响应出现异常，请重试", false);
				}
			}
		});
		return rootView;
	}
	private String addBid(String itemId, String bidPrice)
		throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("itemId", itemId);
		map.put("bidPrice", bidPrice);
		String url = HttpUtil.BASE_URL + "addBid.jsp";
		return HttpUtil.postRequest(url, map);
	}

}
