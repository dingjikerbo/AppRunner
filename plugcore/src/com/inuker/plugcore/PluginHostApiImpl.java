package com.inuker.plugcore;

import com.inuker.pluglib.IPluginHostApi;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class PluginHostApiImpl implements IPluginHostApi {

	@Override
	public void startActivity(Context context, Intent extIntent) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtras(extIntent);
		
		if (context instanceof Application) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		
		intent.setClass(context, PluginProxyActivity.class);
		intent.putExtra(PluginConstants.EXTRA_PACKAGE_NAME, extIntent.getComponent().getPackageName());
		intent.putExtra(PluginConstants.EXTRA_CLASS_NAME, extIntent.getComponent().getClassName());
		
		context.startActivity(intent);
	}
}
