package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.Plugin;

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
