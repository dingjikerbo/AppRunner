package com.inuker.pluglib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * Created by dingjikerbo on 16/4/15.
 */
public class PluginBaseActivity extends Activity {

	private String mPackageName;
	private IPluginProxyActivity mProxy;

	public void attachProxy(String packageName, IPluginProxyActivity proxy) {
		this.mPackageName = packageName;
		mProxy = proxy;
	}
	
	@Override
	public void attachBaseContext(Context context) {
		super.attachBaseContext(context);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		mProxy.setContentView(layoutResID);
	}

	@Override
	public AssetManager getAssets() {
		return mProxy.getAssets();
	}

	@Override
	public Resources getResources() {
		return mProxy.getResources();
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
//		intent.setPackage(mPackageName);
		mProxy.startActivity(intent);
	}

	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		return mProxy.findViewById(id);
	}
}
