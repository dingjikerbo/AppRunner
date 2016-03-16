package com.dingjikerbo.plugin;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by dingjikerbo on 16/3/13.
 */
public interface IPlugin {
    void onCreate(Bundle savedInstanceState);
    void attach(Activity proxyActivity, PluginPackage pluginPackage);
}
