package com.android.systemui.plugins;

import android.content.ComponentName;
import com.android.systemui.plugins.Plugin;
import java.util.function.BiConsumer;

public interface PluginLifecycleManager<T extends Plugin> {
    ComponentName getComponentName();

    String getPackage();

    T getPlugin();

    default boolean isLoaded() {
        return getPlugin() != null;
    }

    void loadPlugin();

    void setLogFunc(BiConsumer<String, String> biConsumer);

    void unloadPlugin();
}
