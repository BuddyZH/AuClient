package com.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddKindFragment extends Fragment {
	EditText kindName, kindDesc;
	Button bnAdd, bnCancel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_kind, container, false);
		kindName = (EditText) rootView.findViewById(R.id.kindName);
		kindDesc = (EditText) rootView.findViewById(R.id.kindDesc);
		bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
		bnCancel = (Button) rootView.findViewById(R.id.bnCancel);
		bnCancel.setOnClickListener(new HomeListener(getActivity()));
		bnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					String name = kindName.getText().toString();
					String desc = kindDesc.getText().toString();
					try {
						// �����Ʒ����
						String result = addKind(name, desc);
						//ʹ�öԻ�����ʾ��ӽ��
						DialogUtil.showDialog(getActivity(), result, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});

		return rootView;
	}

	// ���û�������������ƽ���У��
	private boolean validate() {
		String name = kindName.getText().toString().trim();
		if (name.equals("")) {
			DialogUtil.showDialog(getActivity(), "���������Ǳ����", false);
			return false;
		}
		return true;
	}
	
	private String addKind(String name, String desc)
		throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		map.put("kindName", name);
		map.put("kindDesc", desc);
		String url = HttpUtil.BASE_URL + "addKind.jsp";
		return HttpUtil.postRequest(url, map);
	}

}
