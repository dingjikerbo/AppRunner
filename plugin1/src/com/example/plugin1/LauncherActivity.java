package com.example.plugin1;

import com.inuker.pluglib.PluginBaseActivity;

import android.os.Bundle;

public class LauncherActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }
}
