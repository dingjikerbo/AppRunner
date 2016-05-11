package com.inuker.pluglib;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

public interface IPluginBaseActivity {

	public void startActivity(Intent intent);
	public void setContentView(int layoutResID);
	public Resources getResources();
	public Context getContext();
	public AssetManager getAssets();
	public void onCreate(Bundle savedInstanceState);
}
