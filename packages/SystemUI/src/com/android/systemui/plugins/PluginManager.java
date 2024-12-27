package com.android.systemui.plugins;

import android.text.TextUtils;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginManager {
    public static final String NOTIFICATION_CHANNEL_ID = "ALR";
    public static final String PLUGIN_CHANGED = "com.android.systemui.action.PLUGIN_CHANGED";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class Helper {
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
