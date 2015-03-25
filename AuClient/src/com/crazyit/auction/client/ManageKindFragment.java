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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class ManageKindFragment extends Fragment {
	public static final int ADD_KIND = 0x1007;
	Button bnHome,bnAdd;
	ListView kindList;
	Callbacks mCallbacks;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.manage_kind, container,false);
		bnHome = (Button) rootView.findViewById(R.id.bn_home);
		bnAdd = (Button) rootView.findViewById(R.id.bnAdd);
		kindList = (ListView) rootView.findViewById(R.id.kindList);
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		bnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//������ӡ���ť������ʱ�����ø�Fragment����Activity��onItemSelected����
				mCallbacks.onItemSelected(ADD_KIND, null);
			}
		});
		String url = HttpUtil.BASE_URL + "viewKind.jsp";
		try{
			// ��ָ��URL�������󣬲�����Ӧ��װ��JSONArray����
			final JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(url));
			// ��JSONArray�����װ��Adapters
			kindList.setAdapter(new KindArrayAdapter(jsonArray, getActivity()));
		}catch(Exception e){
			DialogUtil.showDialog(getActivity(), "��������Ӧ�쳣�����Ժ�����", false);
			e.printStackTrace();
		}
		return rootView;
	}
	// ����Fragment����ӡ���ʾ��Activityʱ���ص��÷���
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// ���Activityû��ʵ��Callbacks�ӿڣ��׳��쳣
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException(
				"ManageKindFragment���ڵ�Activity����ʵ��Callbacks�ӿ�!");
		}
		// �Ѹ�Activity����Callbacks����
		mCallbacks = (Callbacks) activity;
	}
	// ����Fragment����������Activity�б�ɾ��ʱ�ص��÷���
	@Override
	public void onDetach() {
		super.onDetach();
		// ��mCallbacks��Ϊnull
		mCallbacks = null;
	}
}
