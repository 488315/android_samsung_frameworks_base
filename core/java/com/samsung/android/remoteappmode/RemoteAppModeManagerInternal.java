package com.samsung.android.remoteappmode;

import android.content.res.Configuration;

public abstract class RemoteAppModeManagerInternal {
    public abstract void onConfigurationChanged(Configuration configuration, int i);

    public abstract void onSecuredAppLaunched(int i, String str);

    public abstract void onSecuredAppRemoved(int i, String str);
}
