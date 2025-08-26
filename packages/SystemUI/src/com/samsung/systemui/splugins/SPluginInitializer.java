package com.samsung.systemui.splugins;

import android.content.Context;
import android.os.Looper;

/* loaded from: classes4.dex */
public interface SPluginInitializer {
    String[] getAllowedPlugins(Context context);

    Looper getBgLooper();

    SPluginEnabler getPluginEnabler(Context context);

    void handleWtfs();

    void onPluginManagerInit();
}
