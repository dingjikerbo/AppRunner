package com.dingjikerbo.plugin.core;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.os.Bundle;

import com.dingjikerbo.library.BaseActivity;
import com.dingjikerbo.plugin.IPlugin;
import com.dingjikerbo.plugin.PluginPackage;

import java.lang.reflect.Constructor;

/**
 * Created by dingjikerbo on 16/3/13.
 */
public class PluginProxyActivity extends BaseActivity {

    private PluginManager mPluginManager;
    private PluginPackage mPluginPackage;
    private AssetManager mAssetManager;
    private Resources mResources;
    private Theme mTheme;

    private ActivityInfo mActivityInfo;
    private IPlugin mPluginActivity;
    private String mPackageName;
    private String mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        intent.setExtrasClassLoader(PluginConstants.class.getClassLoader());

        mPackageName = intent.getStringExtra(PluginConstants.EXTRA_PACKAGE);
        mClass = intent.getStringExtra(PluginConstants.EXTRA_CLASS);

        mPluginManager = PluginManager.getInstance(this);
        mPluginPackage = mPluginManager.getPluginPackage(mPackageName);
        mAssetManager = mPluginPackage.assetManager;
        mResources = mPluginPackage.resources;

        initializeActivityInfo();
        handleActivityInfo();
        launchTargetActivity();
    }

    private void initializeActivityInfo() {
        PackageInfo packageInfo = mPluginPackage.packageInfo;
        if ((packageInfo.activities != null) && (packageInfo.activities.length > 0)) {
            if (mClass == null) {
                mClass = packageInfo.activities[0].name;
            }

            //Finals 修复主题BUG
            int defaultTheme = packageInfo.applicationInfo.theme;
            for (ActivityInfo a : packageInfo.activities) {
                if (a.name.equals(mClass)) {
                    mActivityInfo = a;
                    // Finals ADD 修复主题没有配置的时候插件异常
                    if (mActivityInfo.theme == 0) {
                        if (defaultTheme != 0) {
                            mActivityInfo.theme = defaultTheme;
                        } else {
                            if (Build.VERSION.SDK_INT >= 14) {
                                mActivityInfo.theme = android.R.style.Theme_DeviceDefault;
                            } else {
                                mActivityInfo.theme = android.R.style.Theme;
                            }
                        }
                    }
                }
            }

        }
    }

    private void handleActivityInfo() {
        if (mActivityInfo.theme > 0) {
            setTheme(mActivityInfo.theme);
        }
        Theme superTheme = getTheme();
        mTheme = mResources.newTheme();
        mTheme.setTo(superTheme);
        // Finals适配三星以及部分加载XML出现异常BUG
        try {
            mTheme.applyStyle(mActivityInfo.theme, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void launchTargetActivity() {
        try {
            Class<?> localClass = getClassLoader().loadClass(mClass);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
            Object instance = localConstructor.newInstance(new Object[] {});
            mPluginActivity = (IPlugin) instance;
            mPluginActivity.attach(this, mPluginPackage);

            Bundle bundle = new Bundle();
            mPluginActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
