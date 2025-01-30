package com.samsung.systemui.splugins;

import android.content.Context;
import android.os.Looper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SPluginInitializer {
    String[] getAllowedPlugins(Context context);

    Looper getBgLooper();

    SPluginEnabler getPluginEnabler(Context context);

    void handleWtfs();

    void onPluginManagerInit();
}
