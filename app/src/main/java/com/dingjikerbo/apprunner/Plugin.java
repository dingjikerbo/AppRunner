package com.dingjikerbo.apprunner;

import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by dingjikerbo on 16/3/12.
 */
public class Plugin {
    public Resources resources;
    public AssetManager assetManager;
    public DexClassLoader dexClassLoader;

    public Plugin(DexClassLoader dexClassLoader, AssetManager assetManager, Resources resources) {
        this.resources = resources;
        this.assetManager = assetManager;
        this.dexClassLoader = dexClassLoader;
    }
}
