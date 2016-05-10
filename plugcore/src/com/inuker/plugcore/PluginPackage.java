package com.inuker.plugcore;


import com.inuker.pluglib.PluginApplication;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import dalvik.system.DexClassLoader;

/**
 * Created by dingjikerbo on 16/4/15.
 */
public class PluginPackage {

    private String packageName;

    private String packagePath;

    private DexClassLoader classLoader;

    private AssetManager assetManager;

    private Resources resources;

    private PackageInfo packageInfo;
    
    private PluginApplication application;

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public DexClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(DexClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public PluginApplication getApplication() {
		return application;
	}

	public void setApplication(PluginApplication application) {
		this.application = application;
	}

	public PluginPackage(DexClassLoader loader, Resources resources,
                         PackageInfo packageInfo, PluginApplication application) {
        this.packageName = packageInfo.packageName;
        this.classLoader = loader;
        this.assetManager = resources.getAssets();
        this.resources = resources;
        this.packageInfo = packageInfo;
        this.application = application;
    }
}
