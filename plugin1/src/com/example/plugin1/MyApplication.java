package com.example.plugin1;

import com.inuker.pluglib.IPluginCallback;
import com.inuker.pluglib.PluginApplication;
import com.inuker.pluglib.PluginHostApi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by dingjikerbo on 16/5/7.
 */
public class MyApplication extends PluginApplication {

	@Override
	public boolean handleMessage(int msg, Bundle args, IPluginCallback callback) {
		// TODO Auto-generated method stub
		Log.i("bush", "MessageReceiver.handleMessage " + msg);
		
        switch (msg) {
            case MSG_LAUNCHER:
            	Intent intent = new Intent();
            	intent.setClass(this, LauncherActivity.class);
            	startActivity(intent);
                break;
                
//            case MSG_REMOTE_VIEWS:
//            	Bundle data = new Bundle();
//            	RemoteViews view = new RemoteViews(getContext().getPackageName(), R.layout.remote);
//            	data.putParcelable("view", view);
//            	callback.onCallback(1, data);
//            	break;
        }
        
		return false;
	}
}
