package com.dingjikerbo.plugin;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by dingjikerbo on 16/3/12.
 */
public class PluginPackage {
    public Resources resources;
    public AssetManager assetManager;
    public DexClassLoader dexClassLoader;
    public PackageInfo packageInfo;

    public PluginPackage(DexClassLoader dexClassLoader, Resources resources, PackageInfo packageInfo) {
        this.resources = resources;
        this.assetManager = resources.getAssets();
        this.dexClassLoader = dexClassLoader;
        this.packageInfo = packageInfo;
    }
}
