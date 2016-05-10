package com.inuker.pluglib;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

public interface IPluginBaseActivity {

	void startActivity(Intent intent);

	void setContentView(int layoutResID);

	Resources getResources();

	AssetManager getAssets();

	void onCreate(Bundle savedInstanceState);
}
