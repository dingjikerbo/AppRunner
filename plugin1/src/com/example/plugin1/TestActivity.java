package com.example.plugin1;

import com.inuker.pluglib.PluginBaseActivity;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends PluginBaseActivity {

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
	}
}
