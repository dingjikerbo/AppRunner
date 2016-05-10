package com.inuker.pluglib;

import android.os.Bundle;

/**
 * Created by dingjikerbo on 16/5/7.
 */
public interface IPluginMessageReceiver {

    int MSG_LAUNCHER = 0;
    
    int MSG_REMOTE_VIEWS = 1;
    
    boolean handleMessage(int msg, Bundle args, IPluginCallback callback);
}
