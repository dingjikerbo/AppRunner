package com.inuker.plugcore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.inuker.pluglib.IPluginProxyActivity;
import com.inuker.pluglib.PluginBaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.util.Log;

public class PluginProxyActivity extends Activity implements IPluginProxyActivity {

	private PluginPackage mLoadedApk;

	private Theme mTheme;

	private PluginBaseActivity mPluginActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String packageName = intent.getStringExtra(PluginConstants.EXTRA_PACKAGE_NAME);
		String className = intent.getStringExtra(PluginConstants.EXTRA_CLASS_NAME);

		Log.i("bush", String.format("PluginProxyActivity.onCreate: packageName = %s, className = %s", packageName,
				className));

		mLoadedApk = PluginManager.getInstance().getPackage(packageName);

		try {
			Class<?> clazz = mLoadedApk.getClassLoader().loadClass(className);
			mPluginActivity = (PluginBaseActivity) clazz.newInstance();
			mPluginActivity.attachProxy(this);
			handleActivityTheme();
			mPluginActivity.onCreate(savedInstanceState);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final InvocationHandler mPluginHandler = new InvocationHandler() {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// TODO Auto-generated method stub
			if (method.getName().equals("onCreate")) {
				return null;
			}
			return method.invoke(PluginProxyActivity.this, args);
		}

	};
	
	private void handleActivityTheme() {
		Theme superTheme = super.getTheme();
		mTheme = mLoadedApk.getResources().newTheme();
		mTheme.setTo(superTheme);
	}

	@Override
	public Theme getTheme() {
		if (mTheme == null) {
			return super.getTheme();
		} else {
			return mTheme;
		}
	}

	@Override
	public AssetManager getAssets() {
		if (mLoadedApk != null) {
			return mLoadedApk.getAssetManager();
		} else {
			return super.getAssets();
		}
	}

	@Override
	public ClassLoader getClassLoader() {
		if (mLoadedApk != null) {
			return mLoadedApk.getClassLoader();
		} else {
			return super.getClassLoader();
		}
	}

	@Override
	public Resources getResources() {
		if (mLoadedApk != null) {
			return mLoadedApk.getResources();
		} else {
			return super.getResources();
		}
	}

}
