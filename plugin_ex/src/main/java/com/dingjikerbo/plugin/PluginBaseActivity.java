package com.dingjikerbo.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by dingjikerbo on 16/3/13.
 */
public class PluginBaseActivity extends FragmentActivity implements IPlugin {

    private Activity mProxyActivity;
    private PluginPackage mPluginPackage;

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void attach(Activity proxyActivity, PluginPackage pluginPackage) {
        mProxyActivity = proxyActivity;
        mPluginPackage = pluginPackage;
    }
}
