package com.inuker.plugcore;

import java.io.File;

import android.content.Context;

/**
 * Created by dingjikerbo on 16/4/16.
 */
public class PluginDirHelper {

    private static File sPluginExternalRootDir;
    private static File sPluginApkDir;

    private static File sPluginInternalRootDir;
    private static File sDexOutputDir;
    private static File sNativeLibDir;

    static void initial(Context context) {
        sPluginExternalRootDir = context.getExternalFilesDir("plugin");
        createDirIfNotExist(sPluginExternalRootDir);

        sPluginApkDir = new File(sPluginExternalRootDir, "apk");
        createDirIfNotExist(sPluginApkDir);

        sPluginInternalRootDir = context.getDir("plugin", Context.MODE_PRIVATE);
        createDirIfNotExist(sPluginInternalRootDir);

        sDexOutputDir = new File(sPluginInternalRootDir, "dex");
        createDirIfNotExist(sDexOutputDir);

        sNativeLibDir = new File(sPluginInternalRootDir, "nativelib");
        createDirIfNotExist(sNativeLibDir);
    }

    private static void createDirIfNotExist(File dir) {
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
    }

    public static File getPluginApkDir() {
        return sPluginApkDir;
    }

    static File getDexOutputDir() {
        return sDexOutputDir;
    }

    static String getDexoutputDirPath() {
        return getDexOutputDir().getAbsolutePath();
    }

    static File getNativeLibDir() {
        return sNativeLibDir;
    }

    static String getNativeLibDirPath() {
        return getNativeLibDir().getAbsolutePath();
    }
}
