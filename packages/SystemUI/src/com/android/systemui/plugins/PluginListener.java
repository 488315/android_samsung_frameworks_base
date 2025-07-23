package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.Plugin;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
