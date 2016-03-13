package com.dingjikerbo.plugin.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

    private static PluginManager sInstance;

    private Context mContext;

    private String mNativeLibDir;
    private HashMap<String, PluginPackage> mPluginMap = new HashMap<String, PluginPackage>();

    private PluginManager(Context context) {
        mContext = context.getApplicationContext();
        mNativeLibDir = mContext.getDir("pluginlib", Context.MODE_PRIVATE).getAbsolutePath();
    }

    public static PluginManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PluginManager.class) {
                if (sInstance == null) {
                    sInstance = new PluginManager(context);
                }
            }
        }
        return sInstance;
    }

    public PluginPackage loadPluginApk(String apkPath) {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (packageInfo == null) {
            return null;
        }

        PluginPackage plugin = getPluginPackage(packageInfo.packageName);
        if (plugin != null) {
            return plugin;
        }

        DexClassLoader dexClassLoader = createDexClassLoader(apkPath);
        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = createResources(assetManager);

        plugin = new PluginPackage(dexClassLoader, resources, packageInfo);
        mPluginMap.put(apkPath, plugin);

        return plugin;
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        String dexOutputPath = mContext.getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath();
        FileUtils.deleteFile(dexOutputPath, false);
        return new DexClassLoader(apkPath, dexOutputPath, mNativeLibDir, mContext.getClassLoader());
    }

    private AssetManager createAssetManager(String dexPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Resources createResources(AssetManager assetManager) {
        Resources superRes = mContext.getResources();
        Resources resources = new Resources(assetManager,
                superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }

    public PluginPackage getPluginPackage(String packageName) {
        return mPluginMap.get(packageName);
    }
}
