package com.samsung.systemui.splugins;

import android.content.Context;
import android.os.Looper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface SPluginInitializer {
    String[] getAllowedPlugins(Context context);

    Looper getBgLooper();

    SPluginEnabler getPluginEnabler(Context context);

    void handleWtfs();

    void onPluginManagerInit();
}
