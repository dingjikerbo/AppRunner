package com.inuker.pluglib;

import android.content.Context;
import android.content.Intent;

public interface IPluginHostApi {

	void startActivity(Context context, Intent intent);
	
}
