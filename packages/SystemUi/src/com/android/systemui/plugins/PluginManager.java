package com.android.systemui.plugins;

import android.text.TextUtils;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginManager {
    public static final String NOTIFICATION_CHANNEL_ID = "ALR";
    public static final String PLUGIN_CHANGED = "com.android.systemui.action.PLUGIN_CHANGED";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Helper {
        public static <P> String getAction(Class<P> cls) {
            ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
            if (providesInterface == null) {
                throw new RuntimeException(cls + " doesn't provide an interface");
            }
            if (!TextUtils.isEmpty(providesInterface.action())) {
                return providesInterface.action();
            }
            throw new RuntimeException(cls + " doesn't provide an action");
        }
    }

    <T extends Plugin> void addPluginListener(PluginListener<T> pluginListener, Class<T> cls);

    <T extends Plugin> void addPluginListener(PluginListener<T> pluginListener, Class<T> cls, boolean z);

    <T extends Plugin> void addPluginListener(String str, PluginListener<T> pluginListener, Class<T> cls);

    <T extends Plugin> void addPluginListener(String str, PluginListener<T> pluginListener, Class<T> cls, boolean z);

    <T extends Plugin> void addPluginListener(String str, PluginListener<T> pluginListener, Class<T> cls, boolean z, boolean z2, int i);

    <T> boolean dependsOn(Plugin plugin, Class<T> cls);

    String[] getPrivilegedPlugins();

    default boolean isValidClassLoader(ClassLoader classLoader) {
        return true;
    }

    void removePluginListener(PluginListener<?> pluginListener);
}
