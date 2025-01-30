package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.Plugin;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginListener<T extends Plugin> {
    default boolean onPluginAttached(PluginLifecycleManager<T> pluginLifecycleManager) {
        return true;
    }

    default void onPluginLoaded(T t, Context context, PluginLifecycleManager<T> pluginLifecycleManager) {
        onPluginConnected(t, context);
    }

    default void onPluginUnloaded(T t, PluginLifecycleManager<T> pluginLifecycleManager) {
        onPluginDisconnected(t);
    }

    default void onPluginDetached(PluginLifecycleManager<T> pluginLifecycleManager) {
    }

    @Deprecated
    default void onPluginDisconnected(T t) {
    }

    @Deprecated
    default void onPluginConnected(T t, Context context) {
    }
}
