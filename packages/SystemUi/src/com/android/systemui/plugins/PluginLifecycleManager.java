package com.android.systemui.plugins;

import android.content.ComponentName;
import com.android.systemui.plugins.Plugin;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginLifecycleManager<T extends Plugin> {
    ComponentName getComponentName();

    String getPackage();

    T getPlugin();

    default boolean isLoaded() {
        return getPlugin() != null;
    }

    void loadPlugin();

    void unloadPlugin();
}
