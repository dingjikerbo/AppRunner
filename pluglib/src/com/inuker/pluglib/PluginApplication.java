package com.inuker.pluglib;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by dingjikerbo on 16/5/7.
 */
public abstract class PluginApplication extends Application implements IPluginMessageReceiver {

	@Override
	public boolean handleMessage(int msg, Bundle args, IPluginCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void attachBaseContext(Context base) {
		// TODO Auto-generated method stub
		super.attachBaseContext(base);
	}
	
}
