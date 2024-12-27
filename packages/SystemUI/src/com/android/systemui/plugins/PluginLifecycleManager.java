package com.android.systemui.plugins;

import android.content.ComponentName;
import com.android.systemui.plugins.Plugin;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
