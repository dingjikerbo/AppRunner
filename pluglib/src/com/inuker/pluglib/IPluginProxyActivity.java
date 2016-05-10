package com.inuker.pluglib;

import android.content.res.AssetManager;
import android.content.res.Resources;

public interface IPluginProxyActivity {

	void setContentView(int layoutResID);
	Resources getResources();
	AssetManager getAssets();
}
