package com.example.plugin1;

import com.inuker.pluglib.PluginBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LauncherActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doClick();
			}
		});
    }
    
    private void doClick() {
    	Intent intent = new Intent();
		intent.setClass(this, TestActivity.class);
		Log.i("bush", intent.getComponent().getPackageName());
		startActivity(intent);
    }
}
