package com.dingjikerbo.plugin.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.dingjikerbo.library.utils.FileUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * Created by dingjikerbo on 16/3/12.
 */
public class PluginManager {

    private static HashMap<String, Plugin> mPluginMap = new HashMap<String, Plugin>();

    public static Plugin getPlugin(String apkPath) {
        return mPluginMap.get(apkPath);
    }

    public static void loadPluginApk(Context context, String apkPath) {
        String dexOutputPath = context.getDir("plugin", 0).getAbsolutePath();

        FileUtils.deleteFile(dexOutputPath, false);

        DexClassLoader dexClassLoader = new DexClassLoader(apkPath, dexOutputPath,
                null, PluginManager.class.getClassLoader());

        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = createResources(context, assetManager);

        Plugin plugin = new Plugin(dexClassLoader, assetManager, resources);

        mPluginMap.put(apkPath, plugin);
    }

    private static AssetManager createAssetManager(String dexPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod(
                    "addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Resources createResources(Context context, AssetManager assetManager) {
        Resources superRes = context.getResources();
        Resources resources = new Resources(assetManager,
                superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }
}
