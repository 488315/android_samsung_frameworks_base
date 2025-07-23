package com.android.systemui.plugins;

import android.content.ComponentName;
import com.android.systemui.plugins.Plugin;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
