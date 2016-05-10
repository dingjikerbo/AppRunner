package com.inuker.apprunner;

import java.io.File;

import com.inuker.plugcore.PluginDirHelper;
import com.inuker.plugcore.PluginManager;
import com.inuker.plugcore.PluginPackage;
import com.inuker.pluglib.IPluginCallback;
import com.inuker.pluglib.IPluginMessageReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class MainActivity extends Activity {

	private LinearLayout mRoot;
	
	private Button mBtn0, mBtn1, mBtn2;
	
	private PluginPackage mLoadedApk;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoot = (LinearLayout) findViewById(R.id.root);
        
        Button btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadPlugin();
            }
        });
        
        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	startPluginActivity();
            }
        });
        
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showRemoteView();
			}
		});

    }

    private void loadPlugin() {
        File pluginApkDir = PluginDirHelper.getPluginApkDir();

        File[] pluginApks = pluginApkDir.listFiles();

        if (pluginApks != null && pluginApks.length > 0) {
        	mLoadedApk = PluginManager.getInstance().loadApk(pluginApks[0].getAbsolutePath());
        }
    }
    
    private void startPluginActivity() {
    	mLoadedApk.getApplication().handleMessage(IPluginMessageReceiver.MSG_LAUNCHER, null, null);
    }

    private void showRemoteView() {
        mLoadedApk.getApplication().handleMessage(IPluginMessageReceiver.MSG_REMOTE_VIEWS, null, new IPluginCallback() {

			@Override
			public void onCallback(int arg0, Bundle arg1) {
				// TODO Auto-generated method stub
				Context context = MainActivity.this;
				RemoteViews rview = arg1.getParcelable("view");
				
				View view = rview.apply(new ContextWrapper(context) {

					@Override
					public Resources getResources() {
						// TODO Auto-generated method stub
						return mLoadedApk.getResources();
					}
					
				}, mRoot);
				
				mRoot.addView(view);
			}
        	
        });
    }
}
