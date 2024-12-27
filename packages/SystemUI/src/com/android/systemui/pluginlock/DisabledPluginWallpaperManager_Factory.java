package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisabledPluginWallpaperManager_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final DisabledPluginWallpaperManager_Factory INSTANCE = new DisabledPluginWallpaperManager_Factory();

        private InstanceHolder() {
        }
    }

    public static DisabledPluginWallpaperManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DisabledPluginWallpaperManager newInstance() {
        return new DisabledPluginWallpaperManager();
    }

    @Override // javax.inject.Provider
    public DisabledPluginWallpaperManager get() {
        return newInstance();
    }
}
