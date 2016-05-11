package com.inuker.plugcore;

import com.inuker.pluglib.PluginHostApi;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.util.Log;

public class PluginContext extends ContextWrapper {
	
	private String mPackageName;
	
	public PluginContext(Context base, String packageName) {
		super(base);
		// TODO Auto-generated constructor stub
		mPackageName = packageName;
	}

	@Override
	public String getPackageName() {
		// TODO Auto-generated method stub
		return mPackageName;
	}
	
	private PluginPackage getPluginPackage() {
		return PluginManager.getInstance().getPackage(mPackageName);
	}
	
	@Override
	public AssetManager getAssets() {
		// TODO Auto-generated method stub
		return getPluginPackage().getAssetManager();
	}

	@Override
	public Resources getResources() {
		// TODO Auto-generated method stub
		return getPluginPackage().getResources();
	}

	@Override
	public Theme getTheme() {
		// TODO Auto-generated method stub
		return super.getTheme();
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		PluginHostApi.getInstance().startActivity(getBaseContext(), intent);
	}
}
