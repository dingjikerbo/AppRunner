package com.dingjikerbo.plugin.core;

import android.app.Activity;
import android.os.Bundle;

import com.dingjikerbo.library.BaseActivity;

/**
 * Created by dingjikerbo on 16/3/13.
 */
public class PluginBaseActivity extends BaseActivity implements IPlugin {

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
