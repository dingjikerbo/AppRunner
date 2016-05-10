package com.inuker.plugcore;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import com.inuker.pluglib.PluginApplication;
import com.inuker.pluglib.PluginHostApi;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import dalvik.system.DexClassLoader;

/**
 * Created by dingjikerbo on 16/4/15.
 */
public class PluginManager {

    private static Context mContext;

    private HashMap<String, PluginPackage> mLoadedApks;
    private HashMap<String, PluginPackage> mPackageMap;

    public static void initial(Context context) {
        mContext = context;
        PluginDirHelper.initial(context);
    }

    private PluginManager() {
    	mLoadedApks = new HashMap<String, PluginPackage>();
    	mPackageMap = new HashMap<String, PluginPackage>();
    	setPluginHostApiInstance();
    }
    
    private void setPluginHostApiInstance() {
    	try {
			Field field = PluginHostApi.class.getDeclaredField("instance");
			field.setAccessible(true);
			field.set(null, new PluginHostApiImpl());
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static PluginManager getInstance() {
        return PluginManagerHolder.instance;
    }

    private static class PluginManagerHolder {
        private static PluginManager instance = new PluginManager();
    }

    public PluginPackage loadApk(String apkPath) {
    	PluginPackage loadedApk = null;
    	
        if (!TextUtils.isEmpty(apkPath)) {
            loadedApk = mLoadedApks.get(apkPath);
            if (loadedApk != null) {
                return loadedApk;
            }
        }

        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES
                        | PackageManager.GET_SERVICES
                        | PackageManager.GET_RECEIVERS
                        | PackageManager.GET_META_DATA);

        if (packageInfo == null) {
            return null;
        }

        loadedApk = preparePluginEnv(packageInfo, apkPath);
        
        if (loadedApk != null) {
        	mLoadedApks.put(apkPath, loadedApk);
        }

        return loadedApk;
    }

    private PluginPackage preparePluginEnv(PackageInfo packageInfo, String dexPath) {
        PluginPackage pluginPackage = getPackage(packageInfo.packageName);
        if (pluginPackage != null) {
            return pluginPackage;
        }
        DexClassLoader dexClassLoader = createDexClassLoader(dexPath);
        AssetManager assetManager = createAssetManager(dexPath);
        Resources resources = createResources(assetManager);
        PluginApplication application = createPluginApplication(packageInfo, dexClassLoader);
        pluginPackage = new PluginPackage(dexClassLoader, resources, packageInfo, application);
        mPackageMap.put(packageInfo.packageName, pluginPackage);
        return pluginPackage;
    }

    private DexClassLoader createDexClassLoader(String dexPath) {
        String optimizedDir = PluginDirHelper.getDexoutputDirPath();

        try {
            FileUtils.cleanDirectory(new File(optimizedDir));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DexClassLoader(dexPath, optimizedDir,
                null, mContext.getClassLoader());
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
    
    private PluginApplication createPluginApplication(PackageInfo rawPackage, DexClassLoader classLoader) {
        String applicationName = rawPackage.applicationInfo.className;
        
        PluginApplication pluginApplication = null;

        if (!TextUtils.isEmpty(applicationName)) {
        	if (applicationName.startsWith(".")) {
        		applicationName = String.format("%s%s", rawPackage.packageName, applicationName);
        	}
        	
            try {
                Class<?> localClass = classLoader.loadClass(applicationName);
                Constructor<?> localConstructor = localClass.getConstructor(new Class[]{});
                pluginApplication = (PluginApplication) localConstructor.newInstance(new Object[]{});
                PluginContext pluginContext = new PluginContext(mContext, rawPackage.packageName);
                pluginApplication.attachBaseContext(pluginContext);
                pluginApplication.onCreate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return pluginApplication;
    }
    
    public PluginPackage getPackage(String packageName) {
        return mPackageMap.get(packageName);
    }

    private Resources createResources(AssetManager assetManager) {
        Resources superRes = mContext.getResources();
        Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }

}
