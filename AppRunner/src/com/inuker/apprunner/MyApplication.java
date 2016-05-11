package com.inuker.apprunner;

import com.inuker.plugcore.PluginManager;

import android.app.Application;

/**
 * Created by dingjikerbo on 16/4/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PluginManager.initial(this);
    }
}
