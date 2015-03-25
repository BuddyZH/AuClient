package com.crazyit.auction.client;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.auction.client.util.DialogUtil;
import org.crazyit.auction.client.util.HttpUtil;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

public class Login extends Activity implements Callback, OnClickListener,
		PlatformActionListener {

	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_LOGIN = 2;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR = 4;
	private static final int MSG_AUTH_COMPLETE = 5;

	EditText etName, etPass;
	Button bnLogin, bnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ShareSDK.initSDK(this);

		setContentView(R.layout.login);
		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);
		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnCancel = (Button) findViewById(R.id.bnCancel);
		bnCancel.setOnClickListener(new HomeListener(this));
		bnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					if (loginPro()) {
						Intent intent = new Intent(Login.this,
								AuctionClientActivity.class);
						startActivity(intent);
						finish();
					} else {
						DialogUtil.showDialog(Login.this, "用户名称或密码错误，请重新输入！",
								false);
					}
				}

			}
		});

		findViewById(R.id.tvWeixin).setOnClickListener(this);
		findViewById(R.id.tvQq).setOnClickListener(this);
		findViewById(R.id.tvWeibo).setOnClickListener(this);
		findViewById(R.id.tvQzone).setOnClickListener(this);
	}

	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}

	// 检验登录是否成功
	private boolean loginPro() {
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		JSONObject jsonObj;
		try {
			jsonObj = query(username, pwd);
			if (jsonObj.getInt("userId") > 0) {
				return true;
			}
		} catch (Exception e) {
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试", false);
			e.printStackTrace();
		}
		return false;
	}

	// 对用户输入的用户名、密码进行校验
	private boolean validate() {
		String username = etName.getText().toString().trim();
		if (username.equals("")) {
			DialogUtil.showDialog(this, "用户账户是必填项", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();
		if (pwd.equals("")) {
			DialogUtil.showDialog(this, "用户口令是必填项", false);
			return false;
		}
		return true;
	}

	private JSONObject query(String username, String password) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		String url = HttpUtil.BASE_URL + "login.jsp";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onCancel(Platform arg0, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
		}
	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
			login(platform.getName(), platform.getDb().getUserId(), res);
		}
		System.out.println(res);
		System.out.println("------User Name ---------"
				+ platform.getDb().getUserName());
		System.out.println("------User ID ---------"
				+ platform.getDb().getUserId());

	}

	private void login(String plat, String userId,
			HashMap<String, Object> userInfo) {
		Message msg = new Message();
		msg.what = MSG_LOGIN;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
		}
		t.printStackTrace();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvWeixin: {
			authorize(new Wechat(this));
		}
			break;
		case R.id.tvQq: {
			authorize(new QQ(this));
		}
			break;
		case R.id.tvWeibo: {
			authorize(new SinaWeibo(this));
		}
			break;
		case R.id.tvQzone: {
			authorize(new QZone(this));
		}
			break;
		}
	}

	private void authorize(Platform plat) {
		if (plat.isValid()) {
			// String userId = plat.getDb().getUserId();
			// if (!TextUtils.isEmpty(userId)) {
			// UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
			// login(plat.getName(), userId, null);
			// return;
			// }
			plat.removeAccount();
		}
		plat.setPlatformActionListener(this);
		plat.SSOSetting(true);// true:close the SSO;false:open the SSO
		plat.showUser(null);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_USERID_FOUND: {
			Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT)
					.show();
		}
			break;
		case MSG_LOGIN: {

			String text = getString(R.string.logining, msg.obj);
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			System.out.println("---------------");
			// 启动Main Activity
			Intent intent = new Intent(Login.this, AuctionClientActivity.class);
			startActivity(intent);
			// 结束该Activity
			finish();
		}
			break;
		case MSG_AUTH_CANCEL: {
			Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT)
					.show();
			System.out.println("-------MSG_AUTH_CANCEL--------");
		}
			break;
		case MSG_AUTH_ERROR: {
			Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT)
					.show();
			System.out.println("-------MSG_AUTH_ERROR--------");
		}
			break;
		case MSG_AUTH_COMPLETE: {
			Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT)
					.show();
			System.out.println("--------MSG_AUTH_COMPLETE-------");

		}
			break;
		}
		return false;
	}

}
